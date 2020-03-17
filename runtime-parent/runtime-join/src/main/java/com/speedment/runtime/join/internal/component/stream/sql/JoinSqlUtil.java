/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.join.internal.component.stream.sql;

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
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.CombinedPredicate;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.internal.component.stream.SqlAdapterMapper;
import com.speedment.runtime.join.stage.JoinOperator;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.common.invariant.IntRangeUtil.requireNonNegative;
import static com.speedment.runtime.join.JoinComponentUtil.MAX_DEGREE;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
final class JoinSqlUtil {

    private JoinSqlUtil() {}

    static Dbms requireSameDbms(
        final Project project,
        final List<Stage<?>> stages
    ) {
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

    static <T> SqlFunction<ResultSet, T> resultSetMapper(
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
        if (stage.joinType().isPresent() && stage.joinType().orElseThrow(() -> newNoSuchElementException(stage)).isNullableSelf()) {
            nullOffset = findNullOffset(table, stage, stage.field().orElseThrow(() -> new NoSuchElementException("field is missing in stage" + stage)));
        }

        // Check if another (RIGHT JOIN) stage renders an on-field in this stage to be nullable
        // No use to check if we already know
        if (nullOffset == -1) {
            nullOffset = checkOther(stages, stageIndex, stage, table, nullOffset);
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
            .getColumnId();

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


    private static <T> int checkOther(List<Stage<?>> stages, int stageIndex, Stage<T> stage, Table table, int nullOffset) {
        final TableIdentifier<?> thisId = stage.identifier();
        for (int i = 0; i < stages.size(); i++) {
            if (stageIndex == i) {
                // Ignore this stage
                continue;
            }
            final Stage<?> otherStage = stages.get(i);
            if (otherStage.joinType().isPresent() && otherStage.joinType().orElseThrow(() -> newNoSuchElementException(otherStage)).isNullableOther()) {
                final HasComparableOperators<?, ?> otherStageForeignField = otherStage.foreignField().orElseThrow(() -> new NoSuchElementException("Foreign field is missing in other stage " + otherStage));
                final TableIdentifier<?> referencedId = otherStageForeignField.identifier().asTableIdentifier();
                if (thisId.equals(referencedId)) {
                    nullOffset = findNullOffset(table, otherStage, otherStageForeignField);
                    // If we have a between operation where there is another field pointed, my
                    // belief is that we can safely ignore that because both fields will be null and we
                    // only need to detect one
                }
            }
        }
        return nullOffset;
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

    static <T> Stream<T> stream(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final Project project,
        final List<Stage<?>> stages,
        final SqlFunction<ResultSet, T> rsMapper,
        final boolean allowStreamIteratorAndSpliterator
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

        final StringBuilder predicateSql = new StringBuilder();
        final List<Object> values = new ArrayList<>(); // Must be a mutable list

        // The predicates connected to each specific table is rendered
        // at the end of the query.
        renderPredicates(sqlInfo, stages, predicateSql, values);

        if (predicateSql.length()>0) {
            sb.append(" WHERE ").append(predicateSql);
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

        return new InitialJoinStream<>(asynchronousQueryResult, sqlInfo, allowStreamIteratorAndSpliterator);
    }

    private static final String[] ALIASES = IntStream.range(0, MAX_DEGREE)
            .mapToObj(i -> Character.toString((char) ('A' + i)))
            .toArray(String[]::new);

    static String tableAlias(int index) {
        return ALIASES[index];
    }

    private static void renderPredicates(
        final SqlInfo sqlInfo,
        final List<Stage<?>> stages,
        final StringBuilder sql,
        final List<Object> values
    ) {
        requireNonNull(sqlInfo);
        requireNonNull(stages);
        requireNonNull(sql);
        requireNonNull(values);

        int cnt = 0;
        for (int stageIndex = 0; stageIndex < stages.size(); stageIndex++) {
            final Stage<?> stage = stages.get(stageIndex);
            cnt = renderPredicate(sqlInfo, sql, values, cnt, stageIndex, stage);
        }
    }

    private static int renderPredicate(SqlInfo sqlInfo, StringBuilder sql, List<Object> values, int cnt, int stageIndex, Stage<?> stage) {
        if (!stage.predicates().isEmpty()) {
            for (int j = 0; j < stage.predicates().size(); j++) {
                final Predicate<?> predicate = stage.predicates().get(j);
                if (predicate instanceof FieldPredicate && ((FieldPredicate) predicate).getPredicateType() == PredicateType.ALWAYS_TRUE) {
                    // Remove redundant ALWAYS_TRUE predicates
                    continue;
                }
                if (cnt++ != 0) {
                    sql.append(" AND ");
                }
                renderPredicateHelper(sqlInfo, stage, stageIndex, sql, values, predicate);
            }
        }
        return cnt;
    }

    // See StreamTerminatorUtil::renderSqlWhereHelper for regular streams

    private static <ENTITY> void renderPredicateHelper(
        final SqlInfo sqlInfo,
        final Stage<?> stage,
        final int stageIndex,
        final StringBuilder sql,
        final List<Object> values,
        final Predicate<ENTITY> predicate
    ) {
        requireNonNull(sqlInfo);
        requireNonNull(stage);
        requireNonNegative(stageIndex);
        requireNonNull(sql);
        requireNonNull(values);
        requireNonNull(predicate);

        if (predicate instanceof FieldPredicate) {
            final FieldPredicate<ENTITY> fieldPredicate = (FieldPredicate<ENTITY>) predicate;
            final SqlPredicateFragment fragment = sqlInfo.fieldPredicateView().transform(
                f -> tableAlias(stageIndex) + "." + sqlInfo.namingConvention().encloseField(f.identifier().getColumnId()),
                f -> f.findColumn(sqlInfo.project()).get().findDatabaseType(),
                fieldPredicate
            );
            final Field<ENTITY> referenceFieldTrait = fieldPredicate.getField();
            @SuppressWarnings("unchecked")
            final TypeMapper<Object, Object> tm = (TypeMapper<Object, Object>) referenceFieldTrait.typeMapper();

            sql.append(fragment.getSql());
            fragment.objects().map(tm::toDatabaseType).forEachOrdered(values::add);
        } else if (predicate instanceof CombinedPredicate) {
            final CombinedPredicate<ENTITY> combinedPredicate = (CombinedPredicate<ENTITY>) predicate;
            final StringBuilder internalSql = new StringBuilder();
            final List<Object> internalValues = new ArrayList<>();
            final AtomicInteger cnt = new AtomicInteger();
            combinedPredicate.stream().forEachOrdered(internalPredicate -> {
                if (cnt.getAndIncrement() != 0) {
                    internalSql.append(" ").append(combinedPredicate.getType().toString()).append(" ");
                }
                @SuppressWarnings("unchecked")
                final Predicate<ENTITY> castedInternalPredicate = (Predicate<ENTITY>) internalPredicate;
                renderPredicateHelper(
                    sqlInfo,
                    stage,
                    stageIndex,
                    internalSql,
                    internalValues,
                    castedInternalPredicate
                );
            });
            sql.append("(").append(internalSql).append(")");
            values.addAll(internalValues);
        } else {
            throw new IllegalArgumentException("A predicate that is nether an instanceof FieldPredicate nor CombinedPredicate was given:" + predicate.toString());
        }

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
        final Stage<?> stage = stages.get(stageIndex);
        return renderJoin(naming, sqlStages, stages, stageIndex, stage.joinType().orElseThrow(() -> newNoSuchElementException(stage)));
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
        // This might be different for different database types...
        final StringBuilder sb = new StringBuilder();

        if (joinType == JoinType.CROSS_JOIN) {
            sb.append(", ").append(sqlStage.sqlTableReference()).append(" ");
        } else {
            sb.append(joinType.sql()).append(" ");
            sb.append(sqlStage.sqlTableReference()).append(" ");
            stage.field().ifPresent(field -> {
                final HasComparableOperators<?, ?> foreignFirstField = stage.foreignField().get();
                final int foreignStageIndex = stage.referencedStage(); //stageIndexOf(stages, foreignFirstField);
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
            .append(naming.encloseField(field.identifier().getColumnId()))
            .append(" ")
            .append(operator)
            .append(" ")
            .append(tableAlias(foreignStageIndex))
            .append(".")
            .append(naming.encloseField(foreignField.identifier().getColumnId()));
    }

//    private static int stageIndexOf(final List<Stage<?>> stages, HasComparableOperators<?, ?> foreignField) {
//        // First check if there is exactly one that is matching
//        final Set<Integer> matches = new LinkedHashSet<>();
//        final String foreignIdentifierString = aliasIdentifierString(foreignField);
//
//        for (int i = 0; i < stages.size(); i++) {
//            final Stage<?> stage = stages.get(i);
//            if (stage.field().isPresent()) {
//                final String fieldIdentifierString = aliasIdentifierString(stage.field().get());
//                if (fieldIdentifierString.equals(foreignIdentifierString)) {
//                    matches.add(i);
//                }
//            } else {
//                final TableIdentifier<?> tableIdentifier = foreignField.identifier().asTableIdentifier();
//                if (tableIdentifier.equals(stage.identifier()) && !hasAlias(foreignField)) {
//                    matches.add(i);
//                }
//            }
//
//        }
//        if (matches.size() > 1) {
//            throw new IllegalStateException(
//                "The identifier " + foreignIdentifierString + " is ambiguous. "
//                    + "There are matching entries for stages " + matches + " "
//                + references(stages, matches)
//            );
//        } else if (matches.size() == 1) {
//            return matches.iterator().next();
//        }
//        throw new IllegalStateException(
//            "There is no table for " + foreignField.identifier().getTableId()
//                + ". These tables are available from previous join stages:"
//                + stages.stream().map(Stage::identifier).map(TableIdentifier::getTableId).collect(joining(", "))
//        );
//
//    }

//    private static boolean hasAlias(HasComparableOperators<?, ?> field) {
//        return !field.label().equals(field.identifier().getColumnId());
//    }
//
//    private static <T> String aliasIdentifierString(HasComparableOperators<T, ?> foreignField) {
//        final TableIdentifier<T> tableIdentifier = foreignField.identifier().asTableIdentifier();
//        return tableIdentifier.getDbmsId() + "." +
//            tableIdentifier.getSchemaId() + "." +
//            tableIdentifier.getTableId() + "." +
//            foreignField.label(); // Take "as()" into account
//    }
//
//    private static String references(final List<Stage<?>> stages, Set<Integer> set) {
//        return set.stream()
//            .map(stages::get)
//            .map(Object::toString)
//            .collect(joining(", ", "[", "]"));
//    }

    private static NoSuchElementException newNoSuchElementException(Stage<?> stage) {
        return new NoSuchElementException("Stage " + stage + " did not have a join type");
    }
}