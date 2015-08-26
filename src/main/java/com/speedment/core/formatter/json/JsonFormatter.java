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
package com.speedment.core.formatter.json;

import com.speedment.core.config.model.Column;
import com.speedment.core.config.model.Table;
import com.speedment.core.field.Field;
import com.speedment.core.field.doubles.DoubleField;
import com.speedment.core.field.ints.IntField;
import com.speedment.core.field.longs.LongField;
import com.speedment.core.field.reference.ComparableReferenceForeignKeyField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.ReferenceForeignKeyField;
import com.speedment.core.field.reference.string.StringReferenceForeignKeyField;
import com.speedment.core.formatter.EntityFormatter;
import com.speedment.core.manager.Manager;
import static com.speedment.util.java.JavaLanguage.javaVariableName;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type
 */
public class JsonFormatter<ENTITY> implements EntityFormatter<ENTITY, JsonFormatter<ENTITY>> {

    protected final Map<String, Function<ENTITY, String>> getters;

    /**
     * Constructs an empty JsonFormatter with no fields added to the output
     * renderer.
     *
     */
    public JsonFormatter() {
        this.getters = new LinkedHashMap<>();
    }

    // Fields
    @Override
    public <T> JsonFormatter<ENTITY> put(ReferenceField<ENTITY, T> field) {
        return put(jsonField(field), field::getFrom);
    }

    @Override
    public JsonFormatter<ENTITY> put(IntField<ENTITY> field) {
        return put(jsonField(field), field::getFrom);
    }

    @Override
    public JsonFormatter<ENTITY> put(LongField<ENTITY> field) {
        return put(jsonField(field), field::getFrom);
    }

    @Override
    public JsonFormatter<ENTITY> put(DoubleField<ENTITY> field) {
        return put(jsonField(field), field::getFrom);
    }

    // Foreign key fields.
    @Override
    public <T, FK_ENTITY> JsonFormatter<ENTITY> put(ReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, EntityFormatter<FK_ENTITY, ?> builder) {
        return put(jsonField(field), field::findFrom, builder);
    }

    @Override
    public <T extends Comparable<? super T>, FK_ENTITY> JsonFormatter<ENTITY> put(ComparableReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, EntityFormatter<FK_ENTITY, ?> builder) {
        return put(jsonField(field), field::findFrom, builder);
    }

    @Override
    public <FK_ENTITY> JsonFormatter<ENTITY> put(StringReferenceForeignKeyField<ENTITY, FK_ENTITY> field, EntityFormatter<FK_ENTITY, ?> builder) {
        return put(jsonField(field), field::findFrom, builder);
    }

    // Label-and-getter pairs
    @Override
    public <T> JsonFormatter<ENTITY> put(String label, Function<ENTITY, T> getter) {
        getters.put(label, e -> "\"" + label + "\":" + jsonValue(getter.apply(e)));
        return this;
    }

    @Override
    public JsonFormatter<ENTITY> putDouble(String label, ToDoubleFunction<ENTITY> getter) {
        getters.put(label, e -> "\"" + label + "\":" + getter.applyAsDouble(e));
        return this;
    }

    @Override
    public JsonFormatter<ENTITY> putInt(String label, ToIntFunction<ENTITY> getter) {
        getters.put(label, e -> "\"" + label + "\":" + getter.applyAsInt(e));
        return this;
    }

    @Override
    public JsonFormatter<ENTITY> putLong(String label, ToLongFunction<ENTITY> getter) {
        getters.put(label, e -> "\"" + label + "\":" + getter.applyAsLong(e));
        return this;
    }

    // Label-and-getter with custom formatter
    @Override
    public <FK_ENTITY> JsonFormatter<ENTITY> put(String label, Function<ENTITY, FK_ENTITY> getter, EntityFormatter<FK_ENTITY, ?> builder) {
        getters.put(label, e -> "\"" + label + "\":" + builder.apply(getter.apply(e)));
        return this;
    }

    // Label-and-streamer with custom formatter.
    @Override
    public <FK_ENTITY> JsonFormatter<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, EntityFormatter<FK_ENTITY, ?> builder) {
        getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(builder::apply).collect(joining(",")) + "]");
        return this;
    }

    @Override
    public <FK_ENTITY> JsonFormatter<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Function<FK_ENTITY, String> formatter) {
        getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(formatter).collect(joining(",")) + "]");
        return this;
    }

    // Removers by label
    @Override
    public JsonFormatter<ENTITY> remove(String label) {
        getters.remove(label);
        return this;
    }

    @Override
    public <T> JsonFormatter<ENTITY> remove(ReferenceField<ENTITY, T> field) {
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
        return javaVariableName(field.getColumnName());
    }

    protected static String jsonValue(Object in) {
        final String value;

        if (in instanceof Optional<?>) {
            final Optional<?> o = (Optional<?>) in;
            return o.map(JsonFormatter::jsonValue).orElse("null");
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
     * Creates and return a new JsonFormatter with no fields added to the
     * renderer.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the entity
     * @return a new JsonFormatter with no fields added to the renderer
     */
    public static <ENTITY> JsonFormatter<ENTITY> noneOf(Manager<ENTITY> manager) {
        return new JsonFormatter<>();
    }

    /**
     * Creates and return a new JsonFormatter with all the Entity fields added
     * to the renderer. The field(s) will be rendered using their default class
     * renderer.
     * <p>
     * <em>N.B</em> This method can only be called <em>AFTER</em> the
     * application's
     * {@link com.speedment.core.runtime.SpeedmentApplicationLifecycle} has been
     * {@link com.speedment.core.runtime.SpeedmentApplicationLifecycle#start() started}
     * because it relies on the project's meta data. Thus, normally, it cannot
     * be called in a static context.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the entity
     * @return a new JsonFormatter with all the Entity fields added to the
     * renderer
     */
    public static <ENTITY> JsonFormatter<ENTITY> allOf(Manager<ENTITY> manager) {

        final JsonFormatter<ENTITY> formatter = noneOf(manager);

        final Table table = manager.getTable();

        table
            .streamOf(Column.class)
            .forEachOrdered(c -> {

                formatter.put(
                    javaVariableName(c.getName()),
                    (ENTITY entity) -> manager.get(entity, c)
                );
            });

        return formatter;
    }

    /**
     * Creates and return a new JsonFormatter with the provided Entity field(s)
     * added to the renderer. The field(s) will be rendered using their default
     * class renderer.
     * <p>
     * <em>N.B</em> This method can only be called <em>AFTER</em> the
     * application's
     * {@link com.speedment.core.runtime.SpeedmentApplicationLifecycle} has been
     * {@link com.speedment.core.runtime.SpeedmentApplicationLifecycle#start() started}
     * because it relies on the project's meta data. Thus, normally, it cannot
     * be called in a static context.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the ENTITY
     * @param fields to add to the output renderer
     * @return a new JsonFormatter with the specified fields added to the
     * renderer
     *
     */
    @SafeVarargs
    @SuppressWarnings("varargs") // Using the array in a Stream.of() is safe
    public static <ENTITY> JsonFormatter<ENTITY> of(Manager<ENTITY> manager, Field<ENTITY>... fields) {
        final JsonFormatter<ENTITY> formatter = noneOf(manager);

        final Set<String> fieldNames = Stream.of(fields).map(Field::getColumnName).collect(toSet());
        final Table table = manager.getTable();

        table
            .streamOf(Column.class)
            .filter(c -> fieldNames.contains(c.getName()))
            .forEachOrdered(c -> 
                formatter.put(
                    javaVariableName(c.getName()),
                    entity -> manager.get(entity, c)
                )
            );

        return formatter;
    }

}
