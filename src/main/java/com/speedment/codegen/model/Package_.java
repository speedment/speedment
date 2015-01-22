package com.speedment.codegen.model;

/**
 *
 * @author pemi
 */
public class Package_ implements CodeModel {

    private String name_;
    private Package_ parentPackage;

    public Package_() {}

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
	
	public Package_ getPackage() {
		return parentPackage;
	}
	
	public void setPackage(Package_ parentPackage) {
		this.parentPackage = parentPackage;
	}
}
