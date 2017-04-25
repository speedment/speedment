package com.speedment.runtime.core.internal.util.java9;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public interface Java9StreamAdditions<T> {

    Stream<T> takeWhile(Predicate<? super T> predicate);

    Stream<T> dropWhile(Predicate<? super T> predicate);

}
