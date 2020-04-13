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

package com.speedment.runtime.core.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class ProgressMeasureTest {

    @Test
    void isDone() {
        final ProgressMeasure progressMeasure = ProgressMeasure.create();

        progressMeasure.setProgress(-1);
        assertFalse(progressMeasure.isDone());

        progressMeasure.setProgress(-0.5);
        assertFalse(progressMeasure.isDone());

        progressMeasure.setProgress(0);
        assertFalse(progressMeasure.isDone());

        progressMeasure.setProgress(0.5);
        assertFalse(progressMeasure.isDone());

        progressMeasure.setProgress(1);
        assertTrue(progressMeasure.isDone());
    }

    @Test
    void setAndGetCurrentAction() {
        final ProgressMeasure progressMeasure = ProgressMeasure.create();

        assertDoesNotThrow(() -> progressMeasure.setCurrentAction("action"));
        assertEquals("action", progressMeasure.getCurrentAction());
    }

    @Test
    void addListener() {
        final ProgressMeasure progressMeasure = ProgressMeasure.create();

        assertThrows(NullPointerException.class, () -> progressMeasure.addListener(null));
        assertDoesNotThrow(() -> progressMeasure.addListener(measure -> {}));
    }
}
