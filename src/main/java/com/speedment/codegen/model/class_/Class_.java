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

import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.type.ScalarType_;
import com.speedment.codegen.model.modifier.ClassModifier_;
import com.speedment.util.stream.StreamUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Class_ extends ClassAndInterfaceBase<Class_, ClassModifier_> {

    private final List<Constructor_> constructors;
    private ScalarType_ superClassType;
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

    public Class_(String className, ScalarType_ superClass) {
        this();
        setName(className);
        setSuperClassType(superClass);
    }

    @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.of(super.stream(), constructors.stream());
    }

    public Class_ add(Constructor_ constructor) {
        return add(constructor, constructors::add);
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

    public Class_ setSuperClass(Class<?> superClass) {
        return set(superClass, s -> {
            this.superClass = s;
            this.superClassType = new ScalarType_(s);
        });
    }

    public ScalarType_ getSuperClassType() {
        return superClassType;
    }

    public Class_ setSuperClassType(ScalarType_ superClassType) {
        return set(superClassType, s -> {
            this.superClassType = s;
            this.superClass = s.getTypeClass();
        });
    }

    public Class_ abstract_() {
        return add(ClassModifier_.ABSTRACT);
    }

    public Class_ final_() {
        return add(ClassModifier_.FINAL);
    }

    public Class_ private_() {
        return add(ClassModifier_.PRIVATE);
    }

    public Class_ protected_() {
        return add(ClassModifier_.PROTECTED);
    }

    public Class_ public_() {
        return add(ClassModifier_.PUBLIC);
    }

    public Class_ static_() {
        return add(ClassModifier_.STATIC);
    }

    public Class_ strictfp_() {
        return add(ClassModifier_.STRICTFP);
    }
}
