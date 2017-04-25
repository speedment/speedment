package com.speedment.runtime.core.internal.util.java9;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 *
 * @author Per Minborg
 */
public interface Java9LongStreamAdditions {

    LongStream takeWhile​(LongPredicate predicate);

    LongStream dropWhile​(LongPredicate predicate);

}
