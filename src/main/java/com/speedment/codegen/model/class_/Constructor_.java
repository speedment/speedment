package com.speedment.codegen.model.class_;

import com.speedment.codegen.Nameable;
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
public class Constructor_ implements CodeModel, Nameable, Modifiable<ConstructorModifier_>, Javadockable {

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

    @Override
    public Constructor_ add(final ConstructorModifier_ firstModifier_m, final ConstructorModifier_... restModifiers) {
        getModifiers().add(firstModifier_m);
        Stream.of(restModifiers).forEach(getModifiers()::add);
        return this;
    }

    public Constructor_ add(Field_ field_) {
        getParameters().add(field_);
        return this;
    }

    public Constructor_ add(Statement_ statement) {
        statements.add(statement);
        return this;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public Constructor_ setName(CharSequence name) {
        this.name = name;
        return this;
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

    @SuppressWarnings("unchecked")
    @Override
    public Constructor_ set(final Set<ConstructorModifier_> newSet) {
        getModifiers().clear();
        getModifiers().addAll(newSet);
        return this;
    }

    public Constructor_ private_() {
        add(ConstructorModifier_.PRIVATE);
        return this;
    }

    public Constructor_ protected_() {
        add(ConstructorModifier_.PROTECTED);
        return this;
    }

    public Constructor_ puplic_() {
        add(ConstructorModifier_.PUBLIC);
        return this;
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
        this.javadoc = javadoc;
        return this;
    }

}
