package com.speedment.common.injector;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

final class InjectBundleTest {

    @Test
    void empty() {
        assertEquals(0, InjectBundle.empty().injectables().count());
    }

    @Test
    void of() {
        final Class<?>[] classes = {String.class, Integer.class, Long.class};
        final List<Class<?>> expected = Arrays.asList(classes);
        final InjectBundle bundle = InjectBundle.of(classes);
        assertEquals(expected, bundle.injectables().collect(toList()));
    }

    @Test
    void withBundle() {
        final InjectBundle first = InjectBundle.of(String.class);
        final InjectBundle second = InjectBundle.of(Integer.class);
        final InjectBundle composite = first.withBundle(second);
        assertEquals(Arrays.asList(String.class, Integer.class), composite.injectables().collect(toList()));
    }

    @Test
    void withComponent() {
        final InjectBundle composite = InjectBundle.of(String.class).withComponent(Integer.class);
        assertEquals(Arrays.asList(String.class, Integer.class), composite.injectables().collect(toList()));
    }
}