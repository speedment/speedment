package com.speedment.runtime.internal.field.setter;

import com.speedment.runtime.field.method.SetToLong;
import com.speedment.runtime.field.trait.HasLongValue;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code long}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class SetToLongImpl<ENTITY, D> implements SetToLong<ENTITY, D> {
    
    private final HasLongValue<ENTITY, D> field;
    private final long newValue;
    
    public SetToLongImpl(HasLongValue<ENTITY, D> field, long newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasLongValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public long getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsLong(entity, newValue);
    }
}