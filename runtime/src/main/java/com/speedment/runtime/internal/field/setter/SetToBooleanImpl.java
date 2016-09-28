package com.speedment.runtime.internal.field.setter;

import com.speedment.runtime.field.method.SetToBoolean;
import com.speedment.runtime.field.trait.HasBooleanValue;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code boolean}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class SetToBooleanImpl<ENTITY, D> implements SetToBoolean<ENTITY, D> {
    
    private final HasBooleanValue<ENTITY, D> field;
    private final boolean newValue;
    
    public SetToBooleanImpl(HasBooleanValue<ENTITY, D> field, boolean newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasBooleanValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public boolean getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsBoolean(entity, newValue);
    }
}