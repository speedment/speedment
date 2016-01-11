/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.core.config.db.immutable;

import com.speedment.internal.core.config.BaseDocument;
import com.speedment.config.Document;
import static com.speedment.internal.util.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class ImmutableDocument extends BaseDocument {
    
    private final transient Map<String, List<Document>> children;
    
    protected ImmutableDocument(Map<String, Object> data) {
        super(Collections.unmodifiableMap(data));
        children = new ConcurrentHashMap<>();
    }
    
    protected ImmutableDocument(ImmutableDocument parent, Map<String, Object> data) {
        super(parent, Collections.unmodifiableMap(data));
        children = new ConcurrentHashMap<>();
    }

    @Override
    public final Map<String, Object> getData() {
        return super.getData();
    }

    @Override
    public final void put(String key, Object value) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public <P extends Document, T extends Document> Stream<T> children(String key, BiFunction<P, Map<String, Object>, T> constructor) {
        return children.computeIfAbsent(key, k -> {
            final List<Map<String, Object>> list = 
                (List<Map<String, Object>>) get(k).orElse(null);

            if (list == null) {
                return new ArrayList<>();
            } else {
                return list.stream()
                    .map(Collections::unmodifiableMap)
                    .map(data -> constructor.apply((P) this, data))
                    .map(Document.class::cast)
                    .collect(toList());
            }
        }).stream().map(c -> (T) c);
    }
    
    public static ImmutableDocument wrap(Document document) {
        return new ImmutableDocument(document.getData());
    }
}