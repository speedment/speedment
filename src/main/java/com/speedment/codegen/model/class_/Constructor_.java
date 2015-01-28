package com.speedment.codegen.model.class_;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.AbstractModifiableCodeModel;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.javadoc.JavadocAdder;
import com.speedment.codegen.model.javadoc.Javadoc_;
import com.speedment.codegen.model.javadoc.Javadockable;
import com.speedment.codegen.model.statement.Statement_;
import com.speedment.codegen.model.modifier.ConstructorModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.util.stream.StreamUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Constructor_ extends AbstractModifiableCodeModel<Constructor_, ConstructorModifier_> implements CodeModel, Nameable, Modifiable<ConstructorModifier_>, Javadockable {

    private final Set<ConstructorModifier_> modifiers;
    private final List<Field_> parameters;
    private final List<Statement_> statements;
    private Javadoc_ javadoc;
    private CharSequence name;

    public Constructor_() {
        this.modifiers = EnumSet.noneOf(ConstructorModifier_.class);
        this.parameters = new ArrayList<>();
        this.statements = new ArrayList<>();
    }

    public Constructor_ add(Field_ field_) {
        return add(field_, parameters::add);
    }

    public Constructor_ add(Statement_ statement) {
        return add(statement, statements::add);
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public Constructor_ setName(CharSequence name) {
        return set(name, n -> this.name = n);
    }

    public List<Field_> getParameters() {
        return parameters;
    }

    @Override
    public Type getModelType() {
        return Type.CONSTRUCTOR;
    }

    @Override
    public boolean is(ConstructorModifier_ modifier) {
        return getModifiers().contains(modifier);
    }

    @Override
    public Set<ConstructorModifier_> getModifiers() {
        return modifiers;
    }

    public Constructor_ private_() {
        return add(ConstructorModifier_.PRIVATE);
    }

    public Constructor_ protected_() {
        return add(ConstructorModifier_.PROTECTED);
    }

    public Constructor_ puplic_() {
        return add(ConstructorModifier_.PUBLIC);
    }

    @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.<CodeModel>of(modifiers, parameters, statements);
    }

    public JavadocAdder<Constructor_> javadocAdder() {
        return new JavadocAdder<>(this, this::setJavadoc);
    }

    @Override
    public Javadoc_ getJavadoc() {
        return javadoc;
    }

    @Override
    public Constructor_ setJavadoc(Javadoc_ javadoc) {
        return set(javadoc, j -> this.javadoc = j);
    }

}
