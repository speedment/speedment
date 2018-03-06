package com.speedment.runtime.join;

import com.speedment.runtime.join.trait.HasDefaultBuild;
import com.speedment.runtime.join.trait.HasStream;
import java.util.stream.Stream;

/**
 * This interface represent a Join operation from which Streams of the joined
 * tables can be obtained.
 *
 * @author Per Minborg
 * @param <T> the composite type of entity types
 */
public interface Join<T> extends HasStream<T> {

    /**
     * {@inheritDoc }
     * <p>
     * Note: For Joins obtained via the default {@link HasDefaultBuild#build() }
     * method, elements appearing in the stream may be <em>deeply immutable</em>
     * meaning that Tuples in the stream are immutable and that entities
     * contained in these Tuples may also be immutable. Thus, it is an error to
     * invoke setters on any objects obtained directly or indirectly from the
     * stream elements. If mutable objects are needed, the immutable objects
     * must be used to create a new mutable object.
     *
     */
    @Override
    public Stream<T> stream();

}
