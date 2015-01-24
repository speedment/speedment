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

import com.speedment.codegen.CodeUtil;
import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.annotation.Annotatable;
import com.speedment.codegen.model.method.Method_;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.block.Initializable;
import com.speedment.codegen.model.block.InitializerBlock_;
import com.speedment.codegen.model.dependency_.Dependable;
import com.speedment.codegen.model.dependency_.Dependency_;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.field.Fieldable;
import com.speedment.codegen.model.method.Methodable;
import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.codegen.model.modifier.Modifier_;
import com.speedment.codegen.model.package_.Packagable;
import com.speedment.codegen.model.package_.Package_;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 * @param <M>
 */
public abstract class ClassAndInterfaceBase<T extends ClassAndInterfaceBase<T, M>, M extends Enum<M> & Modifier_<M>>
        implements CodeModel, Modifiable<M>, Annotatable, Fieldable, Methodable, Interfaceable, Nameable, Packagable, Initializable, Nestable, Dependable {
    
    private final List<Interface_> interfaces;
    private final List<Field_> fields;
    private final List<Method_> methods;
    private final Set<M> modifiers;
    private final List<Annotation_> annotations;
    private final List<ClassAndInterfaceBase<?, ?>> nestedClasses;
    private final List<InitializerBlock_> initializers;
    private final Set<Dependency_> dependencies;
    private Package_ pagage;
    private CharSequence name;
    
    public ClassAndInterfaceBase(final Class<M> mClass) {
        fields = new ArrayList<>();
        methods = new ArrayList<>();
        interfaces = new ArrayList<>();
        modifiers = EnumSet.noneOf(mClass);
        annotations = new ArrayList<>();
        nestedClasses = new ArrayList<>();
        initializers = new ArrayList<>();
        dependencies = new HashSet<>();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T add(final Interface_ interf) {
        getInterfaces().add(interf);
        return (T) this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T add(final Field_ field) {
        getFields().add(field);
        return (T) this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T add(final Method_ method_) {
        getMethods().add(method_);
        return (T) this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T add(final Annotation_ annotation) {
        getAnnotations().add(annotation);
        return (T) this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T add(final Class_ nestedClass) {
        getNestedClasses().add(nestedClass);
        return (T) this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T add(final InitializerBlock_ initializer) {
        getInitializers().add(initializer);
        return (T) this;
    }

//    public T add(M classModifier_) {
//        getModifiers().add(classModifier_);
//        return (T) this;
//    }Modifier_<M>
    @SuppressWarnings("unchecked")
    @Override
    public T add(final M firstClassModifier_m, final M... restClassModifiers) {
        getModifiers().add(firstClassModifier_m);
        Stream.of(restClassModifiers).forEach(getModifiers()::add);
        return (T) this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean has(Annotation_ annotation_) {
        return annotations.contains(annotation_);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T set(final Set<M> newSet) {
        getModifiers().clear();
        getModifiers().addAll(newSet);
        return (T) this;
    }
    
    @Override
    public List<Interface_> getInterfaces() {
        return interfaces;
    }
    
    @Override
    public List<Field_> getFields() {
        return fields;
    }
    
    @Override
    public List<Method_> getMethods() {
        return methods;
    }
    
    @Override
    public Package_ getPackage() {
        return pagage;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T setPackage(final Package_ pagage) {
        this.pagage = pagage;
        return (T) this;
    }
    
    @Override
    public CharSequence getName() {
        return name;
    }
    
    @Override
    public T setName(final CharSequence name) {
        this.name = name;
        return (T) this;
    }
    
    @Override
    public Set<M> getModifiers() {
        return modifiers;
    }
    
    @Override
    public boolean is(M modifier) {
        return modifiers.contains(modifier);
    }
    
    @Override
    public List<Annotation_> getAnnotations() {
        return annotations;
    }
    
    @Override
    public List<ClassAndInterfaceBase<?, ?>> getNestedClasses() {
        return nestedClasses;
    }
    
    @Override
    public List<InitializerBlock_> getInitializers() {
        return initializers;
    }
    
    @Override
    public Set<Dependency_> getDependencies() {
        return dependencies;
    }
    
    @Override
    public boolean has(Dependency_ dep) {
        return dependencies.contains(dep);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T add(Dependency_ dep) {
        dependencies.add(dep);
        return (T) this;
    }
	
	public Type_ toType() {
		
		
		
		//return new Type_(CodeUtil.flattenName(this)).add();
		return null;
	}
}
