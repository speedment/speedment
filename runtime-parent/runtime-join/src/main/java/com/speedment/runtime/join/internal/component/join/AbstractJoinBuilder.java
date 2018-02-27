package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasWhere;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.Predicate;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author Per Minborg
 */
abstract class AbstractJoinBuilder<T, SELF> implements HasWhere<T, SELF> {

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

    <T> StageBean<T> addStageBeanOf(TableIdentifier<T> table) {
        return addStageBeanHelper(new StageBean<>(table));
    }

    <T> StageBean<T> addStageBeanOf(TableIdentifier<T> table, JoinType joinType) {
        return addStageBeanHelper(new StageBean<>(table, joinType));
    }

    <T> StageBean<T> addStageBeanOf(JoinType joinType, HasComparableOperators<T, ?> field) {
        return addStageBeanHelper(new StageBean<>(joinType, field));
    }

    <T> StageBean<T> addStageBeanHelper(final StageBean<T> stageBean) {
        requireNonNull(stageBean);
        stageBeans.add((StageBean<?>) stageBean);
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
        return stageBeans.stream()
            .map(StageBean::asStage)
            .collect(collectingAndThen(toList(), Collections::unmodifiableList));

    }

    void assertFieldsAreInJoinTables() throws IllegalStateException {
        final Set<TableIdentifier<?>> tableIdentifiers = stageBeans.stream()
            .map(StageBean::getIdentifier)
            .collect(toSet());

        for (int i = 1; i < stageBeans.size(); i++) {
            final StageBean<?> sb = stageBeans.get(i);
            assertFieldIn(tableIdentifiers, sb.getField(), i);
            assertFieldIn(tableIdentifiers, sb.getForeignFirstField(), i);
            assertFieldIn(tableIdentifiers, sb.getForeignSecondField(), i);
        }
    }

    private void assertFieldIn(Set<TableIdentifier<?>> tableIdentifiers, HasComparableOperators<?, ?> field, int index) {
        if (field != null) {
            if (!tableIdentifiers.contains(field.identifier().asTableIdentifier())) {
                throw new IllegalStateException(
                    "The field " + field.identifier().getColumnName()
                    + " from join stage " + (index + 1)
                    + " is not associated with any of the tables in the join: "
                    + tableIdentifiers.stream().map(TableIdentifier::getTableName).collect(joining(", "))
                );
            }
        }
    }

}
