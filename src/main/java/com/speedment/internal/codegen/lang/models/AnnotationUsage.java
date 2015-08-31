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
package com.speedment.internal.codegen.lang.models;

import com.speedment.internal.codegen.lang.interfaces.Copyable;
import com.speedment.internal.codegen.lang.interfaces.HasType;
import com.speedment.internal.codegen.lang.interfaces.HasValue;
import com.speedment.internal.codegen.lang.models.implementation.AnnotationUsageImpl;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A model that represents the usage of a particular annotation in code.
 *
 * @author Emil Forslund
 * @see Annotation
 */
public interface AnnotationUsage extends Copyable<AnnotationUsage>,
    HasType<AnnotationUsage>, HasValue<AnnotationUsage> {

    /**
     * Use the specified key-value pair when referencing the annotation. If you
     * only want to show a single value without any key, consider using the
     * {@link #set(com.speedment.codegen.lang.models.Value) set()} method.
     *
     * @param key the key
     * @param val the value
     * @return a reference to this model
     */
    AnnotationUsage put(String key, Value<?> val);

    /**
     * Returns a list of all the key-value pairs in this model.
     *
     * @return all key-value pairs
     */
    List<Map.Entry<String, Value<?>>> getValues();

    /**
     * Factory holder.
     */
    enum Factory {
        INST;
        private Function<Type, AnnotationUsage> mapper = AnnotationUsageImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     *
     * @param type the type
     * @return the new instance
     */
    static AnnotationUsage of(Type type) {
        return Factory.INST.mapper.apply(type);
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param mapper the new constructor
     */
    static void setSupplier(Function<Type, AnnotationUsage> mapper) {
        Factory.INST.mapper = requireNonNull(mapper);
    }
}
