package com.speedment.runtime.internal.field.setter;

import com.speedment.runtime.field.method.SetToFloat;
import com.speedment.runtime.field.trait.HasFloatValue;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code float}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class SetToFloatImpl<ENTITY, D> implements SetToFloat<ENTITY, D> {
    
    private final HasFloatValue<ENTITY, D> field;
    private final float newValue;
    
    public SetToFloatImpl(HasFloatValue<ENTITY, D> field, float newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasFloatValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public float getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsFloat(entity, newValue);
    }
}