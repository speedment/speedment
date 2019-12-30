package com.speedment.common.codegen.model.modifier;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.trait.HasModifiers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class KeywordTest {

    private Field field;

    @BeforeEach
    void setup() {
        field = Field.of("x", int.class);
    }

    @Test
    void public_() {
        assertOnOff(Field::public_, Field::notPublic, Modifier.PUBLIC);
    }

    @Test
    void protected_() {
        assertOnOff(Field::protected_, Field::notProtected, Modifier.PROTECTED);
    }

    @Test
    void private_() {
        assertOnOff(Field::private_, Field::notPrivate, Modifier.PRIVATE);
    }

    @Test
    void static_() {
        assertOnOff(Field::static_, Field::notStatic, Modifier.STATIC);
    }

    @Test
    void final_() {
        assertOnOff(Field::final_, Field::notFinal, Modifier.FINAL);
    }

    @Test
    void abstract_() {
        assertOnOff(Class.of("A"), Class::abstract_, Class::notAbstract, Modifier.ABSTRACT);
    }

    @Test
    void strictFp() {
        assertOnOff(Class.of("A"), Class::strictfp_, Class::notStrictfp, Modifier.STRICTFP);
    }

    @Test
    void synchronized_() {
        assertOnOff(Field::synchronized_, Field::notSynchronized, Modifier.SYNCHRONIZED);
    }

    @Test
    void transient_() {
        assertOnOff(Field::transient_, Field::notTransient, Modifier.TRANSIENT);
    }

    @Test
    void volatile_() {
        assertOnOff(Field::volatile_, Field::notVolatile, Modifier.VOLATILE);
    }

    @Test
    void native_() {
        assertOnOff(Method.of("A", int.class), Method::native_, Method::notNative, Modifier.NATIVE);
    }

    @Test
    void default_() {
        assertOnOff(Method.of("A", int.class), Method::default_, Method::notDefault, Modifier.DEFAULT);
    }

    private  void assertOnOff(UnaryOperator<Field> on, UnaryOperator<Field> off, Modifier modifier) {
        assertOnOff(field, on, off, modifier);
    }

    private <T extends HasModifiers<T>> void assertOnOff(T t, UnaryOperator<T> on, UnaryOperator<T> off, Modifier modifier) {
        assertEquals(emptySet(), t.getModifiers());
        on.apply(t);
        assertEquals(singleton(modifier), t.getModifiers());
        off.apply(t);
        assertEquals(emptySet(), t.getModifiers());
    }

}