package com.speedment.runtime.internal.field.predicate.floats;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasFloatValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import javax.annotation.Generated;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class FloatGreaterThanPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Float, HasFloatValue<ENTITY, D>> implements Tuple1<Float> {
    
    private final float value;
    
    public FloatGreaterThanPredicate(HasFloatValue<ENTITY, D> field, float value) {
        super(PredicateType.GREATER_THAN, field, entity -> field.getAsFloat(entity) >= value);
        this.value = value;
    }
    
    @Override
    public Float get0() {
        return value;
    }
}