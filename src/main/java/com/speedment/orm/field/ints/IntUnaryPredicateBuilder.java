package com.speedment.orm.field.ints;

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
 */
public class IntUnaryPredicateBuilder<ENTITY> extends BasePredicate<ENTITY> implements Predicate<ENTITY>, PredicateBuilder {

    private final IntField field;
    private final StandardUnaryOperator unaryOperator;

    public IntUnaryPredicateBuilder(
            IntField field,
            StandardUnaryOperator unaryOperator
    ) {
        this.field = Objects.requireNonNull(field);
        this.unaryOperator = Objects.requireNonNull(unaryOperator);
    }

    @Override
    public boolean test(ENTITY t) {
        return unaryOperator.getComparator().test(getField().getFrom(t));
    }

    @Override
    public IntField getField() {
        return field;
    }

    @Override
    public Operator getOperator() {
        return unaryOperator;
    }

}
