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
package com.speedment.common.codegenxml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

final class TagElementTest {

    @Test
    void toStringTest() {
        final TagElement tagElement = TagElement.of("tag");

        assertNotNull(tagElement.toString());
    }

    @Test
    void setName() {
        final TagElement tagElement = TagElement.of("tag");
        tagElement.setName("new_tag");

        assertEquals("new_tag", tagElement.getName());
    }

}
