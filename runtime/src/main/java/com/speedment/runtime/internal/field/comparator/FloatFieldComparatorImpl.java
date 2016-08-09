package com.speedment.runtime.internal.field.comparator;

import com.speedment.runtime.field.trait.HasFloatValue;
import java.util.Comparator;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class FloatFieldComparatorImpl<ENTITY, D> implements FloatFieldComparator<ENTITY, D> {
    
    private final HasFloatValue<ENTITY, D> field;
    private boolean reversed;
    
    public FloatFieldComparatorImpl(HasFloatValue<ENTITY, D> field) {
        this.field    = requireNonNull(field);
        this.reversed = false;
    }
    
    @Override
    public HasFloatValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public boolean isReversed() {
        return reversed;
    }
    
    @Override
    public Comparator<ENTITY> reversed() {
        reversed = !reversed;
        return this;
    }
    
    @Override
    public int compare(ENTITY first, ENTITY second) {
        requireNonNulls(first, second);
        final float a = field.getAsFloat(first);
        final float b = field.getAsFloat(second);
        return applyReversed(a - b);
    }
    
    private int applyReversed(float compare) {
        if (compare == 0) {
            return 0;
        } else {
            if (reversed) {
                if (compare > 0) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (compare > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }
}