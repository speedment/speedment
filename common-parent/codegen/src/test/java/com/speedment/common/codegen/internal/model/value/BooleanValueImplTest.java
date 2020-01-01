package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.value.BooleanValue;

final class BooleanValueImplTest extends AbstractValueTest<Boolean, BooleanValue> {

    public BooleanValueImplTest() {
        super(() -> new BooleanValueImpl(true),
                a -> a.setValue(false)
        );
    }

}