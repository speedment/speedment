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
import com.speedment.runtime.config.mutator.DocumentMutator;

/**
 * Trait for {@link Document} implementations that implement the
 * {@link #mutator()} method. Mutable {@code Documents} can be modified using a
 * specified typed mutator class, specified in the {@code T} type parameter.
 *
 * @param <T>  mutator type
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */

@FunctionalInterface
public interface HasMutator<T extends DocumentMutator<?>> {

    /**
     * Returns a {@link DocumentMutator} for this Document. A DocumentMutator
     * allows a Document to be updated in a type safe way.
     *
     * @return a DocumentMutator for this Document
     * @throws UnsupportedOperationException if this Document does not support
     * mutation. For example, an immutable {@link Document} might elect to throw
     * an exception upon a call to this method.
     */
    T mutator();

}
