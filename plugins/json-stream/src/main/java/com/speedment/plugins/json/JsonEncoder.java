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
package com.speedment.plugins.json;

import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.field.trait.ReferenceForeignKeyFieldTrait;
import com.speedment.runtime.manager.Manager;
import static com.speedment.runtime.util.NullUtil.requireNonNullElements;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 * An encoder that can transform Speedment entities to JSON.
 * <p>
 * Example usage:
 * <code>
 *      Manager&lt;Address&gt; addresses = app.managerOf(Address.class);
 *      Manager&lt;Employee&gt; employees = app.managerOf(Employee.class);
 *      Manager&lt;Employee&gt; departments = app.managerOf(Department.class);
 * 
 *      // Include street, zip-code and city only.
 *      JconEncoder&lt;Address&gt; addrEncoder = JsonEncoder.noneOf(addresses)
 *          .add(Address.STREET)
 *          .add(Address.ZIPCODE)
 *          .add(Address.CITY);
 * 
 *      // Do not expose SSN but do inline the home address.
 *      JconEncoder&lt;Employee&gt; empEncoder = JsonEncoder.allOf(employees)
 *          .remove(Employee.SSN)
 *          .remove(Employee.DEPARTMENT); // Go the other way around.
 *          .add(Employee.HOME_ADDRESS, addrEncoder); // Foreign key
 * 
 *      // Inline every employee in the department.
 *      JconEncoder&lt;Department&gt; depEncoder = JsonEncoder.allOf(departments)
 *          .putStreamer("employees", Department::employees, empEncoder);
 * 
 *      String json = depEncoder.apply(departments.findAny().get());
 * </code>
 * 
 * @author          Emil Forslund
 * @param <ENTITY>  entity type
 */
public final class JsonEncoder<ENTITY> {

    protected final Map<String, Function<ENTITY, String>> getters;

    /**
     * Constructs an empty JsonEncoder with no fields added to the output
     * renderer.
     */
    private JsonEncoder() {
        this.getters = new LinkedHashMap<>();
    }

    // Fields
    public <D, T, I extends FieldTrait & ReferenceFieldTrait<ENTITY, D, T>> JsonEncoder<ENTITY> put(I field) {
        requireNonNull(field);
        final String columnName = jsonField((FieldTrait) field);
        final Function<ENTITY, T> getter = ((ReferenceFieldTrait<ENTITY, D, T>) field).getter(); // Workaround bugg
        return put(columnName, getter);
    }

    // Foreign key fields.
    public <D, T, FK_ENTITY, I extends FieldTrait & ReferenceFieldTrait<ENTITY, D, T> & ReferenceForeignKeyFieldTrait<ENTITY, D, FK_ENTITY>>
        JsonEncoder<ENTITY> put(I field, JsonEncoder<FK_ENTITY> builder) {
        requireNonNull(field);
        requireNonNull(builder);
        final String columnName = jsonField((FieldTrait) field);
        final ReferenceForeignKeyFieldTrait<ENTITY, D, FK_ENTITY> fkField = (ReferenceForeignKeyFieldTrait< ENTITY, D, FK_ENTITY>) field; // Workaround bugg
        return put(columnName, fkField::findFrom, builder);
    }

    // Label-and-getter pairs
    public <T> JsonEncoder<ENTITY> put(String label, Function<ENTITY, T> getter) {
        requireNonNull(label);
        requireNonNull(getter);
        getters.put(label, e -> "\"" + label + "\":" + jsonValue(getter.apply(e)));
        return this;
    }

    // Label-and-getter with custom formatter
    public <FK_ENTITY> JsonEncoder<ENTITY> put(String label, Function<ENTITY, FK_ENTITY> getter, JsonEncoder<FK_ENTITY> builder) {
        requireNonNull(label);
        requireNonNull(getter);
        requireNonNull(builder);
        getters.put(label, e -> "\"" + label + "\":" + builder.apply(getter.apply(e)));
        return this;
    }

