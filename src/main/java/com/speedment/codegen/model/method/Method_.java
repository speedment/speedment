package com.speedment.codegen.model.method;

import com.speedment.codegen.model.Block_;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.Expression_;
import com.speedment.codegen.model.Field_;
import com.speedment.codegen.model.Statement_;
import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.modifier.MethodModifier_;
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
public class Method_ extends CodeModel implements Modifiable<MethodModifier_> {

    private final Set<MethodModifier_> modifiers;
    private Type_ type_;
    private CharSequence name_;
    private Expression_ expression_;
    private List<Field_> parameters; // Todo: Introduce parameter
    private final List<Statement_> statements; // Todo: Block instead of statements.

    public Method_(Type_ type_, CharSequence name_) {
        this.parameters = new ArrayList<>();
        this.statements = new ArrayList<>();
        this.type_ = type_;
        this.name_ = name_;
        this.modifiers = EnumSet.noneOf(MethodModifier_.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Method_ add(final MethodModifier_ firstClassModifier_m, final MethodModifier_... restClassModifiers) {
        getModifiers().add(firstClassModifier_m);
        Stream.of(restClassModifiers).forEach(getModifiers()::add);
        return (Method_) this;
    }

    public Method_ add(Field_ field_) {
        getParameters().add(field_);
        return this;
    }

    public Method_ add(Statement_ statement) {
        statements.add(statement);
        return this;
    }

    public Type_ getType_() {
        return type_;
    }

    public void setType_(Type_ type_) {
        this.type_ = type_;
    }

    public CharSequence getName_() {
        return name_;
    }

    public void setName_(CharSequence name_) {
        this.name_ = name_;
    }

    public Expression_ getExpression_() {
        return expression_;
    }

    public void setExpression_(Expression_ expression_) {
        this.expression_ = expression_;
    }

    public List<Field_> getParameters() {
        return parameters;
    }

    public void setParameters(List<Field_> parameters) {
        this.parameters = parameters;
    }

    public void setBlock_(Block_ block_) {
        throw new UnsupportedOperationException();
        // TODO: Implement Method_.setBlock_
    }

    public Block_ getBlock_() {
        throw new UnsupportedOperationException();
        // TODO: Implement Method_.getBlock_
    }

    @Override
    public Type getType() {
        return Type.METHOD;
    }

    @Override
    public boolean is(MethodModifier_ modifier) {
        return modifiers.contains(modifier);
    }

    @Override
    public Set<MethodModifier_> getModifiers() {
        return modifiers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Method_ set(final Set<MethodModifier_> newSet) {
        getModifiers().clear();
        getModifiers().addAll(newSet);
        return this;
    }

}
