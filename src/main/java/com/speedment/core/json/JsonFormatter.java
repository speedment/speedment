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
package com.speedment.core.json;

import com.speedment.core.config.model.Column;
import com.speedment.core.config.model.Table;
import com.speedment.core.Buildable;
import com.speedment.core.field.Field;
import com.speedment.core.manager.Manager;
import com.speedment.core.field.doubles.DoubleField;
import com.speedment.core.field.ints.IntField;
import com.speedment.core.field.longs.LongField;
import com.speedment.core.field.reference.ComparableReferenceForeignKeyField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.ReferenceForeignKeyField;
import com.speedment.core.field.reference.string.StringReferenceForeignKeyField;
import com.speedment.core.json.impl.JsonFormatterImpl;
import com.speedment.core.platform.Platform;
import com.speedment.core.platform.component.ManagerComponent;
import static com.speedment.util.java.JavaLanguage.javaVariableName;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 * The JsonFormatter can be used to format an Entity into JSON strings. Fields
 * can be added, removed and it is also possible to change the way a field is
 * rendered into the resulting JSON String.
 * <p>
 * A field can also be rendered using another JsonFormatter when that other
 * field is a foreign key or, for that matter, another Field in any Entity.
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type
 */
public interface JsonFormatter<ENTITY> {

    // Fields
    /**
     * Adds this Field to the output renderer. The field will be rendered using
     * its default class renderer.
     *
     * @param <T> the mapped Java type of the Field
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    <T> JsonFormatter<ENTITY> put(ReferenceField<ENTITY, T> field);

    /**
     * Adds this Integer Field to the output renderer. The field will be
     * rendered using its default class renderer.
     *
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    JsonFormatter<ENTITY> put(IntField<ENTITY> field);

    /**
     * Adds this Long Field to the output renderer. The field will be rendered
     * using its default class renderer.
     *
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    JsonFormatter<ENTITY> put(LongField<ENTITY> field);

    /**
     * Adds this Double Field to the output renderer. The field will be rendered
     * using its default class renderer.
     *
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    JsonFormatter<ENTITY> put(DoubleField<ENTITY> field);

    // Foreign key fields.
    /**
     * Adds this ReferenceForeignKeyField to the output renderer. The field will
     * be rendered such that the foreign key object will be retrieved and then
     * the foreign key object will be rendered using the provided
     * {@code JsonFormatter<FK_ENTITY>}.
     *
     * @param <T> the mapped Java type of the Field
     * @param <FK_ENTITY> the mapped Java type of the foreign key Field
     * @param field to add to the renderer
     * @param fkFormatter the foreign key {@code JsonFormatter<FK_ENTITY>}
     * @return a reference to a resulting JsonFormatter
     */
    <T, FK_ENTITY> JsonFormatter<ENTITY> put(ReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, JsonFormatter<FK_ENTITY> fkFormatter);

    /**
     * Adds this ComparableReferenceForeignKeyField to the output renderer. The
     * field will be rendered such that the foreign key object will be retrieved
     * and then the foreign key object will be rendered using the provided
     * {@code JsonFormatter<FK_ENTITY>}.
     *
     * @param <T> the mapped Java type of the Field
     * @param <FK_ENTITY> the mapped Java type of the foreign key Field
     * @param field to add to the renderer
     * @param fkFormatter the foreign key {@code JsonFormatter<FK_ENTITY>}
     * @return a reference to a resulting JsonFormatter
     */
    <T extends Comparable<? super T>, FK_ENTITY> JsonFormatter<ENTITY> put(ComparableReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, JsonFormatter<FK_ENTITY> fkFormatter);

