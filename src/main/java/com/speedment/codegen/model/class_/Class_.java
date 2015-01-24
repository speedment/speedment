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

import com.speedment.codegen.model.Constructor_;
import com.speedment.codegen.model.modifier.ClassModifier_;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Class_ extends ClassAndInterfaceBase<Class_, ClassModifier_> {

    private final List<Constructor_> constructors;
    private Class_ superClass;

    public Class_() {
        super(ClassModifier_.class);
        constructors = new ArrayList<>();
    }

    public Class_ add(Constructor_ constructor) {
        getConstructors().add(constructor);
        return this;
    }

    public List<Constructor_> getConstructors() {
        return constructors;
    }

    @Override
    public Type getType() {
        return Type.CLASS;
    }

    public Class_ getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class_ parent) {
        this.superClass = parent;
    }

}
