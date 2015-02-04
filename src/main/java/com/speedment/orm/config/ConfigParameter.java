package com.speedment.orm.config;

/**
 *
 * @author pemi
 * @param <E>
 */
public interface ConfigParameter<E> extends Comparable<ConfigParameter<E>> {

    CharSequence getName();

    E get();

}
