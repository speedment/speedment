package com.speedment.common.codegen.internal;

import com.speedment.common.codegen.*;
import com.speedment.common.codegen.provider.StandardTransformFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class MetaImplTest {

    private Meta<Integer, String> instance;
    private Transform<Integer, String> transform;
    private TransformFactory factory;
    private RenderStack renderStack;
    private RenderTree renderTree;

    @BeforeEach
    void setup() {
        transform = (g, i) -> Optional.of(Integer.toString(i));
        factory = new StandardTransformFactory("T");
        renderStack = new DefaultRenderStack();
        renderTree = RenderTree.builder().build();
        instance = Meta.builder(1, "1")
                .withTransform(transform)
                .withFactory(factory)
                .withRenderStack(renderStack)
                .withRenderTree(renderTree)
                .build();
    }

    @Test
    void getResult() {
        assertEquals("1", instance.getResult());
    }

    @Test
    void getTransform() {
        assertEquals(transform, instance.getTransform());
    }

    @Test
    void getFactory() {
        assertEquals(factory, instance.getFactory());
    }

    @Test
    void getModel() {
        assertEquals(1, instance.getModel());
    }

    @Test
    void getRenderStack() {
        assertEquals(renderStack, instance.getRenderStack());
    }

    @Test
    void getRenderTree() {
        assertEquals(renderTree, instance.getRenderTree());
    }

    @Test
    void testToString() {
        final String s = instance.toString();
        assertTrue(s.contains("model"));
        assertTrue(s.contains("result"));
        assertTrue(s.contains("1"));
    }
}