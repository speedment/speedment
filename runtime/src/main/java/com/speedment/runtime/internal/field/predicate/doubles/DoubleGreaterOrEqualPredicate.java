package com.speedment.runtime.internal.field.predicate.doubles;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasDoubleValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class DoubleGreaterOrEqualPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Double, HasDoubleValue<ENTITY, D>> implements Tuple1<Double> {
    
    private final double value;
    
    public DoubleGreaterOrEqualPredicate(HasDoubleValue<ENTITY, D> field, double value) {
        super(PredicateType.GREATER_OR_EQUAL, field, entity -> field.getAsDouble(entity) == value);
        this.value = value;
    }
    
    @Override
    public Double get0() {
        return value;
    }
}