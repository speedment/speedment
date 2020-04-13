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

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.StatisticsReporterComponent;
import com.speedment.runtime.core.internal.component.StatisticsReporterComponentImpl;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateStatisticsReporterComponent implements StatisticsReporterComponent {

    private final StatisticsReporterComponent inner;

    @Inject
    public DelegateStatisticsReporterComponent(InfoComponent info, ProjectComponent projects) {
        inner = new StatisticsReporterComponentImpl(info, projects);
    }

    @Override
    public void reportStarted() {
        inner.reportStarted();
    }

    @Override
    public void reportStopped() {
        inner.reportStopped();
    }

    @Override
    public void alive() {
        inner.alive();
    }
}
