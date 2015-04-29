package com.speedment.orm.field;

import com.speedment.orm.field.CombinedBasePredicate.AndCombinedBasePredicate;
import com.speedment.orm.field.CombinedBasePredicate.OrCombinedBasePredicate;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 */
public abstract class BasePredicate<T> implements Predicate<T> {

    private boolean negated;

    public BasePredicate() {
        negated = false;
    }

    @Override
    public Predicate<T> and(Predicate<? super T> other) {
        return new AndCombinedBasePredicate<>(this, other);
    }

    @Override
    public Predicate<T> or(Predicate<? super T> other) {
        return new OrCombinedBasePredicate<>(this, other);
    }

    @Override
    public BasePredicate<T> negate() {
        negated = !negated;
        return this;
    }

}
