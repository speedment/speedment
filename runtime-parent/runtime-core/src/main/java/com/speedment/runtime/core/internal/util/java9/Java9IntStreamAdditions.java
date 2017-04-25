package com.speedment.runtime.core.internal.util.java9;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 *
 * @author Per Minborg
 */
public interface Java9IntStreamAdditions {

    IntStream takeWhile​(IntPredicate predicate);

    IntStream dropWhile​(IntPredicate predicate);

}
