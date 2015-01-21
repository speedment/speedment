package com.speedment.codegen.model;

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
