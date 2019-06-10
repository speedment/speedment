/**
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

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.component.StatisticsReporterComponent;
import com.speedment.runtime.core.component.StatisticsReporterSchedulerComponent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Per Minborg
 */
public class StatisticsReporterSchedulerComponentImpl implements StatisticsReporterSchedulerComponent {

    private final AtomicBoolean outstanding;

    @Config(name = "statistics.scheduler.enabled", value = "true")
    private boolean enabled;

    private ScheduledExecutorService scheduler;

    public StatisticsReporterSchedulerComponentImpl() {
        outstanding = new AtomicBoolean();
    }

    @ExecuteBefore(State.INITIALIZED)
    public void init() {
        if (enabled) {
            scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                final Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }); // Make the threads daemon to allow speedment applications to exit via main method completion Fix #322
        }
    }

    @ExecuteBefore(State.STARTED)
    public void start(StatisticsReporterComponent src) {
        if (enabled) {
            scheduler.submit(src::reportStarted);
            scheduler.scheduleAtFixedRate(() -> guardedCall(src::alive), 1, 1, TimeUnit.HOURS);
        }
    }

    @ExecuteBefore(State.STOPPED)
    public void stop(StatisticsReporterComponent src) {
        if (enabled) {
            scheduler.submit(src::reportStopped);
            try {
                scheduler.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException ie) {
                System.out.println("Unable to terminate " + StatisticsReporterSchedulerComponentImpl.class.getSimpleName());
                Thread.currentThread().interrupt();
            } finally {
                scheduler.shutdownNow();
            }
        }
    }

    /**
     * Method to guard from multiple invocations. Used to fix #708
     * @param r runnable
     */
    void guardedCall(Runnable r) {
        if (outstanding.compareAndSet(false,true)) {
            try {
                r.run();
            } finally {
                outstanding.set(false);
            }

        }
    }

}
