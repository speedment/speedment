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

import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class AnalyticsUtil {

    private static final String TRACKING_CODE = "UA-54384165-1";

    public static void notify(final FocusPoint focusPoint) {
        requireNonNull(focusPoint);
        final JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(TRACKING_CODE);
        tracker.setLoggingAdapter(new LoggingAdapterImpl());
        tracker.trackAsynchronously(focusPoint);
    }

    public static void notify(final String focusPointName) {
        requireNonNull(focusPointName);
        notify(new FocusPoint(focusPointName));
    }

    /**
     * Utility classes should not be instantiated.
     */
    private AnalyticsUtil() { instanceNotAllowed(getClass()); }
}