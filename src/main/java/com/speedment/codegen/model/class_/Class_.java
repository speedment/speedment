package com.speedment.codegen.model.class_;

import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.modifier.ClassModifier_;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Class_ extends ClassAndInterfaceBase<Class_, ClassModifier_> {

    private final List<Constructor_> constructors;
    private Type_ superClassType;
    private Class<?> superClass;

    public Class_() {
        super(ClassModifier_.class);
        constructors = new ArrayList<>();
    }

    public Class_(final String className) {
        this();
        setName(className);
    }

    public Class_(String className, Class<?> superClass) {
        this();
        setName(className);
        setSuperClass(superClass);
    }

    public Class_(String className, Type_ superClass) {
        this();
        setName(className);
        setSuperClassType(superClass);
    }

    public Class_ add(Constructor_ constructor) {
        getConstructors().add(constructor);
        return this;
    }

    public List<Constructor_> getConstructors() {
        return constructors;
    }

    @Override
    public Type getModelType() {
        return Type.CLASS;
    }

    public Class<?> getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class<?> superClass) {
        this.superClass = superClass;
        this.setSuperClassType(new Type_(superClass));
    }

    public Type_ getSuperClassType() {
        return superClassType;
    }

    public void setSuperClassType(Type_ superClassType) {
        this.superClassType = superClassType;
        this.superClass = superClassType.getTypeClass();
    }

}
