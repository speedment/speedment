package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToLong;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasAsLong<T> {
    ToLong<T> asLong();
}