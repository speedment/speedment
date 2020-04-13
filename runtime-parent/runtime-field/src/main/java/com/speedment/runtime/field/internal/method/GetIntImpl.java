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
package com.speedment.runtime.field.internal.method;

import com.speedment.runtime.field.method.GetInt;
import com.speedment.runtime.field.method.IntGetter;
import com.speedment.runtime.field.trait.HasIntValue;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetInt}-interface.
 * 
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetIntImpl<ENTITY, D> implements GetInt<ENTITY, D> {
    
    private final HasIntValue<ENTITY, D> field;
    private final IntGetter<ENTITY> getter;
    
    public GetIntImpl(HasIntValue<ENTITY, D> field, IntGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasIntValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public int applyAsInt(ENTITY instance) {
        return getter.applyAsInt(instance);
    }
}