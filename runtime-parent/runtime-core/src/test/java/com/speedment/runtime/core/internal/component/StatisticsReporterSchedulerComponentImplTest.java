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
package com.speedment.runtime.core.internal.component;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

final class StatisticsReporterSchedulerComponentImplTest {

    @Test
    void guardedCallStalled() {

        final AtomicBoolean called = new AtomicBoolean();
        final StatisticsReporterSchedulerComponentImpl instance = new StatisticsReporterSchedulerComponentImpl();

        final Thread t0 = new Thread(() -> { instance.guardedCall(() -> dly(1000)); });
        final Thread t1 = new Thread(() -> { called.set(true); });

        t0.start();
        dly(100);
        t1.start();

        assertFalse(called.get());
    }


    @Test
    void guardedCallClean() {

        final AtomicBoolean called = new AtomicBoolean();
        final StatisticsReporterSchedulerComponentImpl instance = new StatisticsReporterSchedulerComponentImpl();

        instance.guardedCall(() -> {});
        instance.guardedCall(() -> called.set(true));

        assertTrue(called.get());
    }



    private void dly(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {

        }
    }

}