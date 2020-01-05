package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.trait.HasCopy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class AbstractValueTest<V, T extends HasCopy<?>> {

    private final Function<T, ?>[] mutators;
    private final Supplier<T> constructor;

    private T instance;

    @SafeVarargs
    @SuppressWarnings("varargs")
    public AbstractValueTest(Supplier<T> constructor, Function<T, ?>... mutators) {
        this.constructor = requireNonNull(constructor);
        this.mutators = requireNonNull(mutators);
    }

    public T instance() {
        return instance;
    }

    @BeforeEach
    void setup() {
        instance = constructor.get();
    }

    @Test
    @SuppressWarnings("unchecked")
    void copy() {
        {
            final T copy = (T) instance.copy();
            assertEquals(instance, copy);
        }
        for (Function<T, ?> mutator : mutators) {
            final T copy = (T) instance.copy();
            assertEquals(instance, copy);
            mutator.apply(copy);
            assertNotEquals(instance, copy);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testHashCode() {
        assertNotEquals(0, instance.hashCode());
        for (Function<T, ?> mutator:mutators) {
            final T copy = (T) instance.copy();
            final T mutatedCopy = (T) mutator.apply(copy);
            assertNotEquals(mutatedCopy.hashCode(), instance.hashCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testEquals() {
        assertEquals(instance, instance);
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        for (Function<T, ?> mutator:mutators) {
            System.out.println(mutator);
            final T copy = (T) instance.copy();
            final T mutatedCopy = (T) mutator.apply(copy);
            assertNotEquals(mutatedCopy, instance);
            assertNotEquals(instance, mutatedCopy);
        }
    }
}