package com.speedment.runtime.join.internal.stage;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.OperatorType;
import com.speedment.runtime.join.stage.Stage;
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
    private final HasComparableOperators<? extends T, ?> field;
    private final OperatorType operatorType;
    private final HasComparableOperators<?, ?> firstForeignField;
    private final HasComparableOperators<?, ?> secondForeignField;
    private final Inclusion foreignInclusion;

    public StageImpl(
        final TableIdentifier<T> identifier,
        final List<Predicate<? super T>> predicates,
        final JoinType joinType,
        final HasComparableOperators<? extends T, ?> field,
        final OperatorType operatorType,
        final HasComparableOperators<?, ?> firstForeignField,
        final HasComparableOperators<?, ?> secondForeignField,
        final Inclusion foreignInclusion
    ) {
        this.identifier = requireNonNull(identifier);
        this.predicates = predicates; // Nullable
        this.joinType = joinType; // Nullable
        this.field = field; // Nullable
        this.operatorType = operatorType; // Nullable
        this.firstForeignField = firstForeignField; // Nullable
        this.secondForeignField = secondForeignField; // Nullable
        this.foreignInclusion = foreignInclusion; // Nullable
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
    public Optional<HasComparableOperators<? extends T, ?>> field() {
        return Optional.ofNullable(field);
    }

    @Override
    public Optional<OperatorType> operatorType() {
        return Optional.ofNullable(operatorType);
    }

    @Override
    public Optional<HasComparableOperators<?, ?>> firstForeignField() {
        return Optional.ofNullable(firstForeignField);
    }

    @Override
    public Optional<HasComparableOperators<?, ?>> secondForeignField() {
        return Optional.ofNullable(secondForeignField);
    }

    @Override
    public Optional<Inclusion> foreignInclusion() {
        return Optional.ofNullable(foreignInclusion);
    }

    @Override
    public String toString() {
        return "StageImpl{" + "identifier=" + identifier + ", predicates=" + predicates + ", joinType=" + joinType + ", field=" + field + ", operatorType=" + operatorType + ", firstForeignField=" + firstForeignField + ", secondForeignField=" + secondForeignField + '}';
    }

}
