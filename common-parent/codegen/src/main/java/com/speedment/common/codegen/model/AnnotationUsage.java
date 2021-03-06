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
package com.speedment.common.codegen.model;

import com.speedment.common.codegen.internal.model.AnnotationUsageImpl;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.trait.HasType;
import com.speedment.common.codegen.model.trait.HasValue;
import com.speedment.common.codegen.model.value.NumberValue;
import com.speedment.common.codegen.model.value.TextValue;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * A model that represents the usage of a particular annotation in code.
 *
 * @see Annotation
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface AnnotationUsage extends HasCopy<AnnotationUsage>,
    HasType<AnnotationUsage>, HasValue<AnnotationUsage> {

    /**
     * Use the specified key-value pair when referencing the annotation. If you
     * only want to show a single value without any key, consider using the
     * {@link #set(Value)} method.
     *
     * @param key the key
     * @param val the value
     * @return a reference to this model
     */
    AnnotationUsage put(String key, Value<?> val);

    /**
     * Use the specified key-value pair when referencing the annotation. If you
     * only want to show a single value without any key, consider using the
     * {@link #set(Value)} method.
     * <p>
     * This method works like {@link #put(String, Value)} except that the type
     * of the value is automatically determined as a {@link TextValue}.
     *
     * @param key       the key
     * @param textValue the value
     * @return          a reference to this model
     *
     * @since 2.5
     */
    default AnnotationUsage put(String key, String textValue) {
        return put(key, Value.ofText(textValue));
    }

    /**
     * Use the specified key-value pair when referencing the annotation. If you
     * only want to show a single value without any key, consider using the
     * {@link #set(Value)} method.
     * <p>
     * This method works like {@link #put(String, Value)} except that the type
     * of the value is automatically determined as a {@link NumberValue}.
     *
     * @param key         the key
     * @param numberValue the value
     * @return            a reference to this model
     *
     * @since 2.5
     */
    default AnnotationUsage put(String key, Number numberValue) {
        return put(key, Value.ofNumber(numberValue));
    }

    /**
     * Returns a list of all the key-value pairs in this model.
     *
     * @return all key-value pairs
     */
    List<Map.Entry<String, Value<?>>> getValues();

    /**
     * Creates a new instance implementing this interface by using the default
     * implementation.
     *
     * @param type the type
     * @return     the new instance
     */
    static AnnotationUsage of(Type type) {
        return new AnnotationUsageImpl(type);
    }
}
