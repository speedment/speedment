package com.speedment.config;

import com.speedment.util.OptionalBoolean;
import com.speedment.stream.MapStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forsund
 */
public interface Document {
    
    Optional<? extends Document> getParent();
    
    Optional<Object> get(String key);
    OptionalBoolean getAsBoolean(String key);
    OptionalLong getAsLong(String key);
    OptionalDouble getAsDouble(String key);
    OptionalInt getAsInt(String key);
    Optional<String> getAsString(String key);
    
    void put(String key, Object value);
    MapStream<String, Object> stream();
    
    default <T> Stream<T> children(String key, Function<Map<String, Object>, T> instantiator) {
        final List<Map<String, Object>> list = 
            (List<Map<String, Object>>) get(key).orElse(null);
        
        if (list == null) {
            return Stream.empty();
        } else {
            return list.stream().map(instantiator);
        }
    }
    
    default Stream<Document> ancestors() {
        final Stream.Builder<Document> stream = Stream.builder();
        Document parent = this;
        
        while ((parent = parent.getParent().orElse(null)) != null) {
            stream.add(parent);
        }
        
        return stream.build();
    }
}