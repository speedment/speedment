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

    public static final Type_ BYTE_PRIMITIVE = new Type_(byte.class),
            SHORT_PRIMITIVE = new Type_(short.class),
            INT_PRIMITIVE = new Type_(int.class),
            LONG_PRIMITIVE = new Type_(long.class),
            FLOAT_PRIMITIVE = new Type_(float.class),
            DOUBLE_PRIMITIVE = new Type_(double.class),
            BOOLEAN_PRIMITIVE = new Type_(boolean.class),
            CHAR_PRIMITIVE = new Type_(char.class),
            BYTE = new Type_(Byte.class),
            SHORT = new Type_(Short.class),
            INT = new Type_(Integer.class),
            LONG = new Type_(Long.class),
            FLOAT = new Type_(Float.class),
            DOUBLE = new Type_(Double.class),
            BOOLEAN = new Type_(Boolean.class),
            CHARACTER = new Type_(Character.class),
            STRING = new Type_(String.class);

    private CharSequence typeName;
    private Class<?> typeClass;

    private final List<Type_> genericTypes;

    public Type_(Class<?> typeClass) {
        this(typeClass.getName());
        setTypeClass(typeClass);
    }

    public Type_(CharSequence typeName) {
        setTypeName(typeName);
        genericTypes = new ArrayList<>();
    }

    public Type_ add(Type_ genericType) {
        genericType.add(genericType);
        return this;
    }

    public CharSequence getTypeName() {
        return typeName;
    }

    public void setTypeName(CharSequence typeName) {
        this.typeName = typeName;
        try {
            this.typeClass = Class.forName(typeName.toString());
        } catch (ClassNotFoundException cnfe) {
            this.typeClass = null;
        }
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(Class<?> typeClass) {
        this.typeClass = typeClass;
        this.typeName = typeClass.getName();
    }

    public List<Type_> getGenericTypes() {
        return genericTypes;
    }

    @Override
    public Type getModelType() {
        return Type.TYPE;
    }
}
