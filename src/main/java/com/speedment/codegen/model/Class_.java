package com.speedment.codegen.model;

import com.speedment.codegen.model.modifier.ClassModifier_;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemiClassModifier_
 */
public class Class_ extends ClassAndInterfaceBase<Class_, ClassModifier_> {

    private final List<Constructor_> constructors;
    private Class_ baseClass;

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

    public Class_ getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(Class_ parent) {
        this.baseClass = parent;
    }

}
