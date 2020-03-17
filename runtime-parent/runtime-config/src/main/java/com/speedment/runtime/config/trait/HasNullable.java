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
package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;

import java.util.Map;

import static com.speedment.runtime.config.util.TraitUtil.viewOf;

/**
 * Trait for {@link Document} implementations that implement the 
 * {@link #isNullable()} method.
 * 
 * @author   Emil Forslund
 * @version  2.3.0
 */
public interface HasNullable extends Document {
    
    /**
     * The various ways a nullable column can be implemented in the generated
     * code.
     */
    enum ImplementAs {
        
        /**
         * This option means that the getter for a column will return an
         * {@code Optional<T>} where {@code T} is the java type. If a primitive
         * type mapper is used, one of the primitive implementations like
         * {@code OptionalInt} might be used instead.
         */
        OPTIONAL,
        
        /**
         * This option means that the getter for a column will return the value
         * straight off. If the value is a {@code Long}, then a {@code Long}
         * will be returned.
         */
        WRAPPER
    }

    /**
     * Returns whether or not this column can hold {@code null} values.
     *
     * @return  {@code true} if null values are tolerated, else {@code false}
     */
    default boolean isNullable() {
        return getAsBoolean(HasNullableUtil.NULLABLE).orElse(true);
    }
    
    /**
     * Returns the implementation that should be used when generating getters
     * for this column. If {@link ImplementAs#OPTIONAL}, every getter will
     * return an {@code Optional<T>}, {@code OptionalInt}, etc. If 
     * {@link ImplementAs#WRAPPER}, every getter will return the type as it is, 
     * {@code Long} for an example.
     * <p>
     * If no value is specified, {@link ImplementAs#OPTIONAL} is returned.
     * 
     * @return  the nullable implementation or {@link ImplementAs#OPTIONAL} if
     *          it is not specified
     */
    default ImplementAs getNullableImplementation() {
        final String impl = getAsString(HasNullableUtil.NULLABLE_IMPLEMENTATION).orElse(null);
        if (impl == null) {
            return ImplementAs.OPTIONAL;
        } else {
            return ImplementAs.valueOf(impl);
        }
    }
    
    /**
     * Returns a wrapper of the specified document that implements the 
     * {@link HasNullable} trait. If the specified document already implements 
     * the trait, it is returned unwrapped.
     * 
     * @param document  the document to wrap
     * @return          the wrapper
     */
    static HasNullable of(Document document) {
        return viewOf(document, HasNullable.class, HasNullableView::new);
    }
}

/**
 * A wrapper class that makes sure that a given {@link Document} implements the
 * {@link HasNullable} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
class HasNullableView extends AbstractTraitView implements HasNullable {

    /**
     * Constructs a new nullable view of with the specified parent and data.
     * 
     * @param parent         the parent of the wrapped document
     * @param data           the data of the wrapped document
     * @param mainInterface  the main interface of the wrapped document
     */
    HasNullableView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}