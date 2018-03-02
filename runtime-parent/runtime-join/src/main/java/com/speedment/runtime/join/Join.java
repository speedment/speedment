package com.speedment.runtime.join;

import com.speedment.runtime.join.trait.HasStream;

/**
 * This interface represent a Join operation from which Streams of the joined
 * tables can be obtained.
 *
 * @author Per Minborg
 * @param <T> the composite type of entity types
 */
public interface Join<T> extends HasStream<T> {

}
