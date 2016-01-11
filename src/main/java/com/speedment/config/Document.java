/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
    
    default <P extends Document, T extends Document> Stream<T> children(
        String key, BiFunction<P, Map<String, Object>, T> constructor) {
        
        final List<Map<String, Object>> list = 
            (List<Map<String, Object>>) get(key).orElse(null);
        
        if (list == null) {
            return Stream.empty();
        } else {
            return list.stream().map(map -> constructor.apply((P) this, map));
        }
    }
    
    Stream<? extends Document> children();
    
    default Stream<Document> ancestors() {
        final Stream.Builder<Document> stream = Stream.builder();
        Document parent = this;
        
        while ((parent = parent.getParent().orElse(null)) != null) {
            stream.add(parent);
        }
        
        return stream.build();
    }
}