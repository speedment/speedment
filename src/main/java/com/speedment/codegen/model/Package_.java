package com.speedment.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Package_ extends CodeModel {

    private String name_;
    private final List<Class_> classes;

    public Package_() {
        this.classes = new ArrayList<>();
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

	@Override
	public Type getType() {
		return Type.PACKAGE;
	}
}
