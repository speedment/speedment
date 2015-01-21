package com.speedment.codegen.model;

import com.speedment.codegen.model.modifier.ClassModifier_;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author pemi
 */
public class Class_ extends CodeModel {

    private final List<Field_> fields;
    private final List<Constructor_> constructors;
    private final List<Method_> methods;
    private Package_ pagage;
    private final Set<ClassModifier_> classModifiers;

    public Class_() {
        this.fields = new ArrayList<>();
        constructors = new ArrayList<>();
        methods = new ArrayList<>();
        classModifiers = EnumSet.noneOf(ClassModifier_.class);
    }

    public Class_ add(Field_ field) {
        getFields().add(field);
        return this;
    }

    public Class_ add(Constructor_ constructor) {
        getConstructors().add(constructor);
        return this;
    }

    public Class_ add(Method_ method_) {
        getMethods().add(method_);
        return this;
    }

    public Class_ add(ClassModifier_ classModifier_) {
        getClassModifiers().add(classModifier_);
        return this;
    }

    public Class_ set(Set<ClassModifier_> newSet) {
        getClassModifiers().clear();
        getClassModifiers().addAll(newSet);
        return this;
    }

    public List<Field_> getFields() {
        return fields;
    }

    public List<Constructor_> getConstructors() {
        return constructors;
    }

    public List<Method_> getMethods() {
        return methods;
    }

    @Override
    public Type getType() {
        return Type.CLASS;
    }

    public Package_ getPagage() {
        return pagage;
    }

    public void setPagage(Package_ pagage) {
        this.pagage = pagage;
    }

    public Set<ClassModifier_> getClassModifiers() {
        return classModifiers;
    }

}
