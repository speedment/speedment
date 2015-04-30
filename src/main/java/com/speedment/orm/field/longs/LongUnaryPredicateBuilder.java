package com.speedment.orm.field.longs;

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
public class LongUnaryPredicateBuilder<ENTITY> extends BasePredicate<ENTITY> implements Predicate<ENTITY>, PredicateBuilder {

    private final LongField field;
    private final StandardUnaryOperator unaryOperator;

    public LongUnaryPredicateBuilder(
            LongField field,
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
    public LongField getField() {
        return field;
    }

    @Override
    public Operator getOperator() {
        return unaryOperator;
    }

}
