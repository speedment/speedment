package com.speedment.runtime.internal.field.predicate.doubles;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasDoubleValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import java.util.Set;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of doubles.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class DoubleInPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Double, HasDoubleValue<ENTITY, D>> implements Tuple1<Set<Double>> {
    
    private final Set<Double> set;
    
    public DoubleInPredicate(HasDoubleValue<ENTITY, D> field, Set<Double> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsDouble(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Double> get0() {
        return set;
    }
}