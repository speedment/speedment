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
package com.speedment.generator.standard.lifecycle;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorProxy;
import com.speedment.generator.translator.AbstractJavaClassTranslator;
import com.speedment.runtime.config.Project;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import static com.speedment.common.codegen.constant.DefaultType.WILDCARD;

/**
 *
 * @author Emil Forslund
 * @since  2.4.0
 */
public final class InjectorProxyTranslator extends AbstractJavaClassTranslator<Project, Class> {

    public InjectorProxyTranslator(Injector injector, Project project) {
        super(injector, project, Class::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().typeName(getSupport().projectOrThrow()) + "InjectorProxy";
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryProject((clazz, project) ->
                clazz
                    .public_()
                    .final_()
                    .add(InjectorProxy.class)
                    .add(isApplicable())
                    .add(set())
                    .add(newInstance())
                    .add(invoke())
            ).build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The default {@link " + InjectorProxy.class.getName() +
            "} implementation class for the {@link " + Project.class.getName() + 
            "} named " + getSupport().projectOrThrow().getId() + "." + Formatting.nl() +
            "<p>" + Formatting.nl() +
            "An InjectorProxy is used to reduce the required number of " +
            "{@code exports} in the module-info.java " +
            "file for a Speedment project"
            ;
    }

    private Method isApplicable() {
        return Method.of("isApplicable", boolean.class)
            .add(Field.of("clazz", SimpleParameterizedType.create(java.lang.Class.class, WILDCARD)))
            .override()
            .public_()
            .add("return InjectorProxy.samePackageOrBelow(" + getClassOrInterfaceName() + ".class).test(clazz);")
            ;
    }

    private Method set() {
        return Method.of("set", void.class)
            .add(Field.of("field", java.lang.reflect.Field.class))
            .add(Field.of("instance", java.lang.Object.class))
            .add(Field.of("value", java.lang.Object.class))
            .add(IllegalAccessException.class)
            .override()
            .public_()
            .add(
                /*"field.setAccessible(true);",*/ // Not required anymore
                "field.set(instance, value);"
            );
    }

    private Method newInstance() {
        final Type t = SimpleType.create("T");
        return Method.of("newInstance", t)
            .add(Generic.of(t))
            .add(Field.of("constructor", SimpleParameterizedType.create(java.lang.reflect.Constructor.class, t)))
            .add(Field.of("args", java.lang.Object[].class))
            .add(InstantiationException.class)
            .add(IllegalAccessException.class)
            .add(InvocationTargetException.class)
            .override()
            .public_()
            .add("return constructor.newInstance(args);");
    }

    private Method invoke() {
        return Method.of("invoke", Object.class)
            .add(Field.of("method", java.lang.reflect.Method.class))
            .add(Field.of("obj", Object.class))
            .add(Field.of("args", java.lang.Object[].class))
            .add(IllegalAccessException.class)
            .add(IllegalArgumentException.class)
            .add(InvocationTargetException.class)
            .override()
            .public_()
            .add("return method.invoke(obj, args);");
    }

}