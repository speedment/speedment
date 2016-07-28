package com.speedment.runtime.internal.field.predicate.bytes;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasByteValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ByteGreaterThanPredicate<ENTITY, D>  extends AbstractFieldPredicate<ENTITY, HasByteValue<ENTITY, D>> implements Tuple1<Byte> {
    
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