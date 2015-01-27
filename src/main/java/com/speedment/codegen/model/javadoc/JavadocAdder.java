package com.speedment.codegen.model.javadoc;

import com.speedment.codegen.Adder;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
public class JavadocAdder<T> extends Javadoc_ implements Adder<T> {

    final T parent;
    final Consumer<JavadocAdder<T>> updater;

    public JavadocAdder(T parent, Consumer<JavadocAdder<T>> updater) {
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
    public JavadocAdder<T> add(CharSequence text) {
        super.add(text);
        return this;
    }

    @Override
    public JavadocAdder<T> add(Tag tag, CharSequence text) {
        super.add(tag, text);
        return this;
    }

    @Override
    public JavadocAdder<T> return_(CharSequence text) {
        return_(text);
        return this;
    }

    @Override
    public JavadocAdder<T> param_(CharSequence text) {
        super.param_(text);
        return this;
    }

    @Override
    public JavadocAdder<T> throws_(CharSequence text) {
        super.throws_(text);
        return this;
    }

    @Override
    public JavadocAdder<T> throws_(Class<? extends Exception> throwClass, CharSequence additionalText) {
        super.throws_(throwClass, additionalText);
        return this;
    }

}