    /**
     * Adds this StringReferenceForeignKeyField to the output renderer. The
     * field will be rendered such that the foreign key object will be retrieved
     * and then the foreign key object will be rendered using the provided
     * {@code JsonFormatter<FK_ENTITY>}.
     *
     * @param <FK_ENTITY> the mapped Java type of the foreign key Field
     * @param field to add to the renderer
     * @param fkFormatter the foreign key {@code JsonFormatter<FK_ENTITY>}
     * @return a reference to a resulting JsonFormatter
     */
    <FK_ENTITY> JsonFormatter<ENTITY> put(StringReferenceForeignKeyField<ENTITY, FK_ENTITY> field, JsonFormatter<FK_ENTITY> fkFormatter);

    // Label-and-getter pairs
    /**
     * Adds a field that corresponds to the label to the output renderer. The
     * field will be rendered as an {@code Object} of type T by applying the
     * provided getter on the Entity and then feeding the returned value to the
     * default class renderer.
     *
     * @param <T> the mapped Java type of the Field
     * @param label the name of the field to add to the output renderer
     * @param getter to apply to the Entity
     * @return a reference to a resulting JsonFormatter
     */
    <T> JsonFormatter<ENTITY> put(String label, Function<ENTITY, T> getter);

    /**
     * Adds a field that corresponds to the label to the output renderer. The
     * field will be rendered as a {@code double} by applying the provided
     * getter on the Entity and then feeding the returned value to the default
     * class renderer.
     *
     * @param label the name of the field to add to the output renderer
     * @param getter to apply to the Entity
     * @return a reference to a resulting JsonFormatter
     */
    JsonFormatter<ENTITY> putDouble(String label, ToDoubleFunction<ENTITY> getter);

    /**
     * Adds a field that corresponds to the label to the output renderer. The
     * field will be rendered as an {@code int} by applying the provided getter
     * on the Entity and then feeding the returned value to the default class
     * renderer.
     *
     * @param label the name of the field to add to the output renderer
     * @param getter to apply to the Entity
     * @return a reference to a resulting JsonFormatter
     */
    JsonFormatter<ENTITY> putInt(String label, ToIntFunction<ENTITY> getter);

    /**
     * Adds a field that corresponds to the label to the output renderer. The
     * field will be rendered as a {@code long} by applying the provided getter
     * on the Entity and then feeding the returned value to the default class
     * renderer.
     *
     * @param label the name of the field to add to the output renderer
     * @param getter to apply to the Entity
     * @return a reference to a resulting JsonFormatter
     */
    JsonFormatter<ENTITY> putLong(String label, ToLongFunction<ENTITY> getter);

    // Label-and-getter with custom formatter
    /**
     * Adds a field that corresponds to the label to the output renderer. The
     * field will be rendered such that the foreign key object will be retrieved
     * and then the foreign key object will be rendered using the provided
     * {@code JsonFormatter<FK_ENTITY>}.
     *
     * @param <FK_ENTITY> the mapped Java type of an Entity
     * @param label the name of the field to add to the output renderer
     * @param getter fkFormatter the foreign key
     * {@code JsonFormatter<FK_ENTITY>}
     * @param fkFormatter the foreign key {@code JsonFormatter<FK_ENTITY>}
     * @return a reference to a resulting JsonFormatter
     */
    <FK_ENTITY> JsonFormatter<ENTITY> put(String label, Function<ENTITY, FK_ENTITY> getter, JsonFormatter<FK_ENTITY> fkFormatter);

