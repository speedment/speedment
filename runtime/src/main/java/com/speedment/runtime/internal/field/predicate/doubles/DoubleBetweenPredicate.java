package com.speedment.runtime.internal.field.predicate.doubles;

import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasDoubleValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import com.speedment.runtime.internal.field.predicate.BetweenPredicate;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is between two doubles.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class DoubleBetweenPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Double, HasDoubleValue<ENTITY, D>> implements BetweenPredicate, Tuple2<Double, Double> {
    
    private final double start;
    private final double end;
    private final Inclusion inclusion;
    
    public DoubleBetweenPredicate(HasDoubleValue<ENTITY, D> field, double start, double end, Inclusion inclusion) {
        super(PredicateType.BETWEEN, field, entity -> {
            final double fieldValue = field.getAsDouble(entity);
            
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
    public Double get0() {
        return start;
    }
    
    @Override
    public Double get1() {
        return end;
    }
    
    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }
}