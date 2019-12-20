package com.speedment.common.injector;

import com.speedment.common.injector.internal.Bazz;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

final class InjectorProxyTest {

    private final class Foo {
        private final class Bar {}
    }

    @Test
    void samePackageOrBelow() {
        assertPredicateCorrect(InjectorProxy.samePackageOrBelow(InjectorProxyTest.class), false);
    }

    @Test
    void testSamePackageOrBelowTrue() {
        assertPredicateCorrect(InjectorProxy.samePackageOrBelow(InjectorProxyTest.class, true), true);
    }

    @Test
    void testSamePackageOrBelowFalse() {
        assertPredicateCorrect(InjectorProxy.samePackageOrBelow(InjectorProxyTest.class, false), false);
    }

    @Test
    void testSamePackageOrBelowStringTrue() {
        assertPredicateCorrect(InjectorProxy.samePackageOrBelow(InjectorProxyTest.class.getPackage().getName(), true), true);
    }

    @Test
    void testSamePackageOrBelowStringFalse() {
        assertPredicateCorrect(InjectorProxy.samePackageOrBelow(InjectorProxyTest.class.getPackage().getName(), false), false);
    }

    void assertPredicateCorrect(final Predicate<? super Class<?>> predicate, boolean excludeInternal) {
        assertTrue(predicate.test(InjectorProxyTest.class));
        assertFalse(predicate.test(String.class));
        assertThrows(NullPointerException.class, () -> predicate.test(null));
        if (excludeInternal) {
            assertFalse(predicate.test(Bazz.class));
        } else {
            assertTrue(predicate.test(Bazz.class));
        }
    }
}