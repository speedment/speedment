package com.speedment.util.stream.builder;

import com.speedment.util.stream.builder.action.Action;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface StreamTerminator {
    
    <T, S extends BaseStream<T, S>> BaseStream<T, S> baseStream(Pipeline<T> pipeline);

    default <T> Stream<T> stream(Pipeline<T> pipeline) {
        return (Stream<T>) baseStream(pipeline);
    }
    
    default <T> IntStream intStream(Pipeline<T> pipeline) {
        return (IntStream) baseStream(pipeline);
    }
    
    default <T> LongStream longStream(Pipeline<T> pipeline) {
        return (LongStream) baseStream(pipeline);
    }
    
    default <T> DoubleStream doubleStream(Pipeline<T> pipeline) {
        return (DoubleStream) baseStream(pipeline);
    }
    
    default <T> void forEach(Pipeline<T> pipeline, Consumer<? super T> action) {
        stream(pipeline).forEach(action);
    }
    
    default <T> void forEach(Pipeline<T> pipeline, IntConsumer action) {
        intStream(pipeline).forEach(action);
    }
    
    default <T> void forEach(Pipeline<T> pipeline, LongConsumer action) {
        longStream(pipeline).forEach(action);
    }
    
    default <T> void forEach(Pipeline<T> pipeline, DoubleConsumer action) {
        doubleStream(pipeline).forEach(action);
    }
    
    
    
    
    
    
    
    default <T> long count(Pipeline<T> pipeline) {
//        if (pipeline.getStreamType() == Stream.class) {
            return stream(pipeline).count();
//        }
//        if (pipeline.getStreamType() == IntStream.class) {
//            return intStream(pipeline).count();
//        }
//        return stream(pipeline).count();
    }
    
    
    default <T> long intCount(Pipeline<T> pipeline) {
        return intStream(pipeline).count();
    }
    
    default <T> long longCount(Pipeline<T> pipeline) {
        return longStream(pipeline).count();
    }
    
    default <T> long doubleCount(Pipeline<T> pipeline) {
        return longStream(pipeline).count();
    }
    
    
    
    
    
    
    
    default <T> Optional<T> findAny(Pipeline<T> pipeline) {
        return stream(pipeline).findAny();
    }
    
    class Pipeline<E> extends LinkedList<Action<?, ?>> {
        
        public Class<? extends BaseStream> getStreamType() {
            return getLast().resultStreamClass();
        }
        
        public Stream<E> stream(BaseStream<E, ?> initial) {
            return (Stream<E>) applyOn(initial);
        }
        
        public IntStream intStream(BaseStream<?, ?> initial) {
            return (IntStream) applyOn(initial);
        }
        
        public LongStream longStream(BaseStream<?, ?> initial) {
            return (LongStream) applyOn(initial);
        }
        
        public DoubleStream doubleStream(BaseStream<?, ?> initial) {
            return (DoubleStream) applyOn(initial);
        }
        
        private BaseStream<E, ?> applyOn(BaseStream<?, ?> initial) {
            BaseStream<?, ?> result = initial;
            
            for (Action<?, ?> action : this) {
                result = cast(result, action);
            }

            return (Stream<E>) result;
        }
        
        private <In extends BaseStream<?, ?>, Out extends BaseStream<?, Out>> Out cast(In in, Action<?, ?> action) {
            final Function<In, Out> mapper = (Function<In, Out>) action.get();
            return mapper.apply(in);
        }
    }
}