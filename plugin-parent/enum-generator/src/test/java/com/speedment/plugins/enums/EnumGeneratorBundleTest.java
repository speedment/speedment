package com.speedment.plugins.enums;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

final class EnumGeneratorBundleTest {

    @Test
    void injectables() {
        final Set<Class<?>> classes = new EnumGeneratorBundle().injectables().collect(toSet());
        assertTrue(classes.contains(EnumGeneratorComponent.class));
    }
}