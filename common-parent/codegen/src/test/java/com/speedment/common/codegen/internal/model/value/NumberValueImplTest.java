package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.value.NumberValue;

final class NumberValueImplTest extends AbstractValueTest<Number, NumberValue> {

    private final static Number VALUE = 1;

    public NumberValueImplTest() {
        super(() -> new NumberValueImpl(VALUE),
                a -> a.setValue(2)
        );
    }

}