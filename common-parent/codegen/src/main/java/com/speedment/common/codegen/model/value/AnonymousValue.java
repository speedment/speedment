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
package com.speedment.common.codegen.model.value;

import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.trait.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A value representing a reference to an abstract implementation used
 * anonymously to set a field.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public interface AnonymousValue
extends Value<Type>,
        HasImports<AnonymousValue>,
        HasValues<AnonymousValue>,
        HasMethods<AnonymousValue>,
        HasFields<AnonymousValue>,
        HasInitializers<AnonymousValue>,
        HasClasses<AnonymousValue> {

    @Override
    AnonymousValue setValue(Type value);

    /**
     * Returns a list of type parameters to set when instantiating the anonymous
     * type.
     * <p>
     * The returned list is mutable.
     *
     * @return list of type parameters
     */
    List<Type> getTypeParameters();

    /**
     * Adds the specified type parameter to the end of the
     * {@link #getTypeParameters} list.
     *
     * @param typeParameter  the type parameter to add
     * @return               a reference to this
     */
    default AnonymousValue add(Type typeParameter) {
        getTypeParameters().add(typeParameter);
        return this;
    }
}