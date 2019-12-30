package com.speedment.common.codegen.model.value;

import com.speedment.common.codegen.internal.model.value.AnonymousValueImpl;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

final class AnonymousValueTest {

    @Test
    void add() {
        AnonymousValue anonymousValue = new AnonymousValueImpl();
        anonymousValue.add(String.class);
        assertEquals(singletonList(String.class), anonymousValue.getTypeParameters());
    }
}