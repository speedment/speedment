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

package com.speedment.runtime.core.internal.util.holder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class HolderDoubleTest {

    @Test
    void get() {
        final HolderDouble doubleHolder = new HolderDouble(1);

        assertEquals(1, doubleHolder.get());
    }

    @Test
    void set() {
        final HolderDouble doubleHolder = new HolderDouble();
        doubleHolder.set(1);

        assertEquals(1, doubleHolder.get());
    }
}
