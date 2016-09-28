package com.speedment.runtime.internal.field.predicate.bytes;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasByteValue;
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
public final class ByteGreaterThanPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Byte, HasByteValue<ENTITY, D>> implements Tuple1<Byte> {
    
    private final byte value;
    
    public ByteGreaterThanPredicate(HasByteValue<ENTITY, D> field, byte value) {
        super(PredicateType.GREATER_THAN, field, entity -> field.getAsByte(entity) >= value);
        this.value = value;
    }
    
    @Override
    public Byte get0() {
        return value;
    }
}