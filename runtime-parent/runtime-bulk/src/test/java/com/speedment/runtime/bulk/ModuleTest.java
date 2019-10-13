/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

package com.speedment.runtime.bulk;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Mislav Milicevic
 */
final class ModuleTest {

    @Test
    void shouldAccessNonInternal() throws ClassNotFoundException {
        try {
            tryAccess(Class.forName("com.speedment.runtime.core.exception.SpeedmentException"));
        } catch (IllegalAccessException e) {
            fail("Should be able to access non-internal class");
        }
    }

    @Test
    void shouldNotAccessInternal() throws ClassNotFoundException {
        try {
            tryAccess(Class.forName("com.speedment.runtime.core.internal.util.TextUtil"));
            fail("Shouldn't be able to access internal class");
        } catch (IllegalAccessException ignore) {
        }
    }

    private static <T> void tryAccess(Class<T> clazz) throws IllegalAccessException {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
