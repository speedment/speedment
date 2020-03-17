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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.Class;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static com.speedment.common.codegen.model.Value.ofBoolean;
import static com.speedment.common.codegen.model.Value.ofNumber;
import static com.speedment.common.codegen.model.Value.ofText;

/**
 * A trait for models that contain {@link Field} components.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 * @since  2.0
 */
public interface HasFields<T extends HasFields<T>> {
    
    /**
     * Adds the specified {@link Field} to this model.
     * 
     * @param field  the new child
     * @return       a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Field field) {
        getFields().add(field.setParent(this));
        return (T) this;
    }

    /**
     * Creates a {@link Field} and adds it to this model. The field will be
     * given the default modifiers for this type, for an example, a
     * {@link Class} will be {@code public final}. The type will be constructed
     * using the default implementation.
     *
     * @param name  the name of the field
     * @param type  the type of the field
     * @return      a reference to this
     *
     * @since 2.5
     */
    default T field(final String name, String type) {
        return field(name, SimpleType.create(type));
    }

    /**
     * Creates a {@link Field} and adds it to this model. The field will be
     * given the default modifiers for this type, for an example, a
     * {@link Class} will be {@code public final}.
     *
     * @param name  the name of the field
     * @param type  the type of the field
     * @return      a reference to this
     *
     * @since 2.5
     */
    default T field(final String name, Type type) {
        return add(Field.of(name, type).public_().final_());
    }

    /**
     * Creates a public final static {@link Field} with the specified value and
     * adds it to this model.
     *
     * @param name   the name of the field
     * @param type   the type of the field
     * @param value  the value of the field
     * @return       a reference to this
     *
     * @since 2.5
     */
    default T constant(final String name, Type type, Value<?> value) {
        return add(Field.of(name, type)
            .public_().final_().static_()
            .set(value)
        );
    }

    /**
     * Creates a public final static {@link Field} with the specified value and
     * adds it to this model.
     *
     * @param name   the name of the field
     * @param type   the type of the field
     * @param value  the value of the field
     * @return       a reference to this
     *
     * @since 2.5
     */
    default T constant(final String name, Type type, String value) {
        return constant(name, type, ofText(value));
    }

    /**
     * Creates a public final static {@link Field} with the specified value and
     * adds it to this model.
     *
     * @param name   the name of the field
     * @param type   the type of the field
     * @param value  the value of the field
     * @return       a reference to this
     *
     * @since 2.5
     */
    default T constant(final String name, Type type, Number value) {
        return constant(name, type, ofNumber(value));
    }

    /**
     * Creates a public final static {@link Field} with the specified value and
     * adds it to this model.
     *
     * @param name   the name of the field
     * @param type   the type of the field
     * @param value  the value of the field
     * @return       a reference to this
     *
     * @since 2.5
     */
    default T constant(final String name, Type type, Boolean value) {
        return constant(name, type, ofBoolean(value));
    }
    
    /**
     * Adds all the specified {@link Field} members to this model.
     * 
     * @param fields  the new children
     * @return        a reference to this
     */
    @SuppressWarnings("unchecked")
    default T addAllFields(final Collection<? extends Field> fields) {
        fields.forEach(this::add);
        return (T) this;
    }
    
    /**
     * Returns a list of all the fields in this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  all the fields 
     */
    List<Field> getFields();
}