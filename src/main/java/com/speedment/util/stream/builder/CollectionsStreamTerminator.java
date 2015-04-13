package com.speedment.util.stream.builder;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.BaseStream;

/**
 *
 * @author pemi
 */
public class CollectionsStreamTerminator<E> implements StreamTerminator {

    private final Collection<E> collection;

    public CollectionsStreamTerminator(Collection<E> Collection) {
        this.collection = Collection;
    }

    @Override
    public <T, S extends BaseStream<T, S>> BaseStream<T, S> baseStream(Pipeline<T> pipeline) {
        return pipeline.stream(collection.stream());
    }

//    
//    @Override
//    public <T> BaseStream<T, ?> stream(Pipeline<T> pipeline) {
//        return pipeline.baseStream(collection.stream());
//    }
    @Override
    public <T> long count(Pipeline<T> pipeline) {
        // Todo: Check pipeline first
        if (pipeline.stream().allMatch(a -> !a.isCountModifying())) {
            return collection.size();
        }
        return pipeline.stream(collection.stream()).count();
    }

    @Override
    public <T> Optional<T> findAny(Pipeline<T> pipeline) {

    }
}
