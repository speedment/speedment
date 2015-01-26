/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.codegen.model.method;

import com.speedment.codegen.Adder;
import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.modifier.MethodModifier_;
import com.speedment.codegen.model.parameter.Parameter_;
import com.speedment.codegen.model.statement.Statement_;
import java.util.Set;

/**
 *
 * @author pemi
 */
public abstract class MethodAdder<T> extends Method_ implements Adder<T> {

    T parent;

    public MethodAdder(T parent) {
        super(null, null);
        this.parent = parent;
    }

    protected abstract void addToParent(T parent);

    @Override
    public T add() {
        addToParent(parent);
        return parent;
    }

    @Override
    public MethodAdder<T> public_() {
        super.public_();
        return this;
    }

    @Override
    public MethodAdder<T> set(Set<MethodModifier_> newSet) {
        super.set(newSet);
        return this;
    }

    @Override
    public MethodAdder<T> setName(CharSequence name) {
        super.setName(name);
        return this;
    }

    @Override
    public MethodAdder<T> setType(Type_ type_) {
        super.setType(type_);
        return this;
    }

    @Override
    public MethodAdder<T> add(Annotation_ annotation) {
        super.add(annotation);
        return this;
    }

    @Override
    public MethodAdder<T> add(Parameter_ field_) {
        super.add(field_);
        return this;
    }

    @Override
    public MethodAdder<T> add(Statement_ statement) {
        super.add(statement);
        return this;
    }

    @Override
    public MethodAdder<T> add(MethodModifier_ firstClassModifier_m, MethodModifier_... restClassModifiers) {
        super.add(firstClassModifier_m, restClassModifiers);
        return this;
    }

}
