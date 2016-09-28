package com.speedment.runtime.internal.field.predicate.chars;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasCharValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import javax.annotation.Generated;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class CharEqualPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Character, HasCharValue<ENTITY, D>> implements Tuple1<Character> {
    
    private final char value;
    
    public CharEqualPredicate(HasCharValue<ENTITY, D> field, char value) {
        super(PredicateType.EQUAL, field, entity -> field.getAsChar(entity) == value);
        this.value = value;
    }
    
    @Override
    public Character get0() {
        return value;
    }
}