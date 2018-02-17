package com.speedment.runtime.join.internal.pipeline;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.pipeline.JoinType;
import com.speedment.runtime.join.pipeline.OperatorType;
import com.speedment.runtime.join.pipeline.Stage;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <T> entity types for this Stage
 */
public class StageImpl<T> implements Stage<T> {

    private final TableIdentifier<T> identifier;
    private final List<Predicate<? super T>> predicates;
    private final JoinType joinType;
    private final HasComparableOperators<?, ?> otherTableField;
    private final OperatorType operatorType;
    private final HasComparableOperators<? extends T, ?> firstField;
    private final HasComparableOperators<? extends T, ?> secondField;

    public StageImpl(
        final TableIdentifier<T> identifier,
        final List<Predicate<? super T>> predicates,
        final JoinType joinType,
        final HasComparableOperators<?, ?> otherTableField,
        final OperatorType operatorType,
        final HasComparableOperators<? extends T, ?> firstField,
        final HasComparableOperators<? extends T, ?> secondField
    ) {
        this.identifier = requireNonNull(identifier);
        this.predicates = predicates;
        this.joinType = joinType;
        this.otherTableField = otherTableField;
        this.operatorType = operatorType;
        this.firstField = firstField;
        this.secondField = secondField;
    }

    @Override
    public TableIdentifier<T> identifier() {
        return identifier;
    }

    @Override
    public List<Predicate<? super T>> predicates() {
        return Collections.unmodifiableList(predicates);
    }

    @Override
    public Optional<JoinType> joinType() {
        return Optional.ofNullable(joinType);
    }

    @Override
    public Optional<HasComparableOperators<?, ?>> otherTableField() {
        return Optional.ofNullable(otherTableField);
    }

    @Override
    public Optional<OperatorType> operatorType() {
        return Optional.ofNullable(operatorType);
    }

    @Override
    public Optional<HasComparableOperators<? extends T, ?>> firstField() {
        return Optional.ofNullable(firstField);
    }

    @Override
    public Optional<HasComparableOperators<? extends T, ?>> secondField() {
        return Optional.ofNullable(secondField);
    }

    @Override
    public String toString() {
        return "StageImpl{" + "identifier=" + identifier + ", predicates=" + predicates + ", joinType=" + joinType + ", otherTableField=" + otherTableField + ", operatorType=" + operatorType + ", firstField=" + firstField + ", secondField=" + secondField + '}';
    }

}
