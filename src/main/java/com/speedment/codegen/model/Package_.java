package com.speedment.codegen.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Package_ {

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
