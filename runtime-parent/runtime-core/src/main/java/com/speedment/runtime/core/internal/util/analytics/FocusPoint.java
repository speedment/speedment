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
package com.speedment.runtime.core.internal.util.analytics;

import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.internal.util.EmailUtil;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

public enum FocusPoint {

    GUI_STARTED("GuiStarted"),
    GUI_PROJECT_LOADED("ProjectLoaded"),
    APP_STARTED("AppStarted"),
    APP_ALIVE("AppAlive"),
    APP_STOPPED("AppStopped"),
    GENERATE("Generate");

    private static final String SEPARATOR = "/";

    private final String eventName;

    FocusPoint(String name) {
        this.eventName = name;
    }

    public String getEventName() {
        return eventName;
    }

    public String getContentURI(InfoComponent infoComponent) {
        return Stream.of(infoComponent.getTitle(),
            infoComponent.getImplementationVersion(),
            eventName,
            EmailUtil.getEmail()
        ).collect(joining(SEPARATOR));
    }
}
