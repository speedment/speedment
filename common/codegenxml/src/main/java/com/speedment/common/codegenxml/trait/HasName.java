package com.speedment.common.codegenxml.trait;

/**
 *
 * @author Per Minborg
 * @param <R> type of self
 */
public interface HasName<R extends HasName<? super R>> {
    
    R setName(String name);

    String getName();

}
