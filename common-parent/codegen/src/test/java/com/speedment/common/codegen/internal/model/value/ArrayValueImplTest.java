package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.value.ArrayValue;

import java.util.Arrays;
import java.util.List;

final class ArrayValueImplTest extends AbstractValueTest<List<Value<?>>, ArrayValue> {

    private final static List<Value<?>> VALUES = Arrays.asList(Value.ofNumber(1), Value.ofNumber(2));
    private final static List<Value<?>> OTHER_VALUES = Arrays.asList(Value.ofNumber(3), Value.ofNumber(4));

    public ArrayValueImplTest() {
        super(() -> new ArrayValueImpl(VALUES),
                a -> a.setValue(OTHER_VALUES)
        );
    }
}