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
package com.speedment.codegen.model;

import com.speedment.annotation.Api;
import com.speedment.codegen.model.trait.HasAnnotationUsage;
import com.speedment.codegen.model.trait.HasCall;
import com.speedment.codegen.model.trait.HasClasses;
import com.speedment.codegen.model.trait.HasCopy;
import com.speedment.codegen.model.trait.HasFields;
import com.speedment.codegen.model.trait.HasGenerics;
import com.speedment.codegen.model.trait.HasImplements;
import com.speedment.codegen.model.trait.HasInitalizers;
import com.speedment.codegen.model.trait.HasJavadoc;
import com.speedment.codegen.model.trait.HasMethods;
import com.speedment.codegen.model.trait.HasModifiers;
import com.speedment.codegen.model.trait.HasName;

/**
 * An abstract base class to share functionality between the models 
 * {@link Class}, {@link Enum} and {@link Interface}.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 * @since  2.0
 */
@Api(version = "2.3")
public interface ClassOrInterface<T extends ClassOrInterface<T>> extends 
    HasCopy<T>, HasCall<T>, HasName<T>, HasJavadoc<T>, HasGenerics<T>,
    HasImplements<T>, HasClasses<T>, HasMethods<T>, HasFields<T>, 
    HasAnnotationUsage<T>, HasModifiers<T>, HasInitalizers<T> {}