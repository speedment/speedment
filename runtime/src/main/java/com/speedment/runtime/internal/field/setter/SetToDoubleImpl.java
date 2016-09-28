package com.speedment.runtime.internal.field.setter;

import com.speedment.runtime.field.method.SetToDouble;
import com.speedment.runtime.field.trait.HasDoubleValue;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code double}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class SetToDoubleImpl<ENTITY, D> implements SetToDouble<ENTITY, D> {
    
    private final HasDoubleValue<ENTITY, D> field;
    private final double newValue;
    
    public SetToDoubleImpl(HasDoubleValue<ENTITY, D> field, double newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasDoubleValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public double getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsDouble(entity, newValue);
    }
}