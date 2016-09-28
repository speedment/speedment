package com.speedment.runtime.internal.field.predicate.floats;

import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasFloatValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import com.speedment.runtime.internal.field.predicate.BetweenPredicate;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is between two floats.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class FloatBetweenPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Float, HasFloatValue<ENTITY, D>> implements BetweenPredicate, Tuple2<Float, Float> {
    
    private final float start;
    private final float end;
    private final Inclusion inclusion;
    
    public FloatBetweenPredicate(HasFloatValue<ENTITY, D> field, float start, float end, Inclusion inclusion) {
        super(PredicateType.BETWEEN, field, entity -> {
            final float fieldValue = field.getAsFloat(entity);
            
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
    public Float get0() {
        return start;
    }
    
    @Override
    public Float get1() {
        return end;
    }
    
    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }
}