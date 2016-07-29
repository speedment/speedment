package com.speedment.runtime.internal.field.predicate.ints;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasIntValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class IntGreaterOrEqualPredicate<ENTITY, D>  extends AbstractFieldPredicate<ENTITY, HasIntValue<ENTITY, D>> implements Tuple1<Integer> {
    
    private final int value;
    
    public IntGreaterOrEqualPredicate(HasIntValue<ENTITY, D> field, int value) {
        super(PredicateType.GREATER_OR_EQUAL, field, entity -> field.getAsInt(entity) == value);
        this.value = value;
    }
    
    @Override
    public Integer get0() {
        return value;
    }
}