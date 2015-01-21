package com.speedment.codegen.model;

import com.speedment.codegen.model.modifier.Modifier_;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author pemi
 * @param <T>
 * @param <M>
 */
public abstract class ClassAndInterfaceBase<T extends ClassAndInterfaceBase<T, M>, M extends Enum<M> & Modifier_<M>> extends CodeModel {

    private final List<Interface_> interfaces;
    private final List<Field_> fields;
    private final List<Method_> methods;
    private Package_ pagage;
    private CharSequence name;
    private final Set<M> modifiers;

    public ClassAndInterfaceBase(Class<M> mClass) {
        fields = new ArrayList<>();
        methods = new ArrayList<>();
        interfaces = new ArrayList<>();
        modifiers = EnumSet.noneOf(mClass);
    }

    public T add(Interface_ interf) {
        getInterfaces().add(interf);
        return (T) this;
    }

    public T add(Field_ field) {
        getFields().add(field);
        return (T) this;
    }

    public T add(Method_ method_) {
        getMethods().add(method_);
        return (T) this;
    }

    public T add(M classModifier_) {
        getModifiers().add(classModifier_);
        return (T) this;
    }

    public T set(Set<M> newSet) {
        getModifiers().clear();
        getModifiers().addAll(newSet);
        return (T) this;
    }

    public boolean is(M modifier) {
        return modifiers.contains(modifier);
    }

    public List<Interface_> getInterfaces() {
        return interfaces;
    }

    public List<Field_> getFields() {
        return fields;
    }

    public List<Method_> getMethods() {
        return methods;
    }

    public Package_ getPackage() {
        return pagage;
    }

    public void setPackage(Package_ pagage) {
        this.pagage = pagage;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public Set<M> getModifiers() {
        return modifiers;
    }
}
