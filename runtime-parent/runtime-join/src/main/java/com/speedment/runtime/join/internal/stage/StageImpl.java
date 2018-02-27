package com.speedment.runtime.join.internal.stage;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.JoinOperator;
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
    private final HasComparableOperators<T, ?> field;
    private final JoinOperator joinOperator;
    private final HasComparableOperators<?, ?> foreignFirstField;
    private final HasComparableOperators<?, ?> foreignSecondField;
    private final Inclusion foreignInclusion;

    public StageImpl(
        final TableIdentifier<T> identifier,
        final List<Predicate<? super T>> predicates,
        final JoinType joinType,
        final HasComparableOperators<T, ?> field,
        final JoinOperator joinOperator,
        final HasComparableOperators<?, ?> foreignFirstField,
        final HasComparableOperators<?, ?> foreignSecondField,
        final Inclusion foreignInclusion
    ) {
        this.identifier = requireNonNull(identifier);
        this.predicates = predicates; // Nullable
        this.joinType = joinType; // Nullable
        this.field = field; // Nullable
        this.joinOperator = joinOperator; // Nullable
        this.foreignFirstField = foreignFirstField; // Nullable
        this.foreignSecondField = foreignSecondField; // Nullable
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
    public Optional<HasComparableOperators<T, ?>> field() {
        return Optional.ofNullable(field);
    }

    @Override
    public Optional<JoinOperator> joinOperator() {
        return Optional.ofNullable(joinOperator);
    }

    @Override
    public Optional<HasComparableOperators<?, ?>> foreignFirstField() {
        return Optional.ofNullable(foreignFirstField);
    }

    @Override
    public Optional<HasComparableOperators<?, ?>> foreignSecondField() {
        return Optional.ofNullable(foreignSecondField);
    }

    @Override
    public Optional<Inclusion> foreignInclusion() {
        return Optional.ofNullable(foreignInclusion);
    }

    @Override
    public String toString() {
        return "StageImpl{" + "identifier=" + identifier + ", predicates=" + predicates + ", joinType=" + joinType + ", field=" + field + ", operatorType=" + joinOperator + ", firstForeignField=" + foreignFirstField + ", secondForeignField=" + foreignSecondField + '}';
    }

}
