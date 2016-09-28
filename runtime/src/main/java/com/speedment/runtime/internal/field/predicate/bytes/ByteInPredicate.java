package com.speedment.runtime.internal.field.predicate.bytes;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasByteValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import java.util.Set;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of bytes.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class ByteInPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Byte, HasByteValue<ENTITY, D>> implements Tuple1<Set<Byte>> {
    
    private final Set<Byte> set;
    
    public ByteInPredicate(HasByteValue<ENTITY, D> field, Set<Byte> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsByte(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Byte> get0() {
        return set;
    }
}