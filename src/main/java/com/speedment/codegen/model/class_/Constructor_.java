package com.speedment.codegen.model.class_;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.Statement_;
import com.speedment.codegen.model.modifier.ConstructorModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Constructor_ implements CodeModel, Nameable, Modifiable<ConstructorModifier_> {

    private final Set<ConstructorModifier_> modifiers;
    private final List<Field_> parameters;
    private final List<Statement_> statements;
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

}
