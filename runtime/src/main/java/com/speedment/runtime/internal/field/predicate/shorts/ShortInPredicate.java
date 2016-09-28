package com.speedment.runtime.internal.field.predicate.shorts;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasShortValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import java.util.Set;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of shorts.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class ShortInPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Short, HasShortValue<ENTITY, D>> implements Tuple1<Set<Short>> {
    
    private final Set<Short> set;
    
    public ShortInPredicate(HasShortValue<ENTITY, D> field, Set<Short> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsShort(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Short> get0() {
        return set;
    }
}