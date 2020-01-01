package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.value.ReferenceValue;

final class ReferenceValueImplTest extends AbstractValueTest<String, ReferenceValue> {

    private final static String VALUE = "A";

    public ReferenceValueImplTest() {
        super(() -> new ReferenceValueImpl(VALUE),
                a -> a.setValue("C")
        );
    }

}