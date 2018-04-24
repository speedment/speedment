package com.speedment.common.function.collector;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link LongCollector}-interface.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
final class LongCollectorImpl<A, R>
implements LongCollector<A, R> {

    private final Supplier<A> supplier;
    private final ObjLongConsumer<A> accumulator;
    private final BinaryOperator<A> combiner;
    private final Function<A, R> finisher;
    private final Set<Collector.Characteristics> characteristics;

    LongCollectorImpl(
            final Supplier<A> supplier,
            final ObjLongConsumer<A> accumulator,
            final BinaryOperator<A> combiner,
            final Function<A, R> finisher,
            final Set<Collector.Characteristics> characteristics) {
        this.supplier        = requireNonNull(supplier);
        this.accumulator     = requireNonNull(accumulator);
        this.combiner        = requireNonNull(combiner);
        this.finisher        = requireNonNull(finisher);
        this.characteristics = requireNonNull(characteristics);
    }

    @Override
    public Supplier<A> supplier() {
        return supplier;
    }

    @Override
    public ObjLongConsumer<A> accumulator() {
        return accumulator;
    }

    @Override
    public BinaryOperator<A> combiner() {
        return combiner;
    }

    @Override
    public Function<A, R> finisher() {
        return finisher;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return characteristics;
    }
}
