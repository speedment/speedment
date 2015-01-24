package com.speedment.codegen.model.package_;

import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.CodeModel.Type;
import com.speedment.codegen.model.class_.Class_;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Package_ implements CodeModel, Packagable {

    // TODO: An idea might be to let classes be part of a package rather than having
    // a package contain a number of classes. Since the primary purpose of the code
    // generator is to have each class render to a particular java file, iterating
    // over each class in a package is secondary.
    private String name_;
    private final List<Class_> classes;
    private Package_ package_;

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
    public Type getModelType() {
        return Type.PACKAGE;
    }

    public Package_ add(Class_ class_) {
        classes.add(class_);
        return this;
    }

    @Override
    public Package_ getPackage() {
        return package_;
    }

    @Override
    public Package_ setPackage(Package_ package_) {
        this.package_ = package_;
        return this;
    }

}
