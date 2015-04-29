package com.speedment.orm.field.reference;

import com.speedment.orm.field.BasePredicate;
import com.speedment.orm.field.Operator;
import com.speedment.orm.field.PredicateBuilder;
import com.speedment.orm.field.StandardUnaryOperator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 * @param <V> Value type
 */
public class UnaryPredicateBuilder<ENTITY, V> extends BasePredicate<ENTITY> implements Predicate<ENTITY>, PredicateBuilder {

    private final ReferenceField field;
    private final StandardUnaryOperator unaryOperator;

    public UnaryPredicateBuilder(
            ReferenceField field,
            StandardUnaryOperator unaryOperator
    ) {
        this.field = Objects.requireNonNull(field);
        this.unaryOperator = Objects.requireNonNull(unaryOperator);
    }

    @Override
    public boolean test(ENTITY entity) {
        return unaryOperator.getComparator().test(field.getFrom(entity));
    }

    @Override
    public ReferenceField getField() {
        return field;
    }

    @Override
    public Operator getOperator() {
        return unaryOperator;
    }

}
