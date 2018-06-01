package com.speedment.common.mapbuilder.internal;

import com.speedment.common.mapbuilder.MapBuilder;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link MapBuilder}.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class MapBuilderImpl<K, V> implements MapBuilder<K, V> {

    public static MapBuilder.ExpectFirstKey expectFirstKey() {
        return new ExpectFirstKeyImpl();
    }

    public static <K, V> MapBuilder<K, V> createTyped() {
        return new MapBuilderImpl<>(emptyMap());
    }

    public static <K, V> MapBuilder<K, V> create(K key, V value) {
        return new MapBuilderImpl<>(singletonMap(key, value));
    }

    private final Map<K, V> map;

    private MapBuilderImpl(Map<K, V> existing) {
        this.map = new HashMap<>(existing);
    }

    private MapBuilderImpl(Map<K, V> existing, K key, V value) {
        final Map<K, V> inner = new HashMap<>(requireNonNull(existing));
        inner.put(requireNonNull(key), requireNonNull(value));
        map = inner;
    }

    @Override
    public ExpectValue<K, V> key(K key) {
        return new ExpectValueImpl<>(map, key);
    }

    @Override
    public MapBuilder<K, V> entry(K key, V value) {
        return new MapBuilderImpl<>(map, key, value);
    }

    @Override
    public Map<K, V> build() {
        switch (map.size()) {
            case 0: return emptyMap();
            case 1: {
                final Map.Entry<K, V> first = map.entrySet().iterator().next();
                return singletonMap(first.getKey(), first.getValue());
            }
            default: return unmodifiableMap(new HashMap<>(map));
        }
    }

    private final static class ExpectFirstKeyImpl
    implements MapBuilder.ExpectFirstKey {

        private ExpectFirstKeyImpl() {}

        @Override
        public <K> ExpectFirstValue<K> key(K key) {
            return new ExpectFirstValueImpl<>(key);
        }
    }

    private final static class ExpectFirstValueImpl<K>
    implements MapBuilder.ExpectFirstValue<K> {

        private final K key;

        private ExpectFirstValueImpl(K key) {
            this.key = requireNonNull(key);
        }

        @Override
        public <V> MapBuilder<K, V> value(V value) {
            return new MapBuilderImpl<>(emptyMap(), key, value);
        }
    }

    private final static class ExpectValueImpl<K, V>
    implements MapBuilder.ExpectValue<K, V> {

        private final Map<K, V> existing;
        private final K key;

        private ExpectValueImpl(Map<K, V> existing, K key) {
            this.existing = requireNonNull(existing);
            this.key      = requireNonNull(key);
        }

        @Override
        public MapBuilder<K, V> value(V value) {
            return new MapBuilderImpl<>(existing, key, value);
        }
    }
}
