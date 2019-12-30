package com.speedment.common.codegen.provider;

import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;
import com.speedment.common.codegen.internal.DefaultDependencyManager;
import com.speedment.common.codegen.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

final class StandardGeneratorTest {

    private StandardGenerator INSTANCE = new StandardGenerator(new DefaultDependencyManager(), new StandardTransformFactory("A"));

    @Test
    void getDependencyMgr() {
        assertNotNull(INSTANCE.getDependencyMgr());
    }

    @Test
    void getRenderStack() {
        assertNotNull(INSTANCE.getRenderStack());
    }

    @Test
    void metaOn() {
        assertNotNull(INSTANCE.metaOn(Integer.class, String.class));
    }

    @Test
    void transform() {
        final TransformFactory factory = new StandardTransformFactory("A");
        final Transform<Integer, String> transform = (g, i) -> Optional.of(Integer.toString(i));
        factory.install(Integer.class, String.class, () -> transform);
        assertNotNull(INSTANCE.transform(transform, 1, factory));
    }
}