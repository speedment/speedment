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
	
	// TODO: Parent class and implemented interfaces.

	private final List<Interface_> interfaces;
    private final List<Field_> fields;
    private final List<Constructor_> constructors;
    private final List<Method_> methods;
    private Package_ pagage;
	private CharSequence name;
	private Class_ parent;
    private final Set<ClassModifier_> classModifiers;

    public Class_() {
        fields			= new ArrayList<>();
        constructors	= new ArrayList<>();
        methods			= new ArrayList<>();
		interfaces		= new ArrayList<>();
        classModifiers	= EnumSet.noneOf(ClassModifier_.class);
    }
	
	public Class_ add(Interface_ interf) {
        getInterfaces().add(interf);
        return this;
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

    public boolean is(ClassModifier_ modifier) {
        return classModifiers.contains(modifier);
    }
	
	public List<Interface_> getInterfaces() {
        return interfaces;
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

	public Class_ getParent() {
		return parent;
	}

	public void setParent(Class_ parent) {
		this.parent = parent;
	}

    public Set<ClassModifier_> getClassModifiers() {
        return classModifiers;
    }
}