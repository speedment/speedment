package com.speedment.orm.field.doubles;

import com.speedment.orm.field.BasePredicate;
import com.speedment.orm.field.Operator;
import com.speedment.orm.field.PredicateBuilder;
import com.speedment.orm.field.StandardBinaryOperator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 */
public class DoubleBinaryPredicateBuilder<ENTITY> extends BasePredicate<ENTITY> implements Predicate<ENTITY>, PredicateBuilder {

    private final DoubleField field;
    private final double value;
    private final StandardBinaryOperator binaryOperator;

    public DoubleBinaryPredicateBuilder(
            DoubleField field,
            double value,
            StandardBinaryOperator binaryOperator
    ) {
        this.field = Objects.requireNonNull(field);
        this.value = value;
        this.binaryOperator = Objects.requireNonNull(binaryOperator);
    }

    @Override
    public boolean test(ENTITY entity) {
        return test(Double.compare(field.getFrom(entity), value));
    }

    public boolean test(int compare) {
        return binaryOperator.getComparator().test(compare);
    }

    @Override
    public DoubleField getField() {
        return field;
    }

    @Override
    public Operator getOperator() {
        return binaryOperator;
    }

}
