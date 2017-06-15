/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.component.StatisticsReporterComponent;
import com.speedment.runtime.core.component.StatisticsReporterSchedulerComponent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Per Minborg
 */
public class StatisticsReporterSchedulerComponentImpl implements StatisticsReporterSchedulerComponent {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        final Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }); // Make the threads daemon to allow speedment applications to exit via main method completion Fix #322 

    @ExecuteBefore(State.STARTED)
    public void start(StatisticsReporterComponent src) {
        scheduler.submit(src::reportStarted);
        scheduler.scheduleAtFixedRate(src::alive, 1, 1, TimeUnit.HOURS);
    }

    @ExecuteBefore(State.STOPPED)
    public void stop(StatisticsReporterComponent src) {
        scheduler.submit(src::reportStopped);
        try {
            scheduler.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {

        } finally {
            scheduler.shutdownNow();
        }
    }

}
