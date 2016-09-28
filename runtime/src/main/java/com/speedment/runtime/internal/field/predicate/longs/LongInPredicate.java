package com.speedment.runtime.internal.field.predicate.longs;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasLongValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import java.util.Set;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of longs.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class LongInPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Long, HasLongValue<ENTITY, D>> implements Tuple1<Set<Long>> {
    
    private final Set<Long> set;
    
    public LongInPredicate(HasLongValue<ENTITY, D> field, Set<Long> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsLong(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Long> get0() {
        return set;
    }
}