    // Label-and-streamer with custom formatter.
    /**
     * Adds a field that corresponds to the label to the output renderer. The
     * field will be rendered by applying the provided streamer that will
     * provide a {@code Stream} of type FK_ENTITY from an ENTITY. Each Stream
     * element will then be rendered in a JSON array by applying the provided
     * fkFormatter.
     *
     * @param <FK_ENTITY> the mapped Java type of an Entity
     * @param label the name of the field to add to the output renderer
     * @param streamer that can produce a {@code Stream<FK_ENTITY>} from an
     * {@code ENTITY}
     * @param fkFormatter the foreign key {@code JsonFormatter<FK_ENTITY>}
     * @return a reference to a resulting JsonFormatter
     */
    <FK_ENTITY> JsonFormatter<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, JsonFormatter<FK_ENTITY> fkFormatter);

    /**
     * Adds a field that corresponds to the label to the output renderer. The
     * field will be rendered by applying the provided streamer that will
     * provide a {@code Stream<FK_ENTITY>} from an ENTITY. Each Stream element
     * will then be rendered by the provided custom fkMapper that produces an
     * Object that is subsequently rendered using its default class renderer.
     *
     * @param <FK_ENTITY> the mapped Java type of an Entity
     * @param label the name of the field to add to the output renderer
     * @param streamer that can produce a {@code Stream<FK_ENTITY>} from an
     * {@code ENTITY}
     * @param fkMapper the foreign key {@code JsonFormatter<FK_ENTITY>}
     * @return a reference to a resulting JsonFormatter
     */
    <FK_ENTITY> JsonFormatter<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Function<FK_ENTITY, String> fkMapper);

    // Removers by label
    /**
     * Removes the field that corresponds to the provided label from the output
     * renderer. If the provided label does not correspond to a field currently
     * existing in the output renderer, the action is silently ignored (no-op).
     *
     * @param label the name of the field to remove from the output renderer
     * @return a reference to a resulting JsonFormatter
     */
    JsonFormatter<ENTITY> remove(String label);

    /**
     * Removes the field that corresponds to the provided label from the output
     * renderer. If the provided label does not correspond to a field currently
     * existing in the output renderer, the action is silently ignored (no-op).
     *
     * @param label the name of the field to remove from the output renderer
     * @return a reference to a resulting JsonFormatter
     */
    /**
     * Removes this Field from the output renderer. If the provided Field does
     * not correspond to a Field currently existing in the output renderer, the
     * action is silently ignored (no-op).
     *
     * @param <T> the mapped Java type of the Field
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    <T> JsonFormatter<ENTITY> remove(ReferenceField<ENTITY, T> field);

    String apply(ENTITY entity);

    /**
     * Creates and return a new JsonFormatter with no fields added to the
     * renderer.
     *
     * @param <ENTITY> the Entity type
     * @param entityClass the class of the ENTITY
     * @return a new JsonFormatter with no fields added to the renderer
     */
    public static <ENTITY> JsonFormatter<ENTITY> noneOf(Class<ENTITY> entityClass) {
        return new JsonFormatterImpl<>();
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
     * @param entityClass the class of the ENTITY
     * @return a new JsonFormatter with all the Entity fields added to the
     * renderer
     */
    public static <ENTITY> JsonFormatter<ENTITY> allOf(Class<ENTITY> entityClass) {

        final JsonFormatter<ENTITY> formatter = noneOf(entityClass);

        final Manager<?, ENTITY, ? extends Buildable<ENTITY>> manager = Platform.get()
            .get(ManagerComponent.class)
            .managerOf(entityClass);

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
     * @param entityClass the class of the ENTITY
     * @param fields to add to the output renderer
     * @return a new JsonFormatter with the specified fields added to the
     * renderer
     *
     */
    @SafeVarargs
    @SuppressWarnings("varargs") // Using the array in a Stream.of() is safe
    public static <ENTITY> JsonFormatter<ENTITY> of(Class<ENTITY> entityClass, Field<ENTITY>... fields) {
        final JsonFormatter<ENTITY> formatter = new JsonFormatterImpl<>();

        final Manager<?, ENTITY, ? extends Buildable<ENTITY>> manager = Platform.get()
            .get(ManagerComponent.class)
            .managerOf(entityClass);

        final Set<String> fieldNames = Stream.of(fields).map(f -> f.getColumn().getName()).collect(toSet());
        final Table table = manager.getTable();

        table
            .streamOf(Column.class)
            .filter(c -> fieldNames.contains(c.getName()))
            .forEachOrdered(c -> {

                formatter.put(
                    javaVariableName(c.getName()),
                    (ENTITY entity) -> manager.get(entity, c)
                );
            });

        return formatter;
    }

}
