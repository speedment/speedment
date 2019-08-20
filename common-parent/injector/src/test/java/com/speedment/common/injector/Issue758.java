package com.speedment.common.injector;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.internal.InjectorBuilderImpl;
import com.speedment.common.logger.Level;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class Issue758 {

    private Level defaultLevel;

    @BeforeEach
    void beforeEach() {
        defaultLevel = InjectorBuilderImpl.LOGGER_INSTANCE.getLevel();
        InjectorBuilderImpl.LOGGER_INSTANCE.setLevel(Level.DEBUG);
    }

    @AfterEach
    void afterEach() {
        InjectorBuilderImpl.LOGGER_INSTANCE.setLevel(defaultLevel);
    }

    @Test
    void constructorInject() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(Bar.class)
            .withComponent(Buzz.class)
            .withComponent(Bazz.class)
            .build();

        final Buzz buzz = injector.getOrThrow(Buzz.class);

        assertEquals(Bazz.class, buzz.foo.getClass());
    }


    @InjectKey(Foo.class)
    public interface Foo {};

    public static final class Bar implements Foo {};
    public static final class Bazz implements Foo {};

    public static final class Buzz {
        private final Foo foo;

        public Buzz(Foo foo) {
            this.foo = requireNonNull(foo);
        }

        public Foo foo() { return foo; }

    };

}
