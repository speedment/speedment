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
package com.speedment.codegen.model.class_;

import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.modifier.ClassModifier_;
import com.speedment.codegen.model.modifier.FieldModifier_;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Class_ extends ClassAndInterfaceBase<Class_, ClassModifier_> {

    private final List<Constructor_> constructors;
    private Type_ superClassType;
    private Class<?> superClass;

    public Class_() {
        super(ClassModifier_.class);
        constructors = new ArrayList<>();
    }

    public Class_(final String className) {
        this();
        setName(className);
    }

    public Class_(String className, Class<?> superClass) {
        this();
        setName(className);
        setSuperClass(superClass);
    }

    public Class_(String className, Type_ superClass) {
        this();
        setName(className);
        setSuperClassType(superClass);
    }

    public Class_ add(Constructor_ constructor) {
        getConstructors().add(constructor);
        return this;
    }

    public List<Constructor_> getConstructors() {
        return constructors;
    }

    @Override
    public Type getModelType() {
        return Type.CLASS;
    }

    public Class<?> getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class<?> superClass) {
        this.superClass = superClass;
        this.setSuperClassType(new Type_(superClass));
    }

    public Type_ getSuperClassType() {
        return superClassType;
    }

    public void setSuperClassType(Type_ superClassType) {
        this.superClassType = superClassType;
        this.superClass = superClassType.getTypeClass();
    }

    public Class_ abstract_() {
        add(ClassModifier_.ABSTRACT);
        return this;
    }

    public Class_ final_() {
        add(ClassModifier_.FINAL);
        return this;
    }

    public Class_ private_() {
        add(ClassModifier_.PRIVATE);
        return this;
    }

    public Class_ protected_() {
        add(ClassModifier_.PROTECTED);
        return this;
    }

    public Class_ public_() {
        add(ClassModifier_.PUBLIC);
        return this;
    }

    public Class_ static_() {
        add(ClassModifier_.STATIC);
        return this;
    }

    public Class_ strictfp_() {
        add(ClassModifier_.STRICTFP);
        return this;
    }

}
