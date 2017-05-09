package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.value.AnonymousValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of the {@link AnonymousValue} interface.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public final class AnonymousValueImpl<T extends ClassOrInterface<T>>
implements AnonymousValue<T> {

    private final List<Value<?>> args;
    private final List<Generic> generics;
    private T classOrInterface;

    public AnonymousValueImpl() {
        this.args     = new ArrayList<>();
        this.generics = new ArrayList<>();
    }

    private AnonymousValueImpl(AnonymousValue<T> prototype) {
        this.args             = Copier.copy(prototype.getValues(), HasCopy::copy);
        this.generics         = Copier.copy(prototype.getGenerics());
        this.classOrInterface = Copier.copy(prototype.getValue());
    }

    @Override
    public List<Value<?>> getValues() {
        return args;
    }

    @Override
    public List<Generic> getGenerics() {
        return generics;
    }

    @Override
    public T getValue() {
        return classOrInterface;
    }

    @Override
    public AnonymousValue<T> setValue(T value) {
        classOrInterface = value;
        return this;
    }

    @Override
    public Value<T> copy() {
        return new AnonymousValueImpl<>(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnonymousValue)) return false;

        final AnonymousValue<?> that = (AnonymousValue<?>) o;
        if (!args.equals(that.getValues())) return false;
        else if (!getGenerics().equals(that.getGenerics())) return false;
        return classOrInterface != null
            ? classOrInterface.equals(that.getValue())
            : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = args.hashCode();
        result = 31 * result + getGenerics().hashCode();
        result = 31 * result + (classOrInterface != null ? classOrInterface.hashCode() : 0);
        return result;
    }
}
