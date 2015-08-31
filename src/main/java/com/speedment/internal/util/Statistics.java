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
package com.speedment.internal.util;

import com.speedment.SpeedmentVersion;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public final class Statistics {

    private final static String PING_URL = "http://speedment.com:8081/Beacon";
    private final static String VERSION = SpeedmentVersion.getImplementationVersion();
    private final static Settings SETTINGS = Settings.inst();

    public static void onGuiStarted() {
        notifyEvent("gui-started",
            new Param("mail", SETTINGS.get("user_mail", "no-mail-specified"))
        );
    }

    public static void onGenerate() {
        notifyEvent("generate");
    }

    public static void onNodeStarted() {
        notifyEvent("node-started");
    }

    private static void notifyEvent(String event, Param... params) {
        requireNonNull(event);
        requireNonNulls(params);
        final Param[] all = Arrays.copyOf(params, params.length + 3);
        all[all.length - 3] = new Param("project-key", Hash.md5(System.getProperty("user.dir")));
        all[all.length - 2] = new Param("version", VERSION);
        all[all.length - 1] = new Param("event", event);
        sendPostRequest(all);
    }

    private static void sendPostRequest(Param... params) {
        requireNonNulls(params);
        new Thread(() -> {
            final URL url = createRequestURL(params);

            try {
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoOutput(true);
                con.setRequestMethod("GET");

                try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                    wr.writeBytes("ping");
                    wr.flush();
                }

                con.getResponseCode();
            } catch (IOException ex) {
                // Silent.
            }
        }).start();
    }

    private static URL createRequestURL(Param... params) {
        requireNonNull(params);
        try {
            return new URL(PING_URL + "?"
                + Stream.of(params)
                .map(Param::encode)
                .collect(joining("&"))
            );
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Could not parse statistics url.", ex);
        }
    }

    private static class Param {

        private final String key, value;

        public Param(String key, String value) {
            this.key = requireNonNull(key);
            this.value = requireNonNull(value);
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String encode() {
            try {
                return URLEncoder.encode(getKey(), "UTF-8") + "="
                    + URLEncoder.encode(getValue(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException("Encoding 'UTF-8' is not supported.");
            }
        }
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private Statistics() { instanceNotAllowed(getClass()); }
}