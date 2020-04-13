/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

package com.speedment.runtime.config;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.trait.HasDeepCopy;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

abstract class BaseConfigTest<T extends Document & HasMainInterface & HasDeepCopy & HasMutator<? extends DocumentMutator<? extends T>>> {

    abstract T getDocumentInstance();

    @Test
    void mainInterface() {
        assertTrue(Arrays.stream(getDocumentInstance().getClass().getInterfaces())
                .anyMatch(x -> x.getName().equals(getDocumentInstance().mainInterface().getName())));
    }

    @Test
    void mutator() {
        assertNotNull(getDocumentInstance().mutator());
    }

    @Test
    void deepCopy() {
        assertNotNull(getDocumentInstance().deepCopy());
    }

    protected final Map.Entry<String, Object> entry(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    protected final Map.Entry<String, Object> entry(String key, boolean value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    protected final Map.Entry<String, Object> entry(String key, int value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    protected final Map.Entry<String, Object> entry(String key, Path value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    protected final Map.Entry<String, Object> entry(String key, Map<String, Object>... children) {
        return new AbstractMap.SimpleEntry<>(key, Stream.of(children).collect(toList()));
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    protected final Map<String, Object> map(Map.Entry<String, Object>... entries) {
        return Stream.of(entries)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
