package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.value.EnumValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class EnumValueImplTest extends AbstractValueTest<Enum<?>, EnumValue> {

    private enum Foo{BAR, BAZ}
    private enum Foo2{BAR2, BAZ2}

    public EnumValueImplTest() {
        super(() -> new EnumValueImpl(Foo.class, "BAR"),
                a -> a.setValue("BAZ")
        );
    }

    @Test
    void set() {
        instance().set(Foo2.class);
        assertEquals(Foo2.class, instance().getType());
    }

    @Test
    void getType() {
        assertEquals(Foo.class, instance().getType());
    }
}