package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.value.InvocationValue;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

final class InvocationValueImplTest extends AbstractValueTest<String, InvocationValue> {

    public InvocationValueImplTest() {
        super(InvocationValueImpl::new,
                a -> a.setValue("A"),
                a -> a.set(int.class),
                a -> a.add(Value.ofNumber(1))
        );
    }

    @Test
    void getValue() {
        assertNull(instance().getValue());
    }

    @Test
    void setValue() {
        final String value = "B";
        instance().setValue(value);
        assertEquals(value, instance().getValue());
    }

    @Test
    void getType() {
        assertNull(instance().getValue());
    }

    @Test
    void set() {
        final Type value = int.class;
        instance().set(value);
        assertEquals(value, instance().getType());
    }

    @Test
    void getValues() {
        assertTrue(instance().getValues().isEmpty());
    }
}