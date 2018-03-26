package com.speedment.runtime.join.internal.component.stream.sql;

import static com.speedment.common.invariant.IntRangeUtil.requireNonNegative;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.SqlAdapter;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.internal.stream.autoclose.AutoClosingReferenceStream;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.internal.component.stream.SqlAdapterMapper;
import com.speedment.runtime.join.stage.JoinOperator;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import java.sql.ResultSet;
import java.util.ArrayList;
import static java.util.Collections.emptyList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class JoinSqlUtil {

    private JoinSqlUtil() {
    }

    public static Dbms requireSameDbms(Project project, List<Stage<?>> stages) {
        requireNonNull(project);
        requireNonNull(stages);
        final Dbms dbms = DocumentDbUtil.referencedDbms(project, stages.get(0).identifier());
        final List<Dbms> failingDbmses = new ArrayList<>();
        for (int i = 1; i < stages.size(); i++) {
            final Dbms otherDbms = DocumentDbUtil.referencedDbms(project, stages.get(i).identifier());
            if (!DocumentDbUtil.isSame(dbms, otherDbms)) {
                failingDbmses.add(otherDbms);
            }
        }
        if (!failingDbmses.isEmpty()) {
            throw new IllegalStateException(
                "The first database in this join is " + dbms.toString()
                + " but there are other databases in the same join which is illegal: "
                + failingDbmses.toString()
            );
        }
        return dbms;
    }

    public static <T> SqlFunction<ResultSet, T> resultSetMapper(
        final Project project,
        final TableIdentifier<T> identifier,
        final List<Stage<?>> stages,
        final int stageIndex,
        final SqlAdapterMapper sqlAdapterMapper
    ) {
        requireNonNull(project);
        requireNonNull(identifier);
        requireNonNull(stages);
        requireNonNegative(stageIndex);
        requireNonNull(sqlAdapterMapper);
        @SuppressWarnings("unchecked")
        final Stage<T> stage = (Stage<T>) stages.get(stageIndex);
        int offset = 0;

        for (int i = 0; i < stageIndex; i++) {
            final Stage<?> otherStage = stages.get(i);
            final Table table = DocumentDbUtil.referencedTable(project, otherStage.identifier());
            offset += table.columns()
                .filter(Column::isEnabled)
                .count();
        }

        final Table table = DocumentDbUtil.referencedTable(project, stage.identifier());
        int nullOffset = -1;

        // Check if this stage renders an on-field to be nullable
        if (stage.joinType().isPresent() && stage.joinType().get().isNullableSelf()) {
            nullOffset = findNullOffset(table, stage, stage.field().get());
        }

        // Check if another (RIGHT JOIN) stage renders an on-field in this stage to be nullable
        // No use to check if we already know
        if (nullOffset == -1) {
            final TableIdentifier<?> thisId = stage.identifier();
            for (int i = 0; i < stages.size(); i++) {
                if (stageIndex == i) {
                    // Ignore this stage
                    continue;
                }
                final Stage<?> otherStage = stages.get(i);
                if (otherStage.joinType().isPresent() && otherStage.joinType().get().isNullableOther()) {
                    final TableIdentifier<?> referencedId = otherStage.foreignField().get().identifier().asTableIdentifier();
                    if (thisId.equals(referencedId)) {
                        nullOffset = findNullOffset(table, otherStage, otherStage.foreignField().get());
                        // If we have a between operation where there is another field pointed, my
                        // belief is that we can safely ignore that because both fields will be null and we
                        // only need to detect one
                    }
                }
            }
        }

        if (nullOffset >= 0) {
            return new NullAwareSqlAdapter<>(sqlAdapterMapper.apply(identifier), nullOffset).entityMapper(offset);
        } else {
            return sqlAdapterMapper.apply(identifier).entityMapper(offset);
        }

    }

    private static int findNullOffset(
        final Table table,
        final Stage<?> stage,
        final HasComparableOperators<?, ?> field
    ) {
        int result = -1;
        final String onColumnId = field
            .identifier()
            .getColumnName();

        final List<Column> columns = table.columns()
            .filter(Column::isEnabled)
            .collect(toList());

        for (int j = 0; j < columns.size(); j++) {
            final String columnId = columns.get(j).getId();
            if (columnId.equals(onColumnId)) {
                // Compose a null detecting entity mapper
                result = j;
                break;
            }
        }
        if (result == -1) {
            throw new IllegalStateException(
                "Unable to locate column " + onColumnId + " in table " + table.getId()
                + " for stage " + stage.toString()
                + " Columns: " + columns.stream().map(Column::getId).collect(joining(", "))
            );
        }
        return result;
    }

    private static class NullAwareSqlAdapter<ENTITY> implements SqlAdapter<ENTITY> {

        private final SqlAdapter<ENTITY> inner;
        private final int nullableColumnOffset;

        private NullAwareSqlAdapter(SqlAdapter<ENTITY> inner, int nullableColumnOffset) {
            this.inner = requireNonNull(inner);
            this.nullableColumnOffset = requireNonNegative(nullableColumnOffset);
        }

        @Override
        public TableIdentifier<ENTITY> identifier() {
            return inner.identifier();
        }

        @Override
        public SqlFunction<ResultSet, ENTITY> entityMapper() {
            return entityMapper(0);
        }

        @Override
        public SqlFunction<ResultSet, ENTITY> entityMapper(int offset) {
            return rs -> {
                final Object value = rs.getObject(1 + offset + nullableColumnOffset);
                // We must check rs.wasNull() becaues the joined field might be null
                // even though it is non-nullable like int, long etc.
                if (value == null || rs.wasNull()) {
                    return null;
                } else {
                    return inner.entityMapper(offset).apply(rs);
                }
            };
        }

    }

    public static <T> Stream<T> stream(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final Project project,
        final List<Stage<?>> stages,
        final SqlFunction<ResultSet, T> rsMapper
    ) {
        requireNonNull(project);
        requireNonNull(dbmsHandlerComponent);
        requireNonNull(stages);
        requireNonNull(rsMapper);
        final SqlInfo sqlInfo = new SqlInfo(dbmsHandlerComponent, project, stages);
        final List<SqlStage> sqlStages = sqlInfo.sqlStages();

        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(
            sqlStages.stream()
                .map(SqlStage::sqlColumnList)
                .collect(joining(", "))
        );

        final SqlStage firstSqlStage = sqlStages.get(0);

        sb.append(" FROM ").append(firstSqlStage.sqlTableReference()).append(" ");

        for (int i = 1; i < sqlStages.size(); i++) {
            final String joinSql = renderJoin(sqlInfo.namingConvention(), sqlStages, stages, i);
            sb.append(joinSql);
        }
        // The predicates connected to each specific table is rendered
        // at the end of the query.
        final List<SqlPredicateFragment> fragments
            = renderPredicates(sqlInfo.project(), sqlInfo.namingConvention(), sqlInfo.fieldPredicateView(), stages);

        final List<?> values;
        if (!fragments.isEmpty()) {
            sb.append(
                fragments.stream().map(SqlPredicateFragment::getSql).collect(joining(" AND ", " WHERE ", ""))
            );
            values = fragments.stream().flatMap(SqlPredicateFragment::objects).collect(toList());
        } else {
            values = emptyList();
        }

        final String sql = sb.toString();

        final AsynchronousQueryResult<T> asynchronousQueryResult
            = sqlInfo.dbmsType().getOperationHandler().executeQueryAsync(
                sqlInfo.dbms(),
                sql,
                values,
                rsMapper,
                ParallelStrategy.computeIntensityDefault()
            );
        return new AutoClosingReferenceStream<>(asynchronousQueryResult.stream())
            .onClose(asynchronousQueryResult::close);
    }

    private static final String[] ALIASES = {"A", "B", "C", "D", "E", "F"};

    public static String tableAlias(int index) {
        requireNonNegative(index);
        return ALIASES[index];
    }

    private static List<SqlPredicateFragment> renderPredicates(
        final Project project,
        final DatabaseNamingConvention naming,
        final FieldPredicateView fieldPredicateView,
        final List<Stage<?>> stages
    ) {
        requireNonNull(project);
        requireNonNull(naming);
        requireNonNull(fieldPredicateView);
        requireNonNull(stages);
        final List<SqlPredicateFragment> result = new ArrayList<>();
        for (int i = 0; i < stages.size(); i++) {
            final int stageIndex = i;
            final Stage<?> stage = stages.get(stageIndex);

            if (!stage.predicates().isEmpty()) {
                for (int j = 0; j < stage.predicates().size(); j++) {
                    final Predicate<?> predicate = stage.predicates().get(j);
                    if (!(predicate instanceof FieldPredicate)) {
                        throw new IllegalStateException(predicate + " is not implementing " + FieldPredicate.class.getName());
                    }
                    final FieldPredicate<?> fieldPredicate = (FieldPredicate<?>) predicate;

                    result.add(
                        fieldPredicateView.transform(
                            f -> tableAlias(stageIndex) + "." + naming.encloseField(f.identifier().getColumnName()),
                            f -> f.findColumn(project).get().findDatabaseType(),
                            fieldPredicate
                        )
                    );

                }
            }
        }
        return result;
    }

    private static String renderJoin(
        final DatabaseNamingConvention naming,
        final List<SqlStage> sqlStages,
        final List<Stage<?>> stages,
        final int stageIndex
    ) {
        requireNonNull(naming);
        requireNonNull(sqlStages);
        requireNonNegative(stageIndex);
        return renderJoin(naming, sqlStages, stages, stageIndex, stages.get(stageIndex).joinType().get());
    }

    private static String renderJoin(
        final DatabaseNamingConvention naming,
        final List<SqlStage> sqlStages,
        final List<Stage<?>> stages,
        final int stageIndex,
        final JoinType joinType
    ) {
        requireNonNull(naming);
        requireNonNull(sqlStages);
        requireNonNegative(stageIndex);
        final SqlStage sqlStage = sqlStages.get(stageIndex);
        final Stage<?> stage = sqlStage.stage();
        // This might be different for different databse types...
        final StringBuilder sb = new StringBuilder();
        switch (joinType) {
            case CROSS_JOIN:
                sb.append(", ").append(sqlStage.sqlTableReference()).append(" ");
                break;
//            case FULL_OUTER_JOIN:
//                // Most databases do not support this natively so we create a 
//                // UNION between a LEFT JOIN and a RIGHT JOIN instead.
//                sb
//                    .append("(")
//                    .append(renderJoin(naming, sqlStages, stages, stageIndex, JoinType.LEFT_JOIN))
//                    .append(" UNION ")
//                    .append(renderJoin(naming, sqlStages, stages, stageIndex, JoinType.RIGHT_JOIN))
//                    .append(")");
//                break;
            default:
                sb.append(joinType.sql()).append(" ");
                sb.append(sqlStage.sqlTableReference()).append(" ");
                stage.field().ifPresent(field -> {
                    final HasComparableOperators<?, ?> foreignFirstField = stage.foreignField().get();
                    final int foreignStageIndex = stageIndexOf(stages, foreignFirstField);
                    final Stage<?> foreignStage = stages.get(foreignStageIndex);
                    sb.append("ON (");
                    final JoinOperator joinOperator = stage.joinOperator().get();
                     renderPredicate(sb, naming, stageIndex, foreignStageIndex, field, foreignFirstField, joinOperator.sqlOperator());
                     
                     
//                    switch (joinOperator) {
//                        case BETWEEN:
//                        case NOT_BETWEEN: {
//                            renderBetweenOnPredicate(
//                                sb,
//                                naming,
//                                joinOperator,
//                                stageIndex,
//                                foreignStageIndex,
//                                field,
//                                foreignFirstField,
//                                stage.foreignSecondField().get(),
//                                stage.foreignInclusion().get()
//                            );
//                            break;
//                        }
//                        default: {
//                            renderPredicate(sb, naming, stageIndex, foreignStageIndex, field, foreignFirstField, joinOperator.sqlOperator());
//                        }
//                    }


                    sb.append(") ");
                });
                break;
        }
        return sb.toString();
    }

//    private static void renderBetweenOnPredicate(
//        final StringBuilder sb,
//        final DatabaseNamingConvention naming,
//        final JoinOperator joinOperator,
//        final int stageIndex,
//        final int foreignStageIndex,
//        final HasComparableOperators<?, ?> field,
//        final HasComparableOperators<?, ?> foreignFirstField,
//        final HasComparableOperators<?, ?> foreignSecondField,
//        final Inclusion inclusion
//    ) {
//        // Use compisition of >, >=, < and <= to implement inclusion variants
//        if (JoinOperator.NOT_BETWEEN.equals(joinOperator)) {
//            sb.append(" NOT ");
//        }
//        sb.append("(");
//        final String firstOperator = inclusion.isStartInclusive() ? ">=" : ">";
//        final String secondOperator = inclusion.isEndInclusive() ? "<=" : "<";
//        renderPredicate(sb, naming, stageIndex, foreignStageIndex, field, foreignFirstField, firstOperator);
//        sb.append(" AND ");
//        renderPredicate(sb, naming, stageIndex, foreignStageIndex, field, foreignSecondField, secondOperator);
//        sb.append(")");
//    }

    private static void renderPredicate(
        final StringBuilder sb,
        final DatabaseNamingConvention naming,
        final int stageIndex,
        final int foreignStageIndex,
        final HasComparableOperators<?, ?> field,
        final HasComparableOperators<?, ?> foreignField,
        final String operator
    ) {
        sb.append(tableAlias(stageIndex))
            .append(".")
            .append(naming.encloseField(field.identifier().getColumnName()))
            .append(" ")
            .append(operator)
            .append(" ")
            .append(tableAlias(foreignStageIndex))
            .append(".")
            .append(naming.encloseField(foreignField.identifier().getColumnName()));
    }

    private static int stageIndexOf(final List<Stage<?>> stages, HasComparableOperators<?, ?> foreignField) {
        for (int i = 0; i < stages.size(); i++) {
            final Stage<?> stage = stages.get(i);
            final TableIdentifier<?> tableIdentifier = foreignField.identifier().asTableIdentifier();
            if (tableIdentifier.equals(stage.identifier())) {
                return i;
            }
        }
        throw new IllegalStateException(
            "There is not table for " + foreignField.identifier().getTableName()
            + ". These tables are available from prevous join stages:"
            + stages.stream().map(Stage::identifier).map(TableIdentifier::getTableName).collect(joining(", "))
        );
    }

}
