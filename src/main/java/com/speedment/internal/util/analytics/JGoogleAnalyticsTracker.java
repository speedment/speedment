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

public final class JGoogleAnalyticsTracker {

    private final HTTPGetMethod httpRequest = new HTTPGetMethod();
    private URLBuildingStrategy urlBuildingStrategy = null;
    private LoggingAdapter loggingAdapter;

    /**
     * Simple constructor passing the google analytics tracking code.
     *
     * @param googleAnalyticsTrackingCode (For ex: "UA-2184000-1")
     */
    public JGoogleAnalyticsTracker(String googleAnalyticsTrackingCode) {
        this.urlBuildingStrategy = new GoogleAnalytics_v1_URLBuildingStrategy(googleAnalyticsTrackingCode);
    }

    /**
     * Setter injection for URLBuildingStrategy incase if you want to use a
     * different url building logic.
     *
     * @param urlBuildingStrategy implemented instance of URLBuildingStrategy
     */
    public void setUrlBuildingStrategy(URLBuildingStrategy urlBuildingStrategy) {
        this.urlBuildingStrategy = urlBuildingStrategy;
    }

    /**
     * Setter injection for LoggingAdpater. You can hook up log4j, System.out or
     * any other loggers you want.
     *
     * @param loggingAdapter implemented instance of LoggingAdapter
     */
    public void setLoggingAdapter(LoggingAdapter loggingAdapter) {
        this.loggingAdapter = loggingAdapter;
        httpRequest.setLoggingAdapter(loggingAdapter);
    }

    /**
     * Track the focusPoint in the application synchronously.
     * <b>Please be cognizant while using this method. Since, it would have
     * a performance hit on the actual application. Use it unless it's really
     * needed</b>
     *
     * @param focusPoint Focus point of the application like application load,
     * application module load, user actions, error events etc.
     */
    public void trackSynchronously(FocusPoint focusPoint) {
        logMessage("JGoogleAnalytics: Tracking synchronously focusPoint=" + focusPoint.getContentTitle());
        httpRequest.request(urlBuildingStrategy.buildURL(focusPoint));
    }

    /**
     * Track the focusPoint in the application asynchronously.
     *
     * @param focusPoint Focus point of the application like application load,
     * application module load, user actions, error events etc.
     */
    public void trackAsynchronously(FocusPoint focusPoint) {
        logMessage("JGoogleAnalytics: Tracking Asynchronously focusPoint=" + focusPoint.getContentTitle());
        new TrackingThread(focusPoint).start();
    }

    private void logMessage(String message) {
        if (loggingAdapter != null) {
            loggingAdapter.logMessage(message);
        }
    }

    private class TrackingThread extends Thread {

        private final FocusPoint focusPoint;

        public TrackingThread(FocusPoint focusPoint) {
            this.focusPoint = focusPoint;
            this.setPriority(Thread.MIN_PRIORITY);
        }

        @Override
        public void run() {
            httpRequest.request(urlBuildingStrategy.buildURL(focusPoint));
        }
    }
}
