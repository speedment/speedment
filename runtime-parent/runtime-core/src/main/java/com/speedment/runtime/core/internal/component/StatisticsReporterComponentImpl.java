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
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.StatisticsReporterComponent;
import com.speedment.runtime.core.internal.util.Statistics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Per Minborg
 */
public class StatisticsReporterComponentImpl implements StatisticsReporterComponent {

    private static final Logger LOGGER = LoggerManager.getLogger(StatisticsReporterComponentImpl.class);

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private @Inject
    InfoComponent infoComponent;

    @ExecuteBefore(State.STARTED)
    public void start() {
        debug("started");
        Statistics.onNodeStarted(infoComponent);
        scheduler.scheduleAtFixedRate(this::alive, 1, 1, TimeUnit.HOURS);
    }

    @ExecuteBefore(State.STOPPED)
    public void stop() {
        debug("stopped");
        Statistics.onNodeStopped(infoComponent);
        try {
            scheduler.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {

        } finally {
            scheduler.shutdownNow();            
        }
    }

    private void alive() {
        debug("alive");
        Statistics.onNodeAlive(infoComponent);
    }

    private void debug(String action) {
        LOGGER.debug("Report node " + action);
    }

}
