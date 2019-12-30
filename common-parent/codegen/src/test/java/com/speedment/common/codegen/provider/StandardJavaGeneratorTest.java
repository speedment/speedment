package com.speedment.common.codegen.provider;

import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;
import com.speedment.common.codegen.model.Field;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class StandardJavaGeneratorTest {

    private StandardJavaGenerator INSTANCE = new StandardJavaGenerator();

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
        assertNotNull(INSTANCE.metaOn(Field.of("A", int.class), String.class));
    }

    @Test
    void transform() {
        final TransformFactory factory = new StandardTransformFactory("A");
        final Transform<Integer, String> transform = (g, i) -> Optional.of(Integer.toString(i));
        factory.install(Integer.class, String.class, () -> transform);
        assertNotNull(INSTANCE.transform(transform, 1, factory));
    }
}