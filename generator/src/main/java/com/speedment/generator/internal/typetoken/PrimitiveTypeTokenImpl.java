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
package com.speedment.generator.internal.typetoken;

import com.speedment.runtime.config.typetoken.PrimitiveTypeToken;

/**
 * The default implementation of the {@link PrimitiveTypeToken} interface.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public enum PrimitiveTypeTokenImpl implements PrimitiveTypeToken {
    
    BYTE    (PrimitiveTypeToken.Primitive.BYTE),
    SHORT   (PrimitiveTypeToken.Primitive.SHORT),
    INT     (PrimitiveTypeToken.Primitive.INT),
    LONG    (PrimitiveTypeToken.Primitive.LONG),
    FLOAT   (PrimitiveTypeToken.Primitive.FLOAT),
    DOUBLE  (PrimitiveTypeToken.Primitive.DOUBLE),
    BOOLEAN (PrimitiveTypeToken.Primitive.BOOLEAN);
    
    private final Primitive primitive;

    PrimitiveTypeTokenImpl(Primitive primitive) {
        this.primitive = primitive;
    }

    @Override
    public Primitive getPrimitiveType() {
        return primitive;
    }

    @Override
    public String getTypeName() {
        return name().toLowerCase();
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isEnum() {
        return false;
    }

    @Override
    public boolean isGeneric() {
        return false;
    }
}