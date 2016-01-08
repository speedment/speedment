package com.speedment.config;

import com.speedment.annotation.Api;
import static com.speedment.internal.util.document.DocumentUtil.childrenOf;
import com.speedment.util.OptionalBoolean;
import com.speedment.stream.MapStream;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public class BaseDocument implements Document {
    
    private final transient Document parent; // Nullable
    private final Map<String, Object> config;
    
    public BaseDocument(Map<String, Object> data) {
        this.parent = null;
        this.config = requireNonNull(data);
    }
    
    public BaseDocument(Document parent, Map<String, Object> data) {
        this.parent = parent;
        this.config = data;
    }

    @Override
    public Optional<? extends Document> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public Map<String, Object> getData() {
        return config;
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.ofNullable(config.get(key));
    }
    
    @Override
    public OptionalBoolean getAsBoolean(String key) {
        return OptionalBoolean.ofNullable((Boolean) config.get(key));
    }

    @Override
    public OptionalLong getAsLong(String key) {
        final Long value = (Long) config.get(key);
        return value == null ? OptionalLong.empty() : OptionalLong.of(value);
    }

    @Override
    public OptionalDouble getAsDouble(String key) {
        final Double value = (Double) config.get(key);
        return value == null ? OptionalDouble.empty() : OptionalDouble.of(value);
    }

    @Override
    public OptionalInt getAsInt(String key) {
        final Integer value = (Integer) config.get(key);
        return value == null ? OptionalInt.empty() : OptionalInt.of(value);
    }
    
    @Override
    public Optional<String> getAsString(String key) {
        return get(key).map(String.class::cast);
    }

    @Override
    public void put(String key, Object value) {
        config.put(key, value);
    }
    
    @Override
    public final MapStream<String, Object> stream() {
        return MapStream.of(config);
    }

    @Override
    public Stream<Document> children() {
        return childrenOf(this, BaseDocument::new);
    }
}