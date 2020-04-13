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
package com.speedment.common.injector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InjectorExceptionTest {

    private static final String MSG = "Arne";

    @Test
    void create1() {
        assertMsgIsResonable(new InjectorException(MSG));
    }

    @Test
    void create2() {
        assertMsgIsResonable(new InjectorException(MSG, new RuntimeException()));
    }

    @Test
    void createEx() {
        assertMsgIsResonable(new InjectorException(new RuntimeException(MSG)));
    }

    private void assertMsgIsResonable(InjectorException e) {
        assertTrue(e.getMessage().contains(MSG));
    }

}