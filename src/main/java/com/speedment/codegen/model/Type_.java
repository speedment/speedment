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
package com.speedment.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Type_ implements CodeModel {

    public static final Type_ INT_PRIMITIVE = new Type_(int.class);
    public static final Type_ INTEGER = new Type_(Integer.class);
    public static final Type_ LONG_PRIMITIVE = new Type_(long.class);
    public static final Type_ LONG = new Type_(Long.class);
    public static final Type_ STRING = new Type_(String.class);

    private String typeName;
    private Class<?> typeClass;
    private int arrayDimension;
    private final List<Type_> genericTypes;

    public Type_(Class<?> typeClass) {
        this(typeClass, 0);
    }

    public Type_(Class<?> typeClass, int arrayDimension) {
        this.typeName = null;
        this.typeClass = typeClass;
        this.arrayDimension = arrayDimension;
        genericTypes = new ArrayList<>();
    }

    public Type_(String typeName, int arrayDimension) {
        this.typeName = typeName;
        this.typeClass = null;
        this.arrayDimension = arrayDimension;
        genericTypes = new ArrayList<>();
    }

    Type_ add(Type_ genericType) {
        genericType.add(genericType);
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(Class<?> typeClass) {
        this.typeClass = typeClass;
    }

    public boolean isArray() {
        return arrayDimension == 0;
    }

    public void setArrayDimension(int arrayDimension) {
        this.arrayDimension = arrayDimension;
    }

    public List<Type_> getGenericTypes() {
        return genericTypes;
    }

	@Override
	public Type getType() {
		return Type.TYPE;
	}
}
