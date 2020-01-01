package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.value.TextValue;

final class TextValueImplTest extends AbstractValueTest<String, TextValue> {

    private final static String VALUE = "A";

    public TextValueImplTest() {
        super(() -> new TextValueImpl(VALUE),
                a -> a.setValue("C")
        );
    }

}