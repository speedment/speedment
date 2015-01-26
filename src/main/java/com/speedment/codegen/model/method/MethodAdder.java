package com.speedment.codegen.model.method;

import com.speedment.codegen.Adder;
import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.modifier.MethodModifier_;
import com.speedment.codegen.model.parameter.Parameter_;
import com.speedment.codegen.model.statement.Statement_;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
public class MethodAdder<T> extends Method_ implements Adder<T> {

    final T parent;
    final Consumer<MethodAdder<T>> updater;

    public MethodAdder(T parent, Consumer<MethodAdder<T>> updater) {
        super();
        this.parent = Objects.requireNonNull(parent);
        this.updater = Objects.requireNonNull(updater);
    }

    @Override
    public T add() {
        updater.accept(this);
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
