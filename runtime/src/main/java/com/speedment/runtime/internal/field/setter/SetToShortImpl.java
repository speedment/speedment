package com.speedment.runtime.internal.field.setter;

import com.speedment.runtime.field.method.SetToShort;
import com.speedment.runtime.field.trait.HasShortValue;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code short}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class SetToShortImpl<ENTITY, D> implements SetToShort<ENTITY, D> {
    
    private final HasShortValue<ENTITY, D> field;
    private final short newValue;
    
    public SetToShortImpl(HasShortValue<ENTITY, D> field, short newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasShortValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public short getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsShort(entity, newValue);
    }
}