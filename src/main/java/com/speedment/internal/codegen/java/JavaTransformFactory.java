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
package com.speedment.internal.codegen.java;

import com.speedment.internal.codegen.base.DefaultTransformFactory;
import com.speedment.internal.codegen.base.TransformFactory;
import com.speedment.internal.codegen.java.views.AnnotationUsageView;
import com.speedment.internal.codegen.java.views.InterfaceMethodView;
import com.speedment.internal.codegen.lang.models.Annotation;
import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.internal.codegen.lang.models.Enum;
import com.speedment.internal.codegen.lang.models.EnumConstant;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Interface;
import com.speedment.internal.codegen.lang.models.InterfaceField;
import com.speedment.internal.codegen.lang.models.InterfaceMethod;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.JavadocTag;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.modifiers.Modifier;
import com.speedment.internal.codegen.lang.models.values.ArrayValue;
import com.speedment.internal.codegen.lang.models.values.BooleanValue;
import com.speedment.internal.codegen.lang.models.values.EnumValue;
import com.speedment.internal.codegen.lang.models.values.NumberValue;
import com.speedment.internal.codegen.lang.models.values.ReferenceValue;
import com.speedment.internal.codegen.lang.models.values.TextValue;
import com.speedment.internal.codegen.java.views.AnnotationView;
import com.speedment.internal.codegen.java.views.ClassView;
import com.speedment.internal.codegen.java.views.ConstructorView;
import com.speedment.internal.codegen.java.views.EnumConstantView;
import com.speedment.internal.codegen.java.views.EnumView;
import com.speedment.internal.codegen.java.views.FieldView;
import com.speedment.internal.codegen.java.views.FileView;
import com.speedment.internal.codegen.java.views.GenericView;
import com.speedment.internal.codegen.java.views.ImportView;
import com.speedment.internal.codegen.java.views.InitalizerView;
import com.speedment.internal.codegen.java.views.InterfaceFieldView;
import com.speedment.internal.codegen.java.views.InterfaceView;
import com.speedment.internal.codegen.java.views.JavadocTagView;
import com.speedment.internal.codegen.java.views.JavadocView;
import com.speedment.internal.codegen.java.views.MethodView;
import com.speedment.internal.codegen.java.views.ModifierView;
import com.speedment.internal.codegen.java.views.TypeView;
import com.speedment.internal.codegen.java.views.values.ArrayValueView;
import com.speedment.internal.codegen.java.views.values.BooleanValueView;
import com.speedment.internal.codegen.java.views.values.EnumValueView;
import com.speedment.internal.codegen.java.views.values.NumberValueView;
import com.speedment.internal.codegen.java.views.values.ReferenceValueView;
import com.speedment.internal.codegen.java.views.values.TextValueView;
import com.speedment.internal.codegen.lang.models.AnnotationUsage;
import com.speedment.internal.codegen.lang.models.Constructor;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Initalizer;

/**
 * Implementation of the {@link TransformFactory} interface that comes with
 * all the basic concepts of the Java language preinstalled.
 * 
 * @author Emil Forslund
 * @see    TransformFactory
 */
public class JavaTransformFactory extends DefaultTransformFactory {
    
    /**
     * Instantiates the JavaTransformFactory with a default name.
     */
    public JavaTransformFactory() {
        this("JavaTransformFactory");
    }
    
    /**
     * Instantiates the JavaTransformFactory with a custom name.
     * 
     * @param name  the custom name
     */
    public JavaTransformFactory(String name) {
        super(name);
        
        install(Class.class, ClassView.class);
        install(Interface.class, InterfaceView.class);
        install(Method.class, MethodView.class);
        install(Field.class, FieldView.class);
		install(Type.class, TypeView.class);
		install(Modifier.class, ModifierView.class);
		install(Javadoc.class, JavadocView.class);
		install(JavadocTag.class, JavadocTagView.class);
		install(Import.class, ImportView.class);
		install(Generic.class, GenericView.class);
		install(Enum.class, EnumView.class);
		install(EnumConstant.class, EnumConstantView.class);
		install(Annotation.class, AnnotationView.class);
		install(AnnotationUsage.class, AnnotationUsageView.class);
		install(ArrayValue.class, ArrayValueView.class);
		install(BooleanValue.class, BooleanValueView.class);
		install(EnumValue.class, EnumValueView.class);
		install(NumberValue.class, NumberValueView.class);
		install(ReferenceValue.class, ReferenceValueView.class);
		install(TextValue.class, TextValueView.class);
		install(InterfaceMethod.class, InterfaceMethodView.class);
		install(InterfaceField.class, InterfaceFieldView.class);
		install(Constructor.class, ConstructorView.class);
		install(File.class, FileView.class);
        install(Initalizer.class, InitalizerView.class);
    }
}