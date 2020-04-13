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
package com.speedment.runtime.core.provider;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.component.StatisticsReporterComponent;
import com.speedment.runtime.core.component.StatisticsReporterSchedulerComponent;
import com.speedment.runtime.core.internal.component.StatisticsReporterSchedulerComponentImpl;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateStatisticsReporterSchedulerComponent implements StatisticsReporterSchedulerComponent {

    private final StatisticsReporterSchedulerComponentImpl inner;

    public DelegateStatisticsReporterSchedulerComponent(@Config(name = "statistics.scheduler.enabled", value = "true") final boolean enabled) {
        this.inner = new StatisticsReporterSchedulerComponentImpl(enabled);
    }

    @ExecuteBefore(State.STARTED)
    public void start(StatisticsReporterComponent src) {
        inner.start(src);
    }

    @ExecuteBefore(State.STOPPED)
    public void stop(StatisticsReporterComponent src) {
        inner.stop(src);
    }

}
