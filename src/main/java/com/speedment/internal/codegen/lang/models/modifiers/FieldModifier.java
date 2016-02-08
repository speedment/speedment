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
package com.speedment.internal.codegen.lang.models.modifiers;

import com.speedment.internal.codegen.lang.models.modifiers.Keyword.final_;
import com.speedment.internal.codegen.lang.models.modifiers.Keyword.private_;
import com.speedment.internal.codegen.lang.models.modifiers.Keyword.protected_;
import com.speedment.internal.codegen.lang.models.modifiers.Keyword.public_;
import com.speedment.internal.codegen.lang.models.modifiers.Keyword.static_;
import com.speedment.internal.codegen.lang.models.modifiers.Keyword.synchronized_;
import com.speedment.internal.codegen.lang.models.modifiers.Keyword.transient_;
import com.speedment.internal.codegen.lang.models.modifiers.Keyword.volatile_;

/**
 *
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface FieldModifier<T extends FieldModifier<T>> 
extends public_<T>, protected_<T>, private_<T>, static_<T>, final_<T>, 
synchronized_<T>, transient_<T>, volatile_<T> {}