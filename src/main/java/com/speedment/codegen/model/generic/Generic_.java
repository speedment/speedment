package com.speedment.codegen.model.generic;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.class_.Class_;
import com.speedment.codegen.model.class_.Interface_;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Generic_ implements CodeModel, Nameable, Genericable {

    private CharSequence name;
    private final List<Generic_> generics;
    private Class_ extendsClass;
    private List<Interface_> interfaces;
 /// Todo; Add "extends" and "super" stuff

    public Generic_() {
        this.generics = new ArrayList<>();
    }

    @Override
    public Type getModelType() {
        return Type.GENERIC_PARAMETER;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public Generic_ setName(CharSequence name) {
        this.name = name;
        return this;
    }

    @Override
    public Genericable add(Generic_ generic) {
        generics.add(generic);
        return this;
    }

    @Override
    public List<Generic_> getGenerics() {
        return generics;
    }

    @Override
    public boolean hasGeneric(Generic_ generic) {
        return generics.contains(generic);
    }

    public Class_ getExtendsClass() {
        return extendsClass;
    }

    public List<Interface_> getInterfaces() {
        return interfaces;
    }

    public Generic_ setExtendsClass(Class_ extendsClass) {
        this.extendsClass = extendsClass;
        return this;
    }

}
