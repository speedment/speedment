/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasWhere;

import java.util.*;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
abstract class AbstractJoinBuilder<T, SELF> implements HasWhere<T, SELF> {

    private final static Logger LOGGER_JOIN =
        LoggerManager.getLogger(ApplicationBuilder.LogType.JOIN.getLoggerName());

    private final JoinStreamSupplierComponent streamSupplier;
    private final List<StageBean<?>> stageBeans;
    private final StageBean<T> stageBean;

    AbstractJoinBuilder(
        final JoinStreamSupplierComponent streamSupplier,
        final TableIdentifier<T> initialTable
    ) {
        this.streamSupplier = requireNonNull(streamSupplier);
        this.stageBeans = new ArrayList<>();
        this.stageBean = AbstractJoinBuilder.this.addStageBeanOf(requireNonNull(initialTable));
    }

    AbstractJoinBuilder(
        final AbstractJoinBuilder<?, ?> previous,
        final StageBean<T> stageBean
    ) {
        requireNonNull(previous);
        this.streamSupplier = previous.streamSuppler();
        this.stageBeans = previous.stageBeans();
        this.stageBean = requireNonNull(stageBean);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SELF where(Predicate<? super T> predicate) {
        addPredicate(predicate);
        return (SELF) this;
    }

    <U> StageBean<U> addStageBeanOf(TableIdentifier<U> table) {
        return addStageBeanHelper(new StageBean<>(table));
    }

    <U> StageBean<U> addStageBeanOf(TableIdentifier<U> table, JoinType joinType) {
        return addStageBeanHelper(new StageBean<>(table, joinType));
    }

    <U> StageBean<U> addStageBeanOf(JoinType joinType, HasComparableOperators<U, ?> field) {
        return addStageBeanHelper(new StageBean<>(joinType, field));
    }

    <U> StageBean<U> addStageBeanHelper(final StageBean<U> stageBean) {
        requireNonNull(stageBean);
        stageBeans.add(stageBean);
        return stageBean;
    }

    /**
     * Returns a mutable list of mutable StageBean objects.
     *
     * @return a mutable list of mutable StageBean objects
     */
    List<StageBean<?>> stageBeans() {
        return stageBeans;
    }

    StageBean<T> stageBean() {
        return stageBean;
    }

    void addPredicate(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        if (!(predicate instanceof FieldPredicate)) {
            throw new IllegalArgumentException(
                "The predicate " + predicate + " for join stage "
                + stageBeans.size() + " does not implement "
                + FieldPredicate.class.getName()
                + ". Only Speedment predicates can be used for join operations"
                + " (and thus no anonymous lambdas)."
            );
        }
        stageBean.getPredicates().add(predicate);
    }

    JoinStreamSupplierComponent streamSuppler() {
        return streamSupplier;
    }

    /**
     * Creates and returns a new unmodifiable list of immutable Stage object
     *
     * @return a new unmodifiable list of immutable Stage object
     */
    List<Stage<?>> stages() {
        resolveStages();
        return stageBeans.stream()
            .map(StageBean::asStage)
            .collect(collectingAndThen(toList(), Collections::unmodifiableList));

    }

//    void assertFieldsAreInJoinTables() throws IllegalStateException {
//        final Set<TableIdentifier<?>> tableIdentifiers = stageBeans.stream()
//            .map(StageBean::getIdentifier)
//            .collect(toSet());
//
//        for (int i = 1; i < stageBeans.size(); i++) {
//            final StageBean<?> sb = stageBeans.get(i);
//            assertFieldIn(tableIdentifiers, sb.getField(), i);
//            assertFieldIn(tableIdentifiers, sb.getForeignField(), i);
//            //assertFieldIn(tableIdentifiers, sb.getForeignSecondField(), i);
//        }
//    }
//
//    private void assertFieldIn(Set<TableIdentifier<?>> tableIdentifiers, HasComparableOperators<?, ?> field, int index) {
//        if (field != null) {
//            if (!tableIdentifiers.contains(field.identifier().asTableIdentifier())) {
//                throw new IllegalStateException(
//                    "The field " + field.identifier().getColumnId()
//                    + " from join stage " + (index + 1)
//                    + " is not associated with any of the tables in the join: "
//                    + tableIdentifiers.stream().map(TableIdentifier::getTableId).collect(joining(", "))
//                );
//            }
//        }
//    }


    /**
     * Calculates which reference to use taking "as()" columns into account.
     */
    void resolveStages() {
        for (int i = 0; i < stageBeans.size(); i++) {
            final StageBean<?> stageBean = stageBeans.get(i);
            final HasComparableOperators<?, ?> foreignField = stageBean.getForeignField();
            if (foreignField == null) {
                stageBean.setReferencedStage(-1);
            } else {
                stageBean.setReferencedStage(stageIndexOf(stageBeans, foreignField, i));
            }
        }
        if (LOGGER_JOIN.getLevel().isEqualOrHigherThan(Level.DEBUG)) {
            final StringBuilder sb = new StringBuilder();
            sb.append("Resolving join with ").append(stageBeans.size()).append(" stages:");
            sb.append(
                format("%n%2s %-32s %-12s %2s %-32s %-16s %-12s %-16s",
                    "#", "Table Identifier", "Join Type", "R#", "Referenced Table Identifier", "Field", "Operation", "Referenced Field")
            );
            for (int i = 0; i < stageBeans.size(); i++) {
                final StageBean<?> stageBean = stageBeans.get(i);
                final Optional<StageBean<?>> referencedStageBean = Optional.of(stageBean.getReferencedStage())
                    .filter(rs -> rs != -1)
                    .map(stageBeans::get);
                sb.append(
                    format(
                        "%n%2d %-32s %-12s %2d %-32s %-16s %-12s %-16s",
                        i,
                        stageBean.getIdentifier(),
                        stageBean.getJoinType(),
                        stageBean.getReferencedStage(),
                        referencedStageBean.map(StageBean::getIdentifier).orElse(null),
                        Optional.ofNullable(stageBean.getField()).map(Field::identifier).map(ColumnIdentifier::getColumnId).orElse("null"),
                        stageBean.getJoinOperator(),
                        Optional.ofNullable(stageBean.getForeignField()).map(Field::identifier).map(ColumnIdentifier::getColumnId).orElse("null")
                    )
                );
            }
            LOGGER_JOIN.debug(sb.toString());
        }
    }

    private static int stageIndexOf(
        final List<StageBean<?>> stages,
        final HasComparableOperators<?, ?> foreignField,
        final int index
    ) {
        // First check if there is exactly one that is matching
        final Set<Integer> matches = new LinkedHashSet<>();
        final String foreignTableIdentifierString = tableIdentifierString(foreignField);

        for (int i = 0; i < stages.size(); i++) {
            final StageBean<?> stage = stages.get(i);
            final String fieldTableIdentifierString = tableTdentifierString(stage);
            if (fieldTableIdentifierString.equals(foreignTableIdentifierString)) {
                matches.add(i);
            }
        }
        if (matches.size() > 1) {
            throw new IllegalStateException(
                "The identifier " + foreignTableIdentifierString + " for stage index " + index + " is ambiguous. "
                    + "There are matching table identifiers for stage indexes " + matches
                    + ". These table identifiers are available from previous join stages: "
                    + stages.stream().map(AbstractJoinBuilder::tableTdentifierString).collect(joining(", "))
            );
        } else if (matches.size() == 1) {
            return matches.iterator().next();
        }
        throw new IllegalStateException(
            "There is no table for table identifier \"" + tableIdentifierString(foreignField) + "\" for stage index " + index + " of [0, " + (stages.size() - 1) + "]"
                + ". These table identifiers are available from previous join stages: "
                + stages.stream().map(AbstractJoinBuilder::tableTdentifierString).collect(joining(", "))
        );

//        for (int i = 0; i < stages.size(); i++) {
//            final Stage<?> stage = stages.get(i);
//            final TableIdentifier<?> tableIdentifier = foreignField.identifier().asTableIdentifier();
//            if (tableIdentifier.equals(stage.identifier())) {
//                return i;
//            }
//        }

    }

    private static boolean hasAlias(HasComparableOperators<?, ?> field) {
        return !field.tableAlias().equals(field.identifier().getTableId());
    }

    private static <T> String tableTdentifierString(StageBean<?> stage) {
        if (stage.getField() != null) {
            return tableIdentifierString(stage.getField());
        } else {
            return tableTdentifierString(stage.getIdentifier());
        }
    }

    private static <T> String tableTdentifierString(TableIdentifier<T> tableIdentifier) {
        return tableIdentifier.getDbmsId() + "." +
            tableIdentifier.getSchemaId() + "." +
            tableIdentifier.getTableId(); // Take tableAlias into account
    }

    private static <T> String tableIdentifierString(HasComparableOperators<T, ?> foreignField) {
        if (hasAlias(foreignField)) {
            return foreignField.tableAlias(); // The alias replaces both schema and table.
        }
        final TableIdentifier<T> tableIdentifier = foreignField.identifier().asTableIdentifier();
        return tableIdentifier.getDbmsId() + "." +
            tableIdentifier.getSchemaId() + "." +
            tableIdentifier.getTableId();
    }

    private static String references(final List<StageBean<?>> stages, Set<Integer> set) {
        return set.stream()
            .map(stages::get)
            .map(Object::toString)
            .collect(joining(", ", "[", "]"));
    }



}
