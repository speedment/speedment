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
package com.speedment.runtime.internal.util;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.analytics.AnalyticsUtil;
import com.speedment.runtime.internal.util.testing.TestSettings;

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
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.speedment.runtime.internal.util.analytics.FocusPoint.*;
import static com.speedment.common.invariant.NullUtil.requireNonNullElements;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public final class Statistics {

    private final static Logger LOGGER = LoggerManager.getLogger(Statistics.class);
    private final static String PING_URL = "http://stat.speedment.com:8081/Beacon";

    public static void onGuiStarted(InfoComponent infoComponent) {
        notifyEvent(infoComponent, "gui-started", includeMail());
        AnalyticsUtil.notify(GUI_STARTED, infoComponent);
    }

    public static void onGuiProjectLoaded(InfoComponent infoComponent) {
        notifyEvent(infoComponent, "gui-project-loaded", includeMail());
        AnalyticsUtil.notify(GUI_PROJECT_LOADED, infoComponent);
    }

    public static void onGenerate(InfoComponent infoComponent) {
        notifyEvent(infoComponent, "generate", includeMail());
        AnalyticsUtil.notify(GENERATE, infoComponent);
    }

    public static void onNodeStarted(InfoComponent infoComponent) {
        notifyEvent(infoComponent, "node-started");
        AnalyticsUtil.notify(APP_STARTED, infoComponent);
    }

    private static void notifyEvent(InfoComponent infoComponent, final String event) {
        notifyEvent(infoComponent, event, emptyList());
    }

    private static void notifyEvent(InfoComponent infoComponent, final String event, final Param param) {
        notifyEvent(infoComponent, event, singletonList(requireNonNull(param)));
    }

    private static void notifyEvent(InfoComponent infoComponent, final String event, final Collection<Param> params) {
        requireNonNull(infoComponent);
        requireNonNull(event);
        requireNonNullElements(params);
        final List<Param> allParams = new ArrayList<>(params);
        allParams.add(new Param("project-key", Hash.md5(System.getProperty("user.dir"))));
        allParams.add(new Param("version", infoComponent.implementationVersion()));
        allParams.add(new Param("event", event));
        sendPostRequest(allParams);
    }

    private static Param includeMail() {
        return new Param("mail", EmailUtil.getEmail());
    }

    private static void sendPostRequest(final Collection<Param> params) {
        requireNonNullElements(params);

        if (!TestSettings.isTestMode()) { // Wolkswagen Pattern

            CompletableFuture.runAsync(() -> {
                final URL url = createRequestURL(params);

                try {
                    final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    
                    con.connect();
                    con.getResponseCode(); // Might have side effects...
                    con.getResponseMessage(); 
                    
                    try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                        final StringBuilder response = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                    }

                } catch (final IOException ex) {
                    LOGGER.debug(ex);
                }
            });
        }
    }

    private static URL createRequestURL(final Collection<Param> params) {
        requireNonNull(params);
        try {
            return new URL(PING_URL + "?"
                + params.stream()
                .map(Param::encode)
                .collect(joining("&"))
            );
        } catch (final MalformedURLException ex) {
            throw new RuntimeException("Could not parse statistics url.", ex);
        }
    }

    private final static class Param {

        private static final String ENCODING = "UTF-8";
        
        private final String key, value;

        public Param(final String key, final String value) {
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
