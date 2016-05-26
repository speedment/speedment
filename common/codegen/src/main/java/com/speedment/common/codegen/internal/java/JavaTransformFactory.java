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
package com.speedment.common.codegen.internal.java;

import com.speedment.common.codegen.TransformFactory;
import com.speedment.common.codegen.model.Annotation;
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.EnumConstant;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Initializer;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.InterfaceField;
import com.speedment.common.codegen.model.InterfaceMethod;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.JavadocTag;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.common.codegen.model.modifier.Modifier;
import com.speedment.common.codegen.internal.DefaultTransformFactory;
import com.speedment.common.codegen.internal.java.view.AnnotationUsageView;
import com.speedment.common.codegen.internal.java.view.AnnotationView;
import com.speedment.common.codegen.internal.java.view.ClassView;
import com.speedment.common.codegen.internal.java.view.ConstructorView;
import com.speedment.common.codegen.internal.java.view.EnumConstantView;
import com.speedment.common.codegen.internal.java.view.EnumView;
import com.speedment.common.codegen.internal.java.view.FieldView;
import com.speedment.common.codegen.internal.java.view.FileView;
import com.speedment.common.codegen.internal.java.view.GenericView;
import com.speedment.common.codegen.internal.java.view.ImportView;
import com.speedment.common.codegen.internal.java.view.InitalizerView;
import com.speedment.common.codegen.internal.java.view.InterfaceFieldView;
import com.speedment.common.codegen.internal.java.view.InterfaceMethodView;
import com.speedment.common.codegen.internal.java.view.InterfaceView;
import com.speedment.common.codegen.internal.java.view.JavadocTagView;
import com.speedment.common.codegen.internal.java.view.JavadocView;
import com.speedment.common.codegen.internal.java.view.MethodView;
import com.speedment.common.codegen.internal.java.view.ModifierView;
import com.speedment.common.codegen.internal.java.view.TypeView;
import com.speedment.common.codegen.internal.java.view.value.ArrayValueView;
import com.speedment.common.codegen.internal.java.view.value.BooleanValueView;
import com.speedment.common.codegen.internal.java.view.value.EnumValueView;
import com.speedment.common.codegen.internal.java.view.value.NumberValueView;
import com.speedment.common.codegen.internal.java.view.value.ReferenceValueView;
import com.speedment.common.codegen.internal.java.view.value.TextValueView;
import com.speedment.common.codegen.internal.model.value.ArrayValue;
import com.speedment.common.codegen.internal.model.value.BooleanValue;
import com.speedment.common.codegen.internal.model.value.EnumValue;
import com.speedment.common.codegen.internal.model.value.NumberValue;
import com.speedment.common.codegen.internal.model.value.ReferenceValue;
import com.speedment.common.codegen.internal.model.value.TextValue;

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
        install(Initializer.class, InitalizerView.class);
    }
}