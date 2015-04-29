package com.speedment.orm.function;

/**
 *
 * @author pemi
 */
@Deprecated
@FunctionalInterface
public interface BinaryPredicate  {

    <C extends Comparable<C>> boolean test(C first, C second);
}
