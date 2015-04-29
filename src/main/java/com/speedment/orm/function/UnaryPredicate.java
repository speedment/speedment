package com.speedment.orm.function;

/**
 *
 * @author pemi
 */
@Deprecated
@FunctionalInterface
public interface UnaryPredicate {

    <C extends Comparable<C>> boolean eval(C first);
}
