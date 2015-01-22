/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.codegen.model.modifier;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class StaticSupport<T extends Modifier_<T>> {

    private final T[] values;
    private final Map<String, T> nameMap;

    public StaticSupport(T[] values) {
        this.values = values;
        this.nameMap = Stream.of(values).collect(Collectors.toMap((m) -> m.name().toLowerCase(), (m) -> m));
    }

    public T by(final String name) {
        return nameMap.get(name.toLowerCase());
    }

    public Set<T> of(final String text) {
        final String[] tokens = text.split("\\s+");
        return Stream.of(tokens).filter((token) -> by(token) != null).map((token) -> by(token)).collect(Collectors.toSet());
    }

    public Set<T> of(final int code) {
        return Stream.of(values).filter((cm) -> Modifier_.valuesContains(code, cm.getValue())).collect(Collectors.toSet());
    }

    @SafeVarargs
    @SuppressWarnings({"unchecked", "varargs"})
    public final Set<T> of(final T... classModifiers) {
        return Stream.of(classModifiers).collect(Collectors.toSet());
    }

}
