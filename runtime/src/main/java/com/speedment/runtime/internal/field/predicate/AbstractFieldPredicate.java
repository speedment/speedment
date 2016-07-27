package com.speedment.runtime.internal.field.predicate;

import com.speedment.common.tuple.Tuple;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.internal.util.Cast;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 *
 * @param <ENTITY>  the entity type that is being tested
 * @param <FIELD>   the field in the entity that is operated on
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public abstract class AbstractFieldPredicate<ENTITY, FIELD extends FieldTrait<ENTITY>> 
        extends AbstractPredicate<ENTITY> implements SpeedmentPredicate<ENTITY> {
    
    private final PredicateType predicateType;
    private final FIELD field;
    private final Predicate<ENTITY> tester;
    
    protected AbstractFieldPredicate(
            PredicateType predicateType,
            FIELD field,
            Predicate<ENTITY> tester) {
        this.predicateType = requireNonNull(predicateType);
        this.field         = requireNonNull(field);
        this.tester        = requireNonNull(tester);
    }
    
    @Override
    protected boolean testWithoutNegation(ENTITY instance) {
        return tester.test(instance);
    }

    @Override
    public final PredicateType getPredicateType() {
        return predicateType;
    }

    @Override
    public final PredicateType getEffectivePredicateType() {
        return isNegated() ? predicateType.negate() : predicateType;
    }

    @Override
    public final FIELD getField() {
        return field;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName())
            .append(" {")
            .append("field: ").append(field)
            .append(", type: '").append(predicateType).append("'");

        Cast.cast(this, Tuple.class)
            .ifPresent(tuple -> {
                for (int i = 0; i < tuple.length(); i++) {
                    sb.append(", operand ").append(i).append(": ").append(tuple.get(i));
                }
            });

        sb.append(", negated: ").append(isNegated())
            .append("}");
        return sb.toString();
    }
}