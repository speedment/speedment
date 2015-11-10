/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util.analytics;

import com.speedment.SpeedmentVersion;
import com.speedment.internal.util.Settings;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

public enum FocusPoint {
    
    GUI_STARTED        ("GuiStarted"),
    GUI_PROJECT_LOADED ("ProjectLoaded"),
    APP_STARTED        ("AppStarted"),
    GENERATE           ("Generate");

    private static final String 
        ENCODING  = "UTF-8",
        SEPARATOR = "/";
    
    private final String eventName;
    
    private FocusPoint(String name) {
        this.eventName = name;
    }
    
    public String getEventName() {
        return eventName;
    }

    public String getContentURI() {
        return Stream.of(
            SpeedmentVersion.getImplementationTitle(),
            SpeedmentVersion.getImplementationVersion(),
            eventName,
            Settings.inst().get("user_mail", "no-mail-specified")
        ).collect(joining(SEPARATOR));
    }

    private static String encode(String name) {
        try {
            return URLEncoder.encode(name, ENCODING);
        } catch (UnsupportedEncodingException e) {
            return name;
        }
    }
}