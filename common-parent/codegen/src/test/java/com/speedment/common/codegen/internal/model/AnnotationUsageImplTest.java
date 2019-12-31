package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Value;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AnnotationUsageImplTest extends AbstractTest<AnnotationUsage> {

    private static final Type TYPE = Integer.class;

    public AnnotationUsageImplTest() {
        super(() -> new AnnotationUsageImpl(TYPE),
                au -> au.set(Long.class),
                au -> au.set( 1),
                au -> au.put("a", 1)
        );
    }

    @Test
    void copyExtra() {
        instance().put("A", Value.ofNumber(1));
        final AnnotationUsage copy = instance().copy();
        assertEquals(instance(), copy);
    }

}