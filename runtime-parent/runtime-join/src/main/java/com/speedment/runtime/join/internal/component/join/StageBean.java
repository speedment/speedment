package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.join.pipeline.OperatorType;
import com.speedment.runtime.join.pipeline.JoinType;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.internal.pipeline.StageImpl;
import com.speedment.runtime.join.pipeline.Stage;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 * Bean used to parse table info during join building.
 *
 * @author Per Minborg
 * @param <T> entity type of the table
 */
public final class StageBean<T> {

    private final TableIdentifier<T> identifier;
    private final List<Predicate<? super T>> predicates;
    private JoinType joinType;
    private HasComparableOperators<?, ?> otherTableField;
    private OperatorType operatorType;
    private HasComparableOperators<? extends T, ?> firstField;
    private HasComparableOperators<? extends T, ?> secondField;

    public StageBean(TableIdentifier<T> identifier) {
        this.identifier = requireNonNull(identifier);
        this.predicates = new ArrayList<>();
    }

    public StageBean(TableIdentifier<T> identifier, JoinType joinType) {
        this.identifier = requireNonNull(identifier);
        this.predicates = new ArrayList<>();
        this.joinType = requireNonNull(joinType);
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = requireNonNull(joinType);
    }

    public HasComparableOperators<?, ?> getOtherTableField() {
        return otherTableField;
    }

    public void setOtherTableField(HasComparableOperators<?, ?> otherTableField) {
        this.otherTableField = requireNonNull(otherTableField);
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = requireNonNull(operatorType);
    }

    public HasComparableOperators<? extends T, ?> getFirstField() {
        return firstField;
    }

    public void setFirstField(HasComparableOperators<? extends T, ?> firstTableField) {
        this.firstField = requireNonNull(firstTableField);
    }

    public HasComparableOperators<? extends T, ?> getSecondField() {
        return secondField;
    }

    public void setSecondField(HasComparableOperators<? extends T, ?> secondTableField) {
        this.secondField = requireNonNull(secondTableField);
    }

    public TableIdentifier<T> getIdentifier() {
        return identifier;
    }

    /**
     * This list is intentionally exposed for mutable operation.
     *
     * @return a mutable list
     */
    public List<Predicate<? super T>> getPredicates() {
        return predicates;
    }

    public Stage<T> asStage() {
        return new StageImpl<>(
            identifier,
            predicates,
            joinType,
            otherTableField,
            operatorType,
            firstField,
            secondField
        );
    }

}
