package com.speedment.common.function.collector;

import com.speedment.common.function.ObjByteConsumer;
import com.speedment.common.function.ToLongFunction;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Primitive collector that operates on {@code byte} values, resulting in a
 * single {@code long}.
 *
 * @param <A>  the intermediary accumulating type
 *
 * @author Emil Forslund
 * @since  1.0.3
 */
public interface ByteToLongCollector<A> {

    /**
     * Returns a supplier that can create an intermediary accumulating object.
     *
     * @return  the supplier for the accumulating object
     * @see Collector#supplier()
     */
    Supplier<A> supplier();

    /**
     * Stateless function that takes an accumulating object returned by
     * {@link #supplier()} and adds a single {@code byte} value to it.
     *
     * @return  the accumulator
     * @see Collector#accumulator()
     */
    ObjByteConsumer<A> accumulator();

    /**
     * Stateless function that takes two accumulating objects and returns a
     * single one representing the combined result. This can be either one of
     * the two instances or a completely new instance.
     *
     * @return  the combiner
     * @see Collector#combiner()
     */
    BinaryOperator<A> combiner();

    /**
     * Returns a finisher function that takes an accumulating object and turns
     * it into the final {@code long}.
     *
     * @return  the finisher
     * @see Collector#finisher()
     */
    ToLongFunction<A> finisher();

    /**
     * Returns a set of characteristics for this collector.
     *
     * @return  characteristics
     * @see Collector#characteristics()
     */
    Set<Collector.Characteristics> characteristics();

}
