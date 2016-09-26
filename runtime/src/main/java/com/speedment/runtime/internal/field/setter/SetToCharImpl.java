package com.speedment.runtime.internal.field.setter;

import com.speedment.runtime.field.method.SetToChar;
import com.speedment.runtime.field.trait.HasCharValue;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code char}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class SetToCharImpl<ENTITY, D> implements SetToChar<ENTITY, D> {
    
    private final HasCharValue<ENTITY, D> field;
    private final char newValue;
    
    public SetToCharImpl(HasCharValue<ENTITY, D> field, char newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasCharValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public char getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsChar(entity, newValue);
    }
}