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

package com.speedment.runtime.core.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

final class SqlPredicateFragmentTest {

    @Test
    void instance() {
        assertNotNull(SqlPredicateFragment.of("SELECT * FROM table"));
        assertNotNull(SqlPredicateFragment.of("SELECT * FROM table WHERE id > ?", 1));
        assertNotNull(SqlPredicateFragment.of("SELECT * FROM table WHERE id > ? AND name = ?", Arrays.asList(1, "name")));
    }
}
