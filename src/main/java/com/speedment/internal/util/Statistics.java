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
package com.speedment.internal.util;

import com.speedment.SpeedmentVersion;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.util.analytics.AnalyticsUtil;
import static com.speedment.internal.util.analytics.FocusPoint.APP_STARTED;
import static com.speedment.internal.util.analytics.FocusPoint.GENERATE;
import static com.speedment.internal.util.analytics.FocusPoint.GUI_PROJECT_LOADED;
import static com.speedment.internal.util.analytics.FocusPoint.GUI_STARTED;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import static com.speedment.util.NullUtil.requireNonNulls;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.CompletableFuture;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public final class Statistics {

    private final static String ENCODING = "UTF-8";
    private final static Logger LOGGER = LoggerManager.getLogger(Statistics.class);

    private final static String PING_URL = "http://stat.speedment.com:8081/Beacon";
    private final static String VERSION = SpeedmentVersion.getImplementationVersion();

    public static void onGuiStarted() {

        notifyEvent("gui-started", includeMail());
        AnalyticsUtil.notify(GUI_STARTED);
    }

    public static void onGuiProjectLoaded() {
        notifyEvent("gui-project-loaded", includeMail());
        AnalyticsUtil.notify(GUI_PROJECT_LOADED);
    }

    public static void onGenerate() {
        notifyEvent("generate", includeMail());
        AnalyticsUtil.notify(GENERATE);
    }

    public static void onNodeStarted() {
        notifyEvent("node-started");
        AnalyticsUtil.notify(APP_STARTED);
    }

    private static void notifyEvent(String event) {
        notifyEvent(event, Collections.emptyList());
    }

    private static void notifyEvent(String event, Param param) {
        notifyEvent(event, Collections.singletonList(requireNonNull(param)));
    }

    private static void notifyEvent(String event, Collection<Param> params) {
        requireNonNull(event);
        requireNonNulls(params);
        final List<Param> allParams = new ArrayList<>(params);
        allParams.add(new Param("project-key", Hash.md5(System.getProperty("user.dir"))));
        allParams.add(new Param("version", VERSION));
        allParams.add(new Param("event", event));
        sendPostRequest(allParams);
    }

    private static Param includeMail() {
        return new Param("mail", EmailUtil.getEmail());
    }

    private static void sendPostRequest(Collection<Param> params) {
        requireNonNulls(params);

        CompletableFuture.runAsync(() -> {
            final URL url = createRequestURL(params);

            try {
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();

                final int responseCode = con.getResponseCode();
                final String responseMessage = con.getResponseMessage();
                try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    final StringBuffer response = new StringBuffer();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    
                    // Do not show...
                    //LOGGER.info(Integer.toString(responseCode) + " " + responseMessage + " -> " + response.length() + " bytes");
                }

            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        });
    }

    private static URL createRequestURL(Collection<Param> params) {
        requireNonNull(params);
        try {
            return new URL(PING_URL + "?"
                + params.stream()
                .map(Param::encode)
                .collect(joining("&"))
            );
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Could not parse statistics url.", ex);
        }
    }

    private final static class Param {

        private final String key, value;

        public Param(String key, String value) {
            this.key = requireNonNull(key);
            this.value = requireNonNull(value);
        }

        public String encode() {
            try {

                return URLEncoder.encode(key, ENCODING) + "="
                    + URLEncoder.encode(value, ENCODING);
            } catch (UnsupportedEncodingException ex) {
                throw new SpeedmentException("Encoding '" + ENCODING + "' is not supported.", ex);
            }
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private Statistics() {
        instanceNotAllowed(getClass());
    }
}
