package com.speedment.runtime.internal.field.predicate.chars;

import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasCharValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import com.speedment.runtime.internal.field.predicate.BetweenPredicate;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is between two chars.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class CharBetweenPredicate<ENTITY, D>  extends AbstractFieldPredicate<ENTITY, HasCharValue<ENTITY, D>> implements BetweenPredicate, Tuple2<Character, Character> {
    
    private final char start;
    private final char end;
    private final Inclusion inclusion;
    
    public CharBetweenPredicate(HasCharValue<ENTITY, D> field, char start, char end, Inclusion inclusion) {
        super(PredicateType.BETWEEN, field, entity -> {
            final char fieldValue = field.getAsChar(entity);
            
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
    public Character get0() {
        return start;
    }
    
    @Override
    public Character get1() {
        return end;
    }
    
    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }
}