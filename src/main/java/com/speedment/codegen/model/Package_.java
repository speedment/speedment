package com.speedment.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Package_ extends CodeModel {
	
	// TODO: An idea might be to let classes be part of a package rather than having
	// a package contain a number of classes. Since the primary purpose of the code
	// generator is to have each class render to a particular java file, iterating
	// over each class in a package is secondary.

    private String name_;
    private final List<Class_> classes; 
    private final List<Package_> packages;

    public Package_() {
        this.classes = new ArrayList<>();
        this.packages = new ArrayList<>();
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
    Package_ add(Class_ class_) {
        classes.add(class_);
        return this;
    }

    Package_ add(Package_ package_) {
        package_.add(package_);
        return this;
    }

    public List<Package_> getPackages() {
        return packages;
    }

}
