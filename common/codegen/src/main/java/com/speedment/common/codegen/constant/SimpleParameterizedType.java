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
package com.speedment.common.codegen.constant;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * A very simple implementation of the java {@link ParameterizedType} interface.
 * 
 * @author  Emil Forslund
 * @since   2.4.1
 */
public final class SimpleParameterizedType implements ParameterizedType {

    /**
     * Creates a new {@code SimpleParameterizedType} based on the class name of
     * the specified class and the specified parameters.
     * 
     * @param mainType    the class to get the name from
     * @param parameters  list of generic parameters to this type
     * @return            the created type
     */
    public static SimpleParameterizedType create(Type mainType, Type... parameters) {
        return create(mainType.getTypeName(), parameters);
    }
    
    /**
     * Creates a new {@code SimpleParameterizedType} with the specified absolute 
     * class name.
     * 
     * @param fullName    the absolute type name
     * @param parameters  list of generic parameters to this type
     * @return            the created simple type
     */
    public static SimpleParameterizedType create(String fullName, Type... parameters) {
        return new SimpleParameterizedType(fullName, parameters);
    }
    
    /**
     * Creates a new {@code SimpleParameterizedType} referencing the specified 
     * class in the specified file. These do not have to exist yet.
     * 
     * @param file        the file to reference
     * @param clazz       the class to reference
     * @param parameters  list of generic parameters to this type
     * @return            the new simple type
     */
    public static SimpleParameterizedType create(File file, ClassOrInterface<?> clazz, Type... parameters) {
        return create(SimpleTypeUtil.nameOf(file, clazz), parameters);
    }
    
    @Override
    public Type[] getActualTypeArguments() {
        return Arrays.copyOf(parameters, parameters.length);
    }

    @Override
    public Type getRawType() {
        return SimpleType.create(fullName);
    }

    @Override
    public Type getOwnerType() {
        throw new UnsupportedOperationException(
            "Owner types are currently not supported by CodeGen."
        );
    }

    @Override
    public String getTypeName() {
        return fullName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.fullName);
        hash = 29 * hash + Arrays.deepHashCode(this.parameters);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        else if (obj == null) { return false; }
        else if (!(obj instanceof ParameterizedType)) { return false; }
        
        final ParameterizedType other = (ParameterizedType) obj;
        return Objects.equals(fullName, other.getTypeName())
            && Arrays.deepEquals(parameters, other.getActualTypeArguments());
    }
    
    @Override
    public String toString() {
        return getTypeName();
    }
    
    private SimpleParameterizedType(String fullName, Type[] parameters) {
        this.fullName   = requireNonNull(fullName);
        this.parameters = requireNonNull(parameters);
    }
    
    private final String fullName;
    private final Type[] parameters;
    
}
