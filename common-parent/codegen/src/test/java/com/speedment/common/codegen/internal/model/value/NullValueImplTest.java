package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.value.NullValue;

final class NullValueImplTest extends AbstractValueTest<Number, NullValue> {

    public NullValueImplTest() {
        super(NullValueImpl::new);
    }

}