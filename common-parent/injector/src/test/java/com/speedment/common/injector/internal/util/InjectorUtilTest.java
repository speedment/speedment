package com.speedment.common.injector.internal.util;

import com.speedment.common.injector.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class InjectorUtilTest {

    private Injector injector;
    private Foo foo;
    private Bar bar;
    private List<Object> instances;

    public static final class Foo {}
    public static final class Bar {}

    @BeforeEach
    void setup() throws InstantiationException {
        foo = new Foo();
        bar = new Bar();
        instances = Arrays.asList(foo, bar);
        injector = Injector.builder().withComponent(Foo.class, () -> foo).withComponent(Bar.class, () -> bar).build();
    }

    @Test
    void findInNotRequired() {
        final Foo expected = InjectorUtil.findIn(Foo.class, injector, instances, false);
        assertEquals(foo, expected);
        assertNull(InjectorUtil.findIn(Integer.class, injector, instances, false));
    }

    @Test
    void findInRequired() {
        assertNotNull(InjectorUtil.findIn(Foo.class, injector, instances, true));
        assertThrows(IllegalArgumentException.class, () -> InjectorUtil.findIn(Integer.class, injector, instances, true));
    }

    @Test
    void findInInjector() {
        assertSame(injector, InjectorUtil.findIn(Injector.class, injector, instances, true));
    }

}