package com.speedment.codegen.model.generic;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.AbstractCodeModel;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.type.ScalarType_;
import com.speedment.codegen.model.class_.Interface_;
import com.speedment.util.stream.StreamUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Generic_ extends AbstractCodeModel<Generic_> implements CodeModel, Nameable, Genericable {

    private CharSequence name;
    private final List<Generic_> generics;
    private ScalarType_ extendsType;
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
        return set(name, n -> this.name = n);

    }

    @Override
    public Genericable add(Generic_ generic) {
        return add(generic, getGenerics()::add);
    }

    @Override
    public List<Generic_> getGenerics() {
        return generics;
    }

    @Override
    public boolean hasGeneric(Generic_ generic) {
        return generics.contains(generic);
    }

    public ScalarType_ getExtendsType() {
        return extendsType;
    }

    public List<Interface_> getInterfaces() {
        return interfaces;
    }

    public Generic_ setExtendsType(ScalarType_ extendsType) {
        return set(extendsType, e -> this.extendsType = e);
    }

    @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.<CodeModel>of(generics, interfaces);
    }
}
