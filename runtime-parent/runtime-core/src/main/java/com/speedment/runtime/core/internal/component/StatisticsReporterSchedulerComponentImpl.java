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

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
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
public final class StatisticsReporterSchedulerComponentImpl implements StatisticsReporterSchedulerComponent {

    private static final Logger LOGGER = LoggerManager.getLogger(StatisticsReporterSchedulerComponentImpl.class);

    private final AtomicBoolean outstanding;
    private final boolean enabled;
    private final ScheduledExecutorService scheduler;

    public StatisticsReporterSchedulerComponentImpl(@Config(name = "statistics.scheduler.enabled", value = "true") final boolean enabled) {
        this.enabled = enabled;
        outstanding = new AtomicBoolean();
        if (enabled) {
            scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                final Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }); // Make the threads daemon to allow speedment applications to exit via main method completion Fix #322
        } else {
            scheduler = null;
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
                LOGGER.error("Unable to terminate " + StatisticsReporterSchedulerComponentImpl.class.getSimpleName());
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
