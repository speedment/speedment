package com.speedment.runtime.internal.field.setter;

import com.speedment.runtime.field.method.SetToInt;
import com.speedment.runtime.field.trait.HasIntValue;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code int}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class SetToIntImpl<ENTITY, D> implements SetToInt<ENTITY, D> {
    
    private final HasIntValue<ENTITY, D> field;
    private final int newValue;
    
    public SetToIntImpl(HasIntValue<ENTITY, D> field, int newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasIntValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public int getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsInt(entity, newValue);
    }
}