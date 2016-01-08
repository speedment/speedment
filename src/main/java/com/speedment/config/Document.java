package com.speedment.config;

import com.speedment.annotation.Api;
import com.speedment.util.OptionalBoolean;
import com.speedment.stream.MapStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forsund
 */
@Api(version = "2.3")
public interface Document {
    
    Optional<? extends Document> getParent();
    
    Map<String, Object> getData();
    
    Optional<Object> get(String key);
    
    OptionalBoolean getAsBoolean(String key);
    
    OptionalLong getAsLong(String key);
    
    OptionalDouble getAsDouble(String key);
    
    OptionalInt getAsInt(String key);
    
    Optional<String> getAsString(String key);
    
    void put(String key, Object value);
    
    default MapStream<String, Object> stream() {
        return MapStream.of(getData());
    }
    
    default <P extends Document, T extends Document> Stream<T> 
            children(String key, BiFunction<P, Map<String, Object>, T> instantiator) {
        
        final List<Map<String, Object>> list = 
            (List<Map<String, Object>>) get(key).orElse(null);
        
        if (list == null) {
            return Stream.empty();
        } else {
            return list.stream().map(map -> instantiator.apply((P) this, map));
        }
    }
    
    Stream<Document> children();
    
    default Stream<Document> ancestors() {
        final Stream.Builder<Document> stream = Stream.builder();
        Document parent = this;
        
        while ((parent = parent.getParent().orElse(null)) != null) {
            stream.add(parent);
        }
        
        return stream.build();
    }
}