package com.speedment.runtime.internal.field.predicate.longs;

import com.speedment.runtime.internal.field.predicate.BetweenPredicate;
import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import static java.util.Objects.requireNonNull;
import static com.speedment.runtime.field.predicate.PredicateType.BETWEEN;
import com.speedment.runtime.field.trait.HasLongValue;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class LongBetweenPredicate<ENTITY, D> 
extends AbstractFieldPredicate<ENTITY, HasLongValue<ENTITY, D>>
        implements Tuple2<Long, Long>, BetweenPredicate {
    
    private final long start;
    private final long end;
    private final Inclusion inclusion;
    
    public LongBetweenPredicate(
            HasLongValue<ENTITY, D> field,
            long start,
            long end,
            Inclusion inclusion) {
        
        super(BETWEEN, field, entity -> {
            final long fieldValue = field.getAsLong(entity);
        
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE : 
                    return (start < fieldValue && end > fieldValue);
                
                case START_EXCLUSIVE_END_INCLUSIVE : 
                    return (start < fieldValue && end >= fieldValue);
                
                case START_INCLUSIVE_END_EXCLUSIVE : 
                    return (start <= fieldValue && end > fieldValue);
                
                case START_INCLUSIVE_END_INCLUSIVE : 
                    return (start <= fieldValue && end >= fieldValue);
                
                default : throw new IllegalStateException("Inclusion unknown: " + inclusion);
            }
        });
        
        this.start     = start;
        this.end       = end;
        this.inclusion = requireNonNull(inclusion);
    }

    @Override
    public Long get0() {
        return start;
    }

    @Override
    public Long get1() {
        return end;
    }

    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }
}