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
package com.speedment.runtime.core.internal.component;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class StatisticsReporterSchedulerComponentImplTest {

    @Test
    void guardedCallStalled() {

        final AtomicBoolean called = new AtomicBoolean();
        final StatisticsReporterSchedulerComponentImpl instance = new StatisticsReporterSchedulerComponentImpl(true);

        final Thread t0 = new Thread(() -> { instance.guardedCall(() -> dly(2000)); });    // Hog the thread
        final Thread t1 = new Thread(() -> { instance.guardedCall(() -> called.set(true)); }); // This should be ignored since there is a pending Runnable

        t0.start();
        dly(1000); // Make "sure" the first thread has started
        t1.start();    // Start the second thread (who's run() shall be ignored)

        assertFalse(called.get());
    }


    @Test
    void guardedCallClean() {

        final AtomicBoolean called = new AtomicBoolean();
        final StatisticsReporterSchedulerComponentImpl instance = new StatisticsReporterSchedulerComponentImpl(true);

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