package com.speedment.codegen.model.field;

import com.speedment.codegen.Adder;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.modifier.FieldModifier_;
import java.util.Set;

/**
 *
 * @author pemi
 * @param <T>
 */
public abstract class FieldAdder<T> extends Field_ implements Adder<T> {

    T parent;

    public FieldAdder(T parent) {
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
    public FieldAdder<T> public_() {
        super.public_();
        return this;
    }

    @Override
    public FieldAdder<T> protected_() {
        super.protected_();
        return this;
    }

    @Override
    public FieldAdder<T> private_() {
        super.private_();
        return this;
    }

    @Override
    public FieldAdder<T> static_() {
        super.static_();
        return this;
    }

    @Override
    public FieldAdder<T> final_() {
        super.final_();
        return this;
    }

    @Override
    public FieldAdder<T> transient_() {
        super.transient_();
        return this;
    }

    @Override
    public FieldAdder<T> volatile_() {
        super.volatile_();
        return this;
    }

    @Override
    public FieldAdder<T> set(Set<FieldModifier_> newSet) {
        super.set(newSet);
        return this;
    }

    @Override
    public FieldAdder<T> setName(CharSequence name) {
        super.setName(name);
        return this;
    }

    @Override
    public FieldAdder<T> add(Annotation_ annotation) {
        super.add(annotation);
        return this;
    }

    @Override
    public FieldAdder<T> add(FieldModifier_ firstModifier_m, FieldModifier_... restModifiers) {
        super.add(firstModifier_m, restModifiers);
        return this;
    }

}
