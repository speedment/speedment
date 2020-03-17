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
package com.speedment.runtime.compute.internal.predicate;

import static com.speedment.runtime.compute.ToBooleanNullable.of;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.expression.predicate.IsNull;
import org.junit.jupiter.api.Test;

import java.util.Objects;

final class IsNotNullImplTest {

    private final IsNotNullImpl<String, Boolean> instance = new IsNotNullImpl<>(of(Objects::isNull));

    @Test
    void negate() {
        assertTrue(instance.negate() instanceof IsNull);
    }

    @Test
    void expression() {
        assertNotNull(instance.expression());
    }
}
