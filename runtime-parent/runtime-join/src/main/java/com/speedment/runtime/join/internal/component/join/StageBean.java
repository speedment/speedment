package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.join.stage.JoinOperator;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.internal.stage.StageImpl;
import com.speedment.runtime.join.stage.Stage;
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
    private HasComparableOperators<T, ?> field;
    private JoinOperator joinOperator;
    private HasComparableOperators<?, ?> foreignField;
//    private HasComparableOperators<?, ?> foreignSecondField;
//    private Inclusion foreignInclusion;

    public StageBean(TableIdentifier<T> identifier) {
        this.identifier = requireNonNull(identifier);
        this.predicates = new ArrayList<>();
    }

    public StageBean(TableIdentifier<T> identifier, JoinType joinType) {
        this.identifier = requireNonNull(identifier);
        this.predicates = new ArrayList<>();
        this.joinType = requireNonNull(joinType);
    }

    public StageBean(JoinType joinType, HasComparableOperators<T, ?> field) {
        requireNonNull(field);
        this.identifier = requireNonNull(field.identifier().asTableIdentifier());
        this.predicates = new ArrayList<>();
        this.joinType = requireNonNull(joinType);
        this.field = field;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = requireNonNull(joinType);
    }

    public HasComparableOperators<? extends T, ?> getField() {
        return field;
    }

    public void setField(HasComparableOperators<T, ?> field) {
        this.field = requireNonNull(field);
    }

    public JoinOperator getJoinOperator() {
        return joinOperator;
    }

    public void setJoinOperator(JoinOperator joinOperator) {
        this.joinOperator = requireNonNull(joinOperator);
    }

    public HasComparableOperators<?, ?> getForeignField() {
        return foreignField;
    }

    public void setForeignField(HasComparableOperators<?, ?> foreignFirstField) {
        this.foreignField = requireNonNull(foreignFirstField);
    }

//    public HasComparableOperators<?, ?> getForeignSecondField() {
//        return foreignSecondField;
//    }
//
//    public void setForeignSecondField(HasComparableOperators<?, ?> foreignSecondField) {
//        this.foreignSecondField = requireNonNull(foreignSecondField);
//    }

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

//    public Inclusion getForeignInclusion() {
//        return foreignInclusion;
//    }
//
//    public void setForeignInclusion(Inclusion foreignInclusion) {
//        this.foreignInclusion = requireNonNull(foreignInclusion);
//    }

    public Stage<T> asStage() {
        return Stage.of(
            identifier,
            predicates,
            joinType,
            field,
            joinOperator,
            foreignField
//            foreignSecondField,
//            foreignInclusion
        );
    }

}
