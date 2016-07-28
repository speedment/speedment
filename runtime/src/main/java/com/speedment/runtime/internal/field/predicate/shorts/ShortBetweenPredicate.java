package com.speedment.runtime.internal.field.predicate.shorts;

import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasShortValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import com.speedment.runtime.internal.field.predicate.BetweenPredicate;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is between two shorts.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ShortBetweenPredicate<ENTITY, D>  extends AbstractFieldPredicate<ENTITY, HasShortValue<ENTITY, D>> implements BetweenPredicate, Tuple2<Short, Short> {
    
    private final short start;
    private final short end;
    private final Inclusion inclusion;
    
    public ShortBetweenPredicate(HasShortValue<ENTITY, D> field, short start, short end, Inclusion inclusion) {
        super(PredicateType.BETWEEN, field, entity -> {
            final short fieldValue = field.getAsShort(entity);
            
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
    public Short get0() {
        return start;
    }
    
    @Override
    public Short get1() {
        return end;
    }
    
    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }
}