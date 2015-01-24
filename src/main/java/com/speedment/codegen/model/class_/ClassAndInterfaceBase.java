package com.speedment.codegen.model.class_;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.annotation.Annotatable;
import com.speedment.codegen.model.method.Method_;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.block.Initializable;
import com.speedment.codegen.model.block.InitializerBlock_;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.field.Fieldable;
import com.speedment.codegen.model.method.Methodable;
import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.codegen.model.modifier.Modifier_;
import com.speedment.codegen.model.package_.Packagable;
import com.speedment.codegen.model.package_.Package_;
import java.util.ArrayList;
import java.util.EnumSet;
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
        implements CodeModel, Modifiable<M>, Annotatable, Fieldable, Methodable, Interfaceable, Nameable, Packagable, Initializable, Nestable {

    private final List<Interface_> interfaces;
    private final List<Field_> fields;
    private final List<Method_> methods;
    private final Set<M> modifiers;
    private final List<Annotation_> annotations;
    private final List<ClassAndInterfaceBase<?, ?>> nestedClasses;
    private final List<InitializerBlock_> initializers;
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
    public void setName(final CharSequence name) {
        this.name = name;
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

}
