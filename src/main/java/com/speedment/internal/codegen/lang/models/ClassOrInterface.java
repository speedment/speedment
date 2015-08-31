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

import com.speedment.internal.codegen.lang.interfaces.HasAnnotationUsage;
import com.speedment.internal.codegen.lang.interfaces.Callable;
import com.speedment.internal.codegen.lang.interfaces.HasClasses;
import com.speedment.internal.codegen.lang.interfaces.Copyable;
import com.speedment.internal.codegen.lang.interfaces.HasJavadoc;
import com.speedment.internal.codegen.lang.interfaces.HasFields;
import com.speedment.internal.codegen.lang.interfaces.HasGenerics;
import com.speedment.internal.codegen.lang.interfaces.HasInitalizers;
import com.speedment.internal.codegen.lang.interfaces.HasImplements;
import com.speedment.internal.codegen.lang.interfaces.HasMethods;
import com.speedment.internal.codegen.lang.interfaces.HasModifiers;
import com.speedment.internal.codegen.lang.interfaces.HasName;

/**
 * An abstract base class to share functionality between the models 
 * {@link Class}, {@link Enum} and {@link Interface}.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface ClassOrInterface<T extends ClassOrInterface<T>> extends 
    Copyable<T>, Callable<T>, HasName<T>, HasJavadoc<T>, HasGenerics<T>,
    HasImplements<T>, HasClasses<T>, HasMethods<T>, HasFields<T>, 
    HasAnnotationUsage<T>, HasModifiers<T>, HasInitalizers<T> {}