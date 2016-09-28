package com.speedment.runtime.internal.field.predicate.chars;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasCharValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import java.util.Set;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of chars.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class CharInPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Character, HasCharValue<ENTITY, D>> implements Tuple1<Set<Character>> {
    
    private final Set<Character> set;
    
    public CharInPredicate(HasCharValue<ENTITY, D> field, Set<Character> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsChar(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Character> get0() {
        return set;
    }
}