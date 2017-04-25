package com.speedment.runtime.core.internal.util.java9;

import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

/**
 *
 * @author Per Minborg
 */
public interface Java9DoubleStreamAdditions {

    DoubleStream takeWhile​(DoublePredicate predicate);

    DoubleStream dropWhile​(DoublePredicate predicate);

}
