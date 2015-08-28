package com.speedment.api.field.builders;

import com.speedment.api.annotation.Api;
import com.speedment.api.field.operators.StringOperator;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
@Api(version = "2.1")
public interface StringPredicateBuilder<ENTITY> 
    extends PredicateBuilder<ENTITY>, Predicate<ENTITY> {

    StringOperator getStringOperator();
    
    String getValue();
}