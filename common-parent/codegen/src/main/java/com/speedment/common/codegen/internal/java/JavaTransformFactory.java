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
package com.speedment.common.codegen.internal.java;

import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;
import com.speedment.common.codegen.internal.java.view.*;
import com.speedment.common.codegen.internal.java.view.value.*;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.modifier.Modifier;
import com.speedment.common.codegen.model.value.*;
import com.speedment.common.codegen.provider.StandardTransformFactory;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Implementation of the {@link TransformFactory} interface that comes with
 * all the basic concepts of the Java language pre-installed.
 * 
 * @author Emil Forslund
 * @see    TransformFactory
 */
public final class JavaTransformFactory implements TransformFactory {

    private final TransformFactory inner;

    /**
     * Instantiates the JavaTransformFactory with a default name.
     */
    JavaTransformFactory() {
        this(JavaTransformFactory.class.getSimpleName());
    }
    
    /**
     * Instantiates the JavaTransformFactory with a custom name.
     * 
     * @param name  the custom name
     */
    private JavaTransformFactory(String name) {
        inner = new StandardTransformFactory(name);
        install(Class.class, ClassView::new);
        install(Interface.class, InterfaceView::new);
        install(Method.class, MethodView::new);
        install(Field.class, FieldView::new);
        install(Type.class, TypeView::new);
        install(Modifier.class, ModifierView::new);
        install(Javadoc.class, JavadocView::new);
        install(LicenseTerm.class, LicenseTermView::new);
        install(JavadocTag.class, JavadocTagView::new);
        install(Import.class, ImportView::new);
        install(Generic.class, GenericView::new);
        install(Enum.class, EnumView::new);
        install(EnumConstant.class, EnumConstantView::new);
        install(Annotation.class, AnnotationView::new);
        install(AnnotationUsage.class, AnnotationUsageView::new);
        install(ArrayValue.class, ArrayValueView::new);
        install(BooleanValue.class, BooleanValueView::new);
        install(EnumValue.class, EnumValueView::new);
        install(NumberValue.class, NumberValueView::new);
        install(ReferenceValue.class, ReferenceValueView::new);
        install(TextValue.class, TextValueView::new);
        install(InterfaceMethod.class, InterfaceMethodView::new);
        install(InterfaceField.class, InterfaceFieldView::new);
        install(Constructor.class, ConstructorView::new);
        install(File.class, FileView::new);
        install(Initializer.class, InitalizerView::new);
        install(AnonymousValue.class, AnonymousValueView::new);
        install(InvocationValue.class, InvocationValueView::new);
    }

    @Override
    public String getName() {
        return inner.getName();
    }

    @Override
    public <A, T extends Transform<A, String>> TransformFactory install(java.lang.Class<A> from, Supplier<T> transformer) {
        return inner.install(from, transformer);
    }

    @Override
    public <A, B, T extends Transform<A, B>> TransformFactory install(java.lang.Class<A> from, java.lang.Class<B> to, Supplier<T> transformer) {
        return inner.install(from, to, transformer);
    }

    @Override
    public <A, T extends Transform<A, ?>> Set<Map.Entry<java.lang.Class<?>, T>> allFrom(java.lang.Class<A> from) {
        return inner.allFrom(from);
    }
}