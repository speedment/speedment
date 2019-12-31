package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class ValueImplTest extends AbstractTest<Value<String>> {

    private final static String VALUE = "A";

    public ValueImplTest() {
        super(() -> new TestValue(VALUE),
                a -> a.setValue("C")
        );
    }

    @Test
    void setValue() {
        final String value = "V";
        instance().setValue(value);
        assertEquals(value, instance().getValue());
    }

    @Test
    void getValue() {
        assertEquals(VALUE, instance().getValue());
    }

    private static final class TestValue extends ValueImpl<String> {

        public TestValue(String val) {
            super(val);
        }

        @Override
        public ValueImpl<String> copy() {
            return new TestValue(getValue());
        }
    }

}