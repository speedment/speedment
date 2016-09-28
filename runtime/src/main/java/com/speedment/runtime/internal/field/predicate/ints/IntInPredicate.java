package com.speedment.runtime.internal.field.predicate.ints;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasIntValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import java.util.Set;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of ints.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class IntInPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Integer, HasIntValue<ENTITY, D>> implements Tuple1<Set<Integer>> {
    
    private final Set<Integer> set;
    
    public IntInPredicate(HasIntValue<ENTITY, D> field, Set<Integer> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsInt(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Integer> get0() {
        return set;
    }
}