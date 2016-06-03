package com.speedment.common.codegenxml.trait;

/**
 *
 * @author Per Minborg
 * @param <R> return type for setter
 */
public interface HasIsEscape<R extends HasIsEscape<? super R>> {

    /**
     * Returns if this HasIsEscape shall be escaped when rendered
     *
     * @return if this HasIsEscape shall be escaped when rendered
     */
    boolean isEscape();

    R setEscape(boolean escape);

}
