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

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * An InjectBundle can be used to represent a collection of classes to be injected.
 *
 * @author Per Minborg
 */
@FunctionalInterface
public interface InjectBundle {

    /**
     * Returns a Stream of classes that are to be provided to an Injector.
     *
     * @return a Stream of classes that are to be provided to an Injector
     */
    Stream<Class<?>> injectables();

    /**
     * Returns an InjectBundle that is empty.
     *
     * @return an InjectBundle that is empty.
     */
    static InjectBundle empty() {
        return Stream::empty;
    }

    /**
     * Creates and returns a new InjectBundle that contains the given classes.
     *
     * @return a new InjectBundle that contains the given classes.
     */
    static InjectBundle of(Class<?>... classes) {
        requireNonNull(classes);
        return () -> Stream.of(classes);
    }

    /**
     * Creates and returns a new InjectBundle that contains the classes
     * in this InjectBundle plus the classes in the given {@code next} InjectBundle.
     *
     * @return a new InjectBundle that contains the classes
     *         in this InjectBundle plus the classes in the given {@code next} InjectBundle
     * @throws NullPointerException if the provided {@code next} is {@code null}
     */
    default InjectBundle withBundle(InjectBundle next) {
        requireNonNull(next);
        return () -> Stream.concat(injectables(), next.injectables());
    }

    /**
     * Creates and returns a new InjectBundle that contains the classes
     * in this InjectBundle plus the class in the given {@code nextClass}.
     *
     * @return a new InjectBundle that contains the classes
     *         in this InjectBundle plus the class in the given {@code nextClass}
     * @throws NullPointerException if the provided {@code nextClass} is {@code null}
     */
    default InjectBundle withComponent(Class<?> nextClass) {
        requireNonNull(nextClass);
        return withBundle(of(nextClass));
    }
}