    // Label-and-streamer with custom formatter.
    public <FK_ENTITY> JsonEncoder<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, JsonEncoder<FK_ENTITY> builder) {
        requireNonNull(label);
        requireNonNull(streamer);
        requireNonNull(builder);
        getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(builder::apply).collect(joining(",")) + "]");
        return this;
    }

    public <FK_ENTITY> JsonEncoder<ENTITY> putStreamer(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Function<FK_ENTITY, String> encoder) {
        requireNonNull(label);
        requireNonNull(streamer);
        requireNonNull(encoder);
        getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(encoder).collect(joining(",")) + "]");
        return this;
    }

    // Removers by label
    public JsonEncoder<ENTITY> remove(String label) {
        requireNonNull(label);
        getters.remove(label);
        return this;
    }
    
    public JsonEncoder<ENTITY> remove(FieldTrait field) {
        requireNonNull(field);
        getters.remove(jsonField(field));
        return this;
    }

    public String apply(ENTITY entity) {
        return entity == null ? "null" : "{"
            + getters.values().stream()
            .map(g -> g.apply(entity))
            .collect(joining(","))
            + "}";
    }

    protected String jsonField(FieldTrait field) {
        requireNonNull(field);
        return formatFieldName(field.getIdentifier().columnName());
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
        return new JsonEncoder<>();
    }

    /**
     * Creates and return a new JsonEncoder with all the Entity fields added to
     * the renderer. The field(s) will be rendered using their default class
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

        manager.fields()
            .filter(ReferenceFieldTrait.class::isInstance)
            .map(f -> castReferenceFieldTrait(manager, f))
            .forEachOrdered(f
                -> {
                @SuppressWarnings("unchecked")
                final FieldIdentifier<ENTITY> fi = f.getIdentifier();
                formatter.put(
                    formatFieldName(f.getIdentifier().columnName()),
                    entity -> manager.get(entity, fi)
                );
            }
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
     * @return a new JsonEncoder with the specified fields added to the renderer
     */
    @SafeVarargs
    @SuppressWarnings("varargs") // Using the array in a Stream.of() is safe
    public static <ENTITY> JsonEncoder<ENTITY> of(Manager<ENTITY> manager, FieldTrait... fields) {
        requireNonNull(manager);
        requireNonNullElements(fields);
        final JsonEncoder<ENTITY> formatter = noneOf(manager);

        final Set<String> fieldNames = Stream.of(fields)
            .map(FieldTrait::getIdentifier)
            .map(FieldIdentifier::columnName)
            .collect(toSet());

        manager.fields()
            .filter(ReferenceFieldTrait.class::isInstance)
            .map(f -> castReferenceFieldTrait(manager, f))
            .filter(f -> fieldNames.contains(f.getIdentifier().columnName()))
            .forEachOrdered(f
                -> formatter.put(
                    formatFieldName(f.getIdentifier().columnName()),
                    entity -> manager.get(entity, f.getIdentifier())
                )
            );

        return formatter;
    }
    
    private static <ENTITY> ReferenceFieldTrait<ENTITY, ?, ?> castReferenceFieldTrait(Manager<ENTITY> mgr, FieldTrait f) {
        @SuppressWarnings("unchecked")
        final ReferenceFieldTrait<ENTITY, ?, ?> result = (ReferenceFieldTrait<ENTITY, ?, ?>) f;
        return result;
    }

    private static String formatFieldName(String externalName) {
        final StringBuilder sb = new StringBuilder(externalName);

        int startIndex = 0;
        for (int i = 0; i < externalName.length(); i++) {
            if (Character.isAlphabetic(sb.charAt(i))) {
                // Skip over any non alphabetic characers like "_"
                startIndex = i;
                break;
            }
        }

        if (sb.length() > startIndex) {
            sb.replace(
                startIndex, startIndex + 1, 
                String.valueOf(sb.charAt(startIndex)).toLowerCase()
            );
        }
        return sb.toString();
    }
}