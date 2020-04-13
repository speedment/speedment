/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
        defaultLevel = InjectorBuilderImpl.INTERNAL_LOGGER.getLevel();
        InjectorBuilderImpl.INTERNAL_LOGGER.setLevel(Level.DEBUG);
    }

    @AfterEach
    void afterEach() {
        InjectorBuilderImpl.INTERNAL_LOGGER.setLevel(defaultLevel);
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

        injector.stop();
    }


    @InjectKey(Foo.class)
    public interface Foo {}

    public static final class Bar implements Foo {}

    public static final class Bazz implements Foo {}

    public static final class Buzz {
        private final Foo foo;

        public Buzz(Foo foo) {
            this.foo = requireNonNull(foo);
        }

        public Foo foo() { return foo; }

    }

}
