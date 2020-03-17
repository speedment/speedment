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

/**
 * Trait for code generator models that can be deep-copied. If the model
 * implementing this trait also implements {@link HasParent}, then the parent
 * property <em>will not</em> be copied.
 *
 * @param <T>  the extending type
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface HasCopy<T extends HasCopy<T>> {
    
    /**
     * Create a deep copy of this model if it is mutable. If this class is not
     * mutable, then this method may return itself. If the model implements
     * {@link HasParent}, then the parent property <em>will not</em> be copied.
     * Instead it will be set to {@code null}.
     *
     * @return  the copy
     */
    T copy();
}