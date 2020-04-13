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

import com.speedment.runtime.field.method.GetReference;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.trait.HasReferenceValue;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetReference}-interface.
 * 
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * @param <T>      the java type
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetReferenceImpl<ENTITY, D, T> implements GetReference<ENTITY, D, T> {
    
    private final HasReferenceValue<ENTITY, D, T> field;
    private final ReferenceGetter<ENTITY, T> getter;

    public GetReferenceImpl(HasReferenceValue<ENTITY, D, T> field, ReferenceGetter<ENTITY, T> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }

    @Override
    public HasReferenceValue<ENTITY, D, T> getField() {
        return field;
    }

    @Override
    public T apply(ENTITY instance) {
        return getter.apply(instance);
    }
}