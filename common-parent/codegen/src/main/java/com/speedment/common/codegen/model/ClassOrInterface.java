/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.codegen.model.trait.*;

/**
 * An abstract base class to share functionality between the models 
 * {@link Class}, {@link Enum} and {@link Interface}.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 * @since  2.0
 */
public interface ClassOrInterface<T extends ClassOrInterface<T>> extends 
    HasCopy<T>, HasCall<T>, HasName<T>, HasJavadoc<T>, HasGenerics<T>,
    HasImplements<T>, HasClasses<T>, HasMethods<T>, HasFields<T>, 
    HasAnnotationUsage<T>, HasModifiers<T>, HasInitializers<T> {}