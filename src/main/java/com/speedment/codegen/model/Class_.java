package com.speedment.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Class_ {

    private final List<Field_> fields;
    private final List<Constructor_> constructors;
    private final List<Method_> methods;
    private Package_ pagage;

    public Class_() {
        this.fields = new ArrayList<>();
        constructors = new ArrayList<>();
        methods = new ArrayList<>();
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

    public List<Field_> getFields() {
        return fields;
    }

    public List<Constructor_> getConstructors() {
        return constructors;
    }

    public List<Method_> getMethods() {
        return methods;
    }

    public Package_ getPagage() {
        return pagage;
    }

    public void setPagage(Package_ pagage) {
        this.pagage = pagage;
    }

}
