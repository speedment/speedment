package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Field;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

final class HasCallTest {

    @Test
    void call() {
        final AtomicBoolean called = new AtomicBoolean();
        final HasCall<Field> field = Field.of("x", int.class);
        field.call(() -> called.set(true));
        assertTrue(called.get());
    }

}