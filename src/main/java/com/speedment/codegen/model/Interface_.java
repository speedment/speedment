package com.speedment.codegen.model;

import com.speedment.codegen.model.modifier.InterfaceModifier_;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Duncan
 */
public class Interface_ extends CodeModel {
	private final Set<InterfaceModifier_> interfaceModifiers;
	private final List<Interface_> parents;
	private final List<Field_> constants;
	private final List<Method_> methods;
	private CharSequence name;

	public Interface_() {
		this.interfaceModifiers = EnumSet.noneOf(InterfaceModifier_.class);
		this.parents	= new ArrayList<>();
		this.constants	= new ArrayList<>();
		this.methods	= new ArrayList<>();
	}
	
	public Interface_ add(Interface_ parent) {
		getParents().add(parent);
		return this;
	}
	
	public Interface_ add(Field_ constant) {
		getConstants().add(constant);
		return this;
	}
	
	public Interface_ add(Method_ method) {
		getMethods().add(method);
		return this;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}
	
	public boolean is(InterfaceModifier_ modifier) {
		return interfaceModifiers.contains(modifier);
	}

	public Set<InterfaceModifier_> getInterfaceModifiers() {
		return interfaceModifiers;
	}

	public List<Interface_> getParents() {
		return parents;
	}

	public List<Field_> getConstants() {
		return constants;
	}

	public List<Method_> getMethods() {
		return methods;
	}

	public CharSequence getName() {
		return name;
	}

	@Override
	public Type getType() {
		return Type.INTERFACE;
	}
}
