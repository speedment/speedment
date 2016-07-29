package com.speedment.plugins.json.internal;

import com.speedment.plugins.json.JsonCollector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import static java.util.stream.Collector.Characteristics.CONCURRENT;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class JsonCollectorImpl<ENTITY> implements JsonCollector<ENTITY> {
    
    public static <ENTITY> JsonCollector<ENTITY> collect(Function<ENTITY, String> converter, Function<List<String>, String> merger) {
        return new JsonCollectorImpl<>(converter, merger);
    }
    
    @Override
    public Supplier<List<String>> supplier() {
        return () -> Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public BiConsumer<List<String>, ENTITY> accumulator() {
        return (l, t) -> {
            synchronized (l) {
                l.add(converter.apply(t));
            }
        };
    }

    @Override
    public BinaryOperator<List<String>> combiner() {
        return (l1, l2) -> {
            synchronized (l1) {
                l1.addAll(l2);
                return l1;
            }
        };
    }

    @Override
    public Function<List<String>, String> finisher() {
        return merger::apply;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return EnumSet.of(CONCURRENT);
    }
    
    private JsonCollectorImpl(Function<ENTITY, String> converter, Function<List<String>, String> merger) {
        this.converter = requireNonNull(converter);
        this.merger    = requireNonNull(merger);
    }
    
    private final Function<ENTITY, String> converter;
    private final Function<List<String>, String> merger;
}