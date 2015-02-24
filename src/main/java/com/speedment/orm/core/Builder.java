package com.speedment.orm.core;

import com.speedment.orm.annotations.Api;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Builder<T> {

    T build();

}
