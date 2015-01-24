package com.speedment.codegen.model.dependency_;

import com.speedment.codegen.model.CodeModel;

/**
 *
 * @author Duncan
 */
public class Dependency_ implements CodeModel {
	private String source;
	private boolean isStatic;

	public String getSource() {
		return source;
	}

	public Dependency_ setSource(final String source) {
		this.source = source;
		return this;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public Dependency_ setStatic(final boolean isStatic) {
		this.isStatic = isStatic;
		return this;
	}
	
	@Override
	public Type getType() {
		return Type.DEPENDENCY;
	}
	
	public static Dependency_ of(Class<?> javaClass) {
		return new Dependency_().setSource(javaClass.getName());
	}
}
