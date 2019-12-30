package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.AnnotationUsage;

import java.lang.reflect.Type;

final class AnnotationUsageImplTest extends AbstractTest<AnnotationUsage> {

    private static final Type TYPE = Integer.class;

    public AnnotationUsageImplTest() {
        super(() -> new AnnotationUsageImpl(TYPE),
                au -> au.set(Long.class),
                au -> au.set( 1),
                au -> au.put("a", 1)
        );
    }

}