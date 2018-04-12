package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToInt;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasAsInt<T> {
    ToInt<T> asInt();
}