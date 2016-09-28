package com.speedment.runtime.internal.field.predicate.floats;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasFloatValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;
import java.util.Set;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of floats.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class FloatInPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Float, HasFloatValue<ENTITY, D>> implements Tuple1<Set<Float>> {
    
    private final Set<Float> set;
    
    public FloatInPredicate(HasFloatValue<ENTITY, D> field, Set<Float> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsFloat(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Float> get0() {
        return set;
    }
}