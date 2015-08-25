package com.speedment.core.field;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY>  Entity type
 */
public interface FunctionBuilder<ENTITY> {

    Field<ENTITY> getField();

}