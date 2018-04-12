package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToDouble;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasAsDouble<T> {
    ToDouble<T> asDouble();
}