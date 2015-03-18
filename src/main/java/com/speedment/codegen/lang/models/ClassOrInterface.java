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
package com.speedment.codegen.lang.models;

import com.speedment.codegen.lang.interfaces.Annotable;
import com.speedment.codegen.lang.interfaces.Callable;
import com.speedment.codegen.lang.interfaces.Classable;
import com.speedment.codegen.lang.interfaces.Copyable;
import com.speedment.codegen.lang.interfaces.Documentable;
import com.speedment.codegen.lang.interfaces.Fieldable;
import com.speedment.codegen.lang.interfaces.Generable;
import com.speedment.codegen.lang.interfaces.Initalizable;
import com.speedment.codegen.lang.interfaces.Interfaceable;
import com.speedment.codegen.lang.interfaces.Methodable;
import com.speedment.codegen.lang.interfaces.Modifiable;
import com.speedment.codegen.lang.interfaces.Nameable;

/**
 *
 * @author Emil Forslund
 * @param <T>
 */
public interface ClassOrInterface<T extends ClassOrInterface<T>> extends 
    Copyable<T>, Callable<T>, Nameable<T>, Documentable<T>, Generable<T>,
    Interfaceable<T>, Classable<T>, Methodable<T>, Fieldable<T>, 
    Annotable<T>, Modifiable<T>, Initalizable<T> {}