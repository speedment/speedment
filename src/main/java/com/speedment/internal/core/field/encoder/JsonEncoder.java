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
package com.speedment.internal.core.field.encoder;

import com.speedment.field.Encoder;
import com.speedment.config.Column;
import com.speedment.config.Table;
import com.speedment.field.Field;
import com.speedment.Manager;
import com.speedment.field.ReferenceField;
import com.speedment.field.ReferenceForeignKeyField;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.util.JavaLanguage.javaVariableName;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type
 */
public final class JsonEncoder<ENTITY> implements Encoder<ENTITY, JsonEncoder<ENTITY>, String> {

    protected final Map<String, Function<ENTITY, String>> getters;

    /**
     * Constructs an empty JsonEncoder with no fields added to the output
     * renderer.
     */
    public JsonEncoder() {
        this.getters = new LinkedHashMap<>();
    }

    // Fields
    @Override
    public <T> JsonEncoder<ENTITY> put(ReferenceField<ENTITY, T> field) {
        requireNonNull(field);
        return put(jsonField(field), field::get);
    }

    // Foreign key fields.
    @Override
    public <T, FK_ENTITY> JsonEncoder<ENTITY> put(ReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, Encoder<FK_ENTITY, ?, String> builder) {
        requireNonNull(field);
        requireNonNull(builder);
        return put(jsonField(field), field::findFrom, builder);
    }

    // Label-and-getter pairs
    @Override
    public <T> JsonEncoder<ENTITY> put(String label, Function<ENTITY, T> getter) {
        requireNonNull(label);
        requireNonNull(getter);
        getters.put(label, e -> "\"" + label + "\":" + jsonValue(getter.apply(e)));
        return this;
    }

    // Label-and-getter with custom formatter
    @Override
    public <FK_ENTITY> JsonEncoder<ENTITY> put(String label, Function<ENTITY, FK_ENTITY> getter, Encoder<FK_ENTITY, ?, String> builder) {
        requireNonNull(label);
        requireNonNull(getter);
        requireNonNull(builder);
        getters.put(label, e -> "\"" + label + "\":" + builder.apply(getter.apply(e)));
        return this;
    }

    // Label-and-streamer with custom formatter.
    @Override
    public <FK_ENTITY> JsonEncoder<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Encoder<FK_ENTITY, ?, String> builder) {
        requireNonNull(label);
        requireNonNull(streamer);
        requireNonNull(builder);
        getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(builder::apply).collect(joining(",")) + "]");
        return this;
    }

    @Override
    public <FK_ENTITY> JsonEncoder<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Function<FK_ENTITY, String> encoder) {
        requireNonNull(label);
        requireNonNull(streamer);
        requireNonNull(encoder);
        getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(encoder).collect(joining(",")) + "]");
        return this;
    }

    // Removers by label
    @Override
    public JsonEncoder<ENTITY> remove(String label) {
        requireNonNull(label);
        getters.remove(label);
        return this;
    }

    @Override
    public <T> JsonEncoder<ENTITY> remove(ReferenceField<ENTITY, T> field) {
        requireNonNull(field);
        getters.remove(jsonField(field));
        return this;
    }

    @Override
    public String apply(ENTITY entity) {
        requireNonNull(entity);
        return "{"
            + getters.values().stream()
            .map(g -> g.apply(entity))
            .collect(joining(","))
            + "}";
    }

    protected static <ENTITY> String jsonField(Field<ENTITY> field) {
        requireNonNull(field);
        return javaVariableName(field.getColumnName());
    }

    protected static String jsonValue(Object in) {
        // in is nullable, a field can certainly be null
        final String value;

        if (in instanceof Optional<?>) {
            final Optional<?> o = (Optional<?>) in;
            return o.map(JsonEncoder::jsonValue).orElse("null");
        } else if (in == null) {
            value = "null";
        } else if (in instanceof Byte
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

        return value;
    }

    /**
     * Creates and return a new JsonEncoder with no fields added to the
     * renderer.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the entity
     * @return a new JsonEncoder with no fields added to the renderer
     */
    public static <ENTITY> JsonEncoder<ENTITY> noneOf(Manager<ENTITY> manager) {
        requireNonNull(manager);
        return new JsonEncoder<>();
    }

    /**
     * Creates and return a new JsonEncoder with all the Entity fields added
     * to the renderer. The field(s) will be rendered using their default class
     * renderer.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the entity
     * @return a new JsonEncoder with all the Entity fields added to the
     * renderer
     */
    public static <ENTITY> JsonEncoder<ENTITY> allOf(Manager<ENTITY> manager) {
        requireNonNull(manager);

        final JsonEncoder<ENTITY> formatter = noneOf(manager);

        final Table table = manager.getTable();

        table
            .streamOf(Column.class)
            .forEachOrdered(c
                -> formatter.put(
                    javaVariableName(c.getName()),
                    entity -> manager.get(entity, c)
                )
            );

        return formatter;
    }

    /**
     * Creates and return a new JsonEncoder with the provided Entity field(s)
     * added to the renderer. The field(s) will be rendered using their default
     * class renderer.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the ENTITY
     * @param fields to add to the output renderer
     * @return a new JsonEncoder with the specified fields added to the
     * renderer
     */
    @SafeVarargs
    @SuppressWarnings("varargs") // Using the array in a Stream.of() is safe
    public static <ENTITY> JsonEncoder<ENTITY> of(Manager<ENTITY> manager, Field<ENTITY>... fields) {
        requireNonNull(manager);
        requireNonNulls(fields);
        final JsonEncoder<ENTITY> formatter = noneOf(manager);

        final Set<String> fieldNames = Stream.of(fields).map(Field::getColumnName).collect(toSet());
        final Table table = manager.getTable();

        table
            .streamOf(Column.class)
            .filter(c -> fieldNames.contains(c.getName()))
            .forEachOrdered(c
                -> formatter.put(
                    javaVariableName(c.getName()),
                    entity -> manager.get(entity, c)
                )
            );

        return formatter;
    }

}
