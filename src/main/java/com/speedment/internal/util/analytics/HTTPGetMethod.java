/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public final class HTTPGetMethod {

    private static final String GET_METHOD_NAME = "GET";

    //private static final String SUCCESS_MESSAGE = "JGoogleAnalytics: Tracking Successful!";
    private LoggingAdapter loggingAdapter = null;

    public void setLoggingAdapter(LoggingAdapter loggingAdapter) {
        this.loggingAdapter = loggingAdapter;
    }

    private final static String UA_NAME = "Java/" + System.getProperty("java.version"); // java version info appended; // User Agent name
    private final static String OS_STRING = System.getProperty("os.name") + " " + System.getProperty("os.version");

    HTTPGetMethod() {
    }

    public void request(String urlString) {
        try {
            final URL url = new URL(urlString);
            final HttpURLConnection urlConnection = openURLConnection(url);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setRequestMethod(GET_METHOD_NAME);
            urlConnection.setRequestProperty("User-agent", UA_NAME + " (" + OS_STRING + ")");

            urlConnection.connect();
            final int responseCode = getResponseCode(urlConnection);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                logError("JGoogleAnalytics: Error tracking, url=" + urlString);
            } else {
                //logMessage(SUCCESS_MESSAGE);
            }
        } catch (Exception e) {
            logError(e.getMessage());
        }
    }

    protected int getResponseCode(HttpURLConnection urlConnection) throws IOException {
        return urlConnection.getResponseCode();
    }

    private HttpURLConnection openURLConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private void logMessage(String message) {
        if (loggingAdapter != null) {
            loggingAdapter.logMessage(message);
        }
    }

    private void logError(String errorMesssage) {
        if (loggingAdapter != null) {
            loggingAdapter.logError(errorMesssage);
        }
    }
}
