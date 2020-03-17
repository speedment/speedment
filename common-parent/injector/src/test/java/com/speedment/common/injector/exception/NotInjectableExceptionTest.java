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

final class NotInjectableExceptionTest {

    private static final String MSG = "Arne";

    @Test
    void create1() {
        final NotInjectableException ex = new NotInjectableException(Integer.class);
        assertTrue(ex.getMessage().contains(Integer.class.getSimpleName()));
        assertMsgReasonable(ex);
    }

    @Test
    void create2() {
        final NotInjectableException ex = new NotInjectableException(Integer.class, new RuntimeException());
        assertTrue(ex.getMessage().contains(Integer.class.getSimpleName()));
        assertMsgReasonable(ex);
    }

    void assertMsgReasonable(NotInjectableException ex) {
        assertTrue(ex.getMessage().contains("injected"));
    }

}