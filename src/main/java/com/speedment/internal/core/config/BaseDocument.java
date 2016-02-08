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
package com.speedment.internal.core.config;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import static com.speedment.internal.util.document.DocumentUtil.childrenOf;
import com.speedment.util.OptionalBoolean;
import com.speedment.stream.MapStream;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public class BaseDocument implements Document {
    
    private final transient Document parent; // Nullable
    private final Map<String, Object> config;

    public BaseDocument(Document parent, Map<String, Object> data) {
        this.parent = parent;
        this.config = requireNonNull(data);
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
        final Number value = (Number) config.get(key);
        return value == null ? OptionalLong.empty() : OptionalLong.of(value.longValue());
    }

    @Override
    public OptionalDouble getAsDouble(String key) {
        final Number value = (Number) config.get(key);
        return value == null ? OptionalDouble.empty() : OptionalDouble.of(value.doubleValue());
    }

    @Override
    public OptionalInt getAsInt(String key) {
        final Number value = (Number) config.get(key);
        return value == null ? OptionalInt.empty() : OptionalInt.of(value.intValue());
    }
    
    @Override
    public Optional<String> getAsString(String key) {
        return get(key).map(String.class::cast);
    }

    @Override
    public void put(String key, Object value) {
        requireNonNull(value);
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