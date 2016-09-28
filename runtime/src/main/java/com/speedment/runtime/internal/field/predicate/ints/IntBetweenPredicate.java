package com.speedment.runtime.internal.field.predicate.ints;

import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasIntValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import com.speedment.runtime.internal.field.predicate.BetweenPredicate;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is between two ints.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class IntBetweenPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Integer, HasIntValue<ENTITY, D>> implements BetweenPredicate, Tuple2<Integer, Integer> {
    
    private final int start;
    private final int end;
    private final Inclusion inclusion;
    
    public IntBetweenPredicate(HasIntValue<ENTITY, D> field, int start, int end, Inclusion inclusion) {
        super(PredicateType.BETWEEN, field, entity -> {
            final int fieldValue = field.getAsInt(entity);
            
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
    public Integer get0() {
        return start;
    }
    
    @Override
    public Integer get1() {
        return end;
    }
    
    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }
}