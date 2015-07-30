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
package com.speedment.core.json.impl;

import com.speedment.core.json.*;
import com.speedment.core.field.Field;
import com.speedment.core.field.doubles.DoubleField;
import com.speedment.core.field.ints.IntField;
import com.speedment.core.field.longs.LongField;
import com.speedment.core.field.reference.ComparableReferenceForeignKeyField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.ReferenceForeignKeyField;
import com.speedment.core.field.reference.string.StringReferenceForeignKeyField;
import static com.speedment.util.java.JavaLanguage.javaVariableName;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type
 */
public class JsonFormatterImpl<ENTITY> implements JsonFormatter<ENTITY> {

    protected final Map<String, Function<ENTITY, String>> getters;

    /**
     * Constructs an empty JsonFormatter with no fields added to the output
     * renderer.
     */
    public JsonFormatterImpl() {
        this.getters = new LinkedHashMap<>();
    }

    // Fields
    @Override
    public <T> JsonFormatterImpl<ENTITY> put(ReferenceField<ENTITY, T> field) {
        return put(jsonField(field), field::getFrom);
    }

    @Override
    public JsonFormatterImpl<ENTITY> put(IntField<ENTITY> field) {
        return put(jsonField(field), field::getFrom);
    }

    @Override
    public JsonFormatterImpl<ENTITY> put(LongField<ENTITY> field) {
        return put(jsonField(field), field::getFrom);
    }

    @Override
    public JsonFormatterImpl<ENTITY> put(DoubleField<ENTITY> field) {
        return put(jsonField(field), field::getFrom);
    }

    // Foreign key fields.
    @Override
    public <T, FK_ENTITY> JsonFormatterImpl<ENTITY> put(ReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, JsonFormatter<FK_ENTITY> builder) {
        return put(jsonField(field), field::findFrom, builder);
    }

    @Override
    public <T extends Comparable<? super T>, FK_ENTITY> JsonFormatterImpl<ENTITY> put(ComparableReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, JsonFormatter<FK_ENTITY> builder) {
        return put(jsonField(field), field::findFrom, builder);
    }

    @Override
    public <FK_ENTITY> JsonFormatterImpl<ENTITY> put(StringReferenceForeignKeyField<ENTITY, FK_ENTITY> field, JsonFormatter<FK_ENTITY> builder) {
        return put(jsonField(field), field::findFrom, builder);
    }

    // Label-and-getter pairs
    @Override
    public <T> JsonFormatterImpl<ENTITY> put(String label, Function<ENTITY, T> getter) {
        getters.put(label, e -> "\"" + label + "\":" + jsonValue(getter.apply(e)));
        return this;
    }

    @Override
    public JsonFormatterImpl<ENTITY> putDouble(String label, ToDoubleFunction<ENTITY> getter) {
        getters.put(label, e -> "\"" + label + "\":" + getter.applyAsDouble(e));
        return this;
    }

    @Override
    public JsonFormatterImpl<ENTITY> putInt(String label, ToIntFunction<ENTITY> getter) {
        getters.put(label, e -> "\"" + label + "\":" + getter.applyAsInt(e));
        return this;
    }

    @Override
    public JsonFormatterImpl<ENTITY> putLong(String label, ToLongFunction<ENTITY> getter) {
        getters.put(label, e -> "\"" + label + "\":" + getter.applyAsLong(e));
        return this;
    }

    // Label-and-getter with custom formatter
    @Override
    public <FK_ENTITY> JsonFormatterImpl<ENTITY> put(String label, Function<ENTITY, FK_ENTITY> getter, JsonFormatter<FK_ENTITY> builder) {
        getters.put(label, e -> "\"" + label + "\":" + builder.apply(getter.apply(e)));
        return this;
    }

    // Label-and-streamer with custom formatter.
    @Override
    public <FK_ENTITY> JsonFormatterImpl<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, JsonFormatter<FK_ENTITY> builder) {
        getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(builder::apply).collect(joining(",")) + "]");
        return this;
    }

    @Override
    public <FK_ENTITY> JsonFormatterImpl<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Function<FK_ENTITY, String> formatter) {
        getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(formatter).collect(joining(",")) + "]");
        return this;
    }

    // Removers by label
    @Override
    public JsonFormatterImpl<ENTITY> remove(String label) {
        getters.remove(label);
        return this;
    }

    @Override
    public <T> JsonFormatterImpl<ENTITY> remove(ReferenceField<ENTITY, T> field) {
        getters.remove(jsonField(field));
        return this;
    }

    @Override
    public String apply(ENTITY entity) {
        return "{"
            + getters.values().stream()
            .map(g -> g.apply(entity))
            .collect(joining(","))
            + "}";
    }

    protected static <ENTITY> String jsonField(Field<ENTITY> field) {
        return javaVariableName(field.getColumn().getName());
    }

    protected static String jsonValue(Object in) {
        final String value;

        if (in instanceof Optional<?>) {
            final Optional<?> o = (Optional<?>) in;
            return o.map(JsonFormatterImpl::jsonValue).orElse("null");
        } else {
            if (in == null) {
                value = "null";
            } else {
                if (in instanceof Byte
                    || in instanceof Short
                    || in instanceof Integer
                    || in instanceof Long
                    || in instanceof Boolean
                    || in instanceof Float
                    || in instanceof Double) {
                    value = String.valueOf(in);
                } else {
                    value = "\"" + String.valueOf(in).replace("\"", "\\\"") + "\"";
                }
            }
        }

        return value;
    }

}
