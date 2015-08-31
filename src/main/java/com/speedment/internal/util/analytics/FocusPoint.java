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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import static java.util.Objects.requireNonNull;

public final class FocusPoint {

    public static final String VERSION = SpeedmentVersion.getImplementationVersion();
    public static final String TITLE = SpeedmentVersion.getImplementationTitle();

    public static final FocusPoint CORE = new FocusPoint(TITLE);
    public static final FocusPoint CORE_WITH_VERSION = new FocusPoint(VERSION, CORE);
	public static final FocusPoint GUI_STARTED = new FocusPoint("GuiStarted", CORE_WITH_VERSION);
	public static final FocusPoint APP_STARTED = new FocusPoint("AppStarted", CORE_WITH_VERSION);
    public static final FocusPoint GENERATE = new FocusPoint("Generate", CORE_WITH_VERSION);

    private final String name;
    private final FocusPoint parentFocusPoint;
    private static final String URI_SEPARATOR = "/";
    private static final String TITLE_SEPARATOR = "-";

    public FocusPoint(String name) {
        this(name, null);
    }

    public FocusPoint(String name, FocusPoint parentFocusPoint) {
        this.name = requireNonNull(name,"Name must be non-null. ");
        this.parentFocusPoint = parentFocusPoint; // nullable
    }

    public String getName() {
        return name;
    }

    public FocusPoint getParentFocusPoint() {
        return parentFocusPoint;
    }

    public String getContentURI() {
        final StringBuilder contentURIBuffer = new StringBuilder();
        getContentURI(contentURIBuffer, this);
        return contentURIBuffer.toString();
    }

    public String getContentTitle() {
        final StringBuilder titleBuffer = new StringBuilder();
        getContentTitle(titleBuffer, this);
        return titleBuffer.toString();
    }

    private void getContentURI(StringBuilder contentURIBuffer, FocusPoint focusPoint) {
        final FocusPoint parentFP = focusPoint.getParentFocusPoint();
        if (parentFP != null) {
            getContentURI(contentURIBuffer, parentFP);
        }
        contentURIBuffer.append(URI_SEPARATOR);
        contentURIBuffer.append(encode(focusPoint.getName()));
    }

    private String encode(String name) {
        try {
            return URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return name;
        }
    }

    private void getContentTitle(StringBuilder titleBuffer, FocusPoint focusPoint) {
        final FocusPoint parentFP = focusPoint.getParentFocusPoint();
        if (parentFP != null) {
            getContentTitle(titleBuffer, parentFP);
            titleBuffer.append(TITLE_SEPARATOR);
        }
        titleBuffer.append(encode(focusPoint.getName()));
    }
}
