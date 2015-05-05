package com.speedment.orm.field;

/**
 *
 * @author pemi
 */
public interface UnaryPredicateBuilder extends PredicateBuilder {

    @Override
    public StandardUnaryOperator getOperator();
    
}