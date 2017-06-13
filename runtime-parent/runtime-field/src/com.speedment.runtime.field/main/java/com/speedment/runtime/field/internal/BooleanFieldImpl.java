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
package com.speedment.runtime.field.internal;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.BooleanField;
import com.speedment.runtime.field.internal.method.GetBooleanImpl;
import com.speedment.runtime.field.method.BooleanGetter;
import com.speedment.runtime.field.method.BooleanSetter;
import com.speedment.runtime.field.method.GetBoolean;
import com.speedment.runtime.typemapper.TypeMapper;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class BooleanFieldImpl<ENTITY, D> implements BooleanField<ENTITY, D> {
    
    private final ColumnIdentifier<ENTITY> identifier;
    private final GetBoolean<ENTITY, D> getter;
    private final BooleanSetter<ENTITY> setter;
    private final TypeMapper<D, Boolean> typeMapper;
    private final boolean unique;
    
    public BooleanFieldImpl(
            ColumnIdentifier<ENTITY> identifier,
            BooleanGetter<ENTITY> getter,
            BooleanSetter<ENTITY> setter,
            TypeMapper<D, Boolean> typeMapper,
            boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = new GetBooleanImpl<>(this, getter);
        this.setter     = requireNonNull(setter);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    @Override
    public ColumnIdentifier<ENTITY> identifier() {
        return identifier;
    }
    
    @Override
    public BooleanSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public GetBoolean<ENTITY, D> getter() {
        return getter;
    }
    
    @Override
    public TypeMapper<D, Boolean> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
}