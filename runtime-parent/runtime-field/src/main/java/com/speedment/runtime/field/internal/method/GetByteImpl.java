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

import com.speedment.runtime.field.method.ByteGetter;
import com.speedment.runtime.field.method.GetByte;
import com.speedment.runtime.field.trait.HasByteValue;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetByte}-interface.
 * 
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetByteImpl<ENTITY, D> implements GetByte<ENTITY, D> {
    
    private final HasByteValue<ENTITY, D> field;
    private final ByteGetter<ENTITY> getter;
    
    public GetByteImpl(HasByteValue<ENTITY, D> field, ByteGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasByteValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public byte applyAsByte(ENTITY instance) {
        return getter.applyAsByte(instance);
    }
}