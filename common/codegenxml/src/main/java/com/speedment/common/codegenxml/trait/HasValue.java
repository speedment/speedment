package com.speedment.common.codegenxml.trait;

import java.util.Optional;

/**
 *
 * @author Per Minborg
 * @param <R> type of self
 */
public interface HasValue<R extends HasValue<? super R>> {
    
    R setValue(String value);
    
    Optional<String> getValue();
}