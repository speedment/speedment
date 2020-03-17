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

import static com.speedment.runtime.core.util.Statistics.Event.NODE_ALIVE;
import static com.speedment.runtime.core.util.Statistics.Event.NODE_STARTED;
import static com.speedment.runtime.core.util.Statistics.Event.NODE_STOPPED;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.StatisticsReporterComponent;
import com.speedment.runtime.core.util.Statistics;

/**
 * Default implementation of the {@link StatisticsReporterComponent} component.
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since  3.0.8
 */
public final class StatisticsReporterComponentImpl implements StatisticsReporterComponent {

    private static final Logger LOGGER = LoggerManager.getLogger(StatisticsReporterComponentImpl.class);

    private final InfoComponent info;
    private final ProjectComponent projects;

    @Inject
    public StatisticsReporterComponentImpl(InfoComponent info, ProjectComponent projects) {
        this.info = info;
        this.projects = projects;
    }

    @Override
    public void reportStarted() {
        debug("started");
        Statistics.report(info, projects, NODE_STARTED);
    }

    @Override
    public void reportStopped() {
        debug("stopped");
        Statistics.report(info, projects, NODE_STOPPED);
    }

    @Override
    public void alive() {
        debug("alive");
        Statistics.report(info, projects, NODE_ALIVE);
    }

    private void debug(String action) {
        LOGGER.debug("Report node " + action);
    }
}
