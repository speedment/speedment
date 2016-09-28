package com.speedment.runtime.internal.field.setter;

import com.speedment.runtime.field.method.SetToByte;
import com.speedment.runtime.field.trait.HasByteValue;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code byte}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class SetToByteImpl<ENTITY, D> implements SetToByte<ENTITY, D> {
    
    private final HasByteValue<ENTITY, D> field;
    private final byte newValue;
    
    public SetToByteImpl(HasByteValue<ENTITY, D> field, byte newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasByteValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public byte getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsByte(entity, newValue);
    }
}