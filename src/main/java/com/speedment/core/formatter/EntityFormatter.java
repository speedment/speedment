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
package com.speedment.core.formatter;

import com.speedment.core.field.doubles.DoubleField;
import com.speedment.core.field.ints.IntField;
import com.speedment.core.field.longs.LongField;
import com.speedment.core.field.reference.ComparableReferenceForeignKeyField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.ReferenceForeignKeyField;
import com.speedment.core.field.reference.string.StringReferenceForeignKeyField;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

/**
 * The EntityFormatter can be used to format an Entity into any kind of strings. Fields
 * can be added, removed and it is also possible to change the way a field is
 * rendered into the resulting String.
 * <p>
 * A field can also be rendered using another EntityFormatter when that other
 * field is a foreign key or, for that matter, another Field in any Entity.
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type
 * @param <R> Type of formatter
 */
public interface EntityFormatter<ENTITY, R extends EntityFormatter<ENTITY, R>> {

    // Fields
    /**
     * Adds this Field to the output renderer. The field will be rendered using
     * its default class renderer.
     *
     * @param <T> the mapped Java type of the Field
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    <T> R put(ReferenceField<ENTITY, T> field);

    /**
     * Adds this Integer Field to the output renderer. The field will be
     * rendered using its default class renderer.
     *
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    R put(IntField<ENTITY> field);

    /**
     * Adds this Long Field to the output renderer. The field will be rendered
     * using its default class renderer.
     *
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    R put(LongField<ENTITY> field);

    /**
     * Adds this Double Field to the output renderer. The field will be rendered
     * using its default class renderer.
     *
     * @param field to add to the renderer
     * @return a reference to a resulting JsonFormatter
     */
    R put(DoubleField<ENTITY> field);

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
    <T, FK_ENTITY> R put(ReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, EntityFormatter<FK_ENTITY, ?> fkFormatter);

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
    <T extends Comparable<? super T>, FK_ENTITY> R put(ComparableReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, EntityFormatter<FK_ENTITY, ?> fkFormatter);

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
    <FK_ENTITY> R put(StringReferenceForeignKeyField<ENTITY, FK_ENTITY> field, EntityFormatter<FK_ENTITY, ?> fkFormatter);

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
    <T> R put(String label, Function<ENTITY, T> getter);

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
    R putDouble(String label, ToDoubleFunction<ENTITY> getter);

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
    R putInt(String label, ToIntFunction<ENTITY> getter);

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
    R putLong(String label, ToLongFunction<ENTITY> getter);

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
    <FK_ENTITY> R put(String label, Function<ENTITY, FK_ENTITY> getter, EntityFormatter<FK_ENTITY, ?> fkFormatter);

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
    <FK_ENTITY> R putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, EntityFormatter<FK_ENTITY, ?> fkFormatter);

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
    <FK_ENTITY> R putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Function<FK_ENTITY, String> fkMapper);

    // Removers by label
    /**
     * Removes the field that corresponds to the provided label from the output
     * renderer. If the provided label does not correspond to a field currently
     * existing in the output renderer, the action is silently ignored (no-op).
     *
     * @param label the name of the field to remove from the output renderer
     * @return a reference to a resulting JsonFormatter
     */
    R remove(String label);

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
    <T> R remove(ReferenceField<ENTITY, T> field);

    String apply(ENTITY entity);

}
