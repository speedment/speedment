package com.speedment.plugins.json;

import com.speedment.plugins.json.provider.DelegateJsonComponent;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

final class JsonBundleTest {

    @Test
    void injectables() {
        final Set<Class<?>> set = new JsonBundle().injectables().collect(toSet());
        assertTrue(set.contains(DelegateJsonComponent.class));
    }
}