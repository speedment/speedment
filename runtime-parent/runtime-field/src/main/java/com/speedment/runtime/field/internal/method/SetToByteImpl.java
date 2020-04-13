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

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.field.method.SetToByte;
import com.speedment.runtime.field.trait.HasByteValue;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code byte}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class SetToByteImpl<ENTITY, D> implements SetToByte<ENTITY, D> {
    
    private final HasByteValue<ENTITY, D> field;
    private final byte newValue;
    
    public SetToByteImpl(HasByteValue<ENTITY, D> field, byte newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasByteValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public byte getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        field.setter().setAsByte(entity, newValue);
        return entity;
    }
}