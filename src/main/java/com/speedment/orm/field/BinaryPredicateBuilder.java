package com.speedment.orm.field;

/**
 *
 * @author pemi
 * @param <V>
 */
public interface BinaryPredicateBuilder<V> extends PredicateBuilder {
    
    V getValueAsObject();
    
    @Override
    public BinaryOperator getOperator();
}