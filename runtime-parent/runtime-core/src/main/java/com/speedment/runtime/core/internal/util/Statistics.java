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
package com.speedment.runtime.core.internal.util;

import com.speedment.common.json.Json;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.internal.util.testing.TestSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * Utility class for reporting events to the statistics manager.
 *
 * @author Emil Forslund
 */
public final class Statistics {

    public enum Event {

        GUI_STARTED        ("gui-started"),
        GUI_PROJECT_LOADED ("gui-project-loaded"),
        GENERATE           ("generate"),
        NODE_STARTED       ("node-started"),
        NODE_ALIVE         ("node-alive"),
        NODE_STOPPED       ("node-stopped");

        private final String eventName;

        Event(String eventName) {
            this.eventName = eventName;
        }
    }

    private final static Logger LOGGER   = LoggerManager.getLogger(Statistics.class);
    private final static String PING_URL = "https://api.speedment.com:9020/stats";

    public static void report(final InfoComponent info,
                              final ProjectComponent projects,
                              final Event event) {

        requireNonNull(info);
        requireNonNull(projects);
        requireNonNull(event);

        if (TestSettings.isTestMode()) {
            return;
        }

        final Project project = projects.getProject();
        final Map<String, Object> ping = new HashMap<>();

        ping.put("userId", InternalEmailUtil.getUserId().toString());
        ping.put("appId", project.getAppId());
        ping.put("eventType", event.eventName);
        ping.put("productName", info.getTitle());
        ping.put("productVersion", info.getImplementationVersion());
        ping.put("databases", project.dbmses()
            .map(Dbms::getTypeName)
            .distinct()
            .collect(toList())
        );
        ping.put("emailAddress", InternalEmailUtil.getEmail());
        ping.put("computerName", System.getProperty("user.dir"));
        ping.put("dateStarted", STARTED);

        sendPostRequest(PING_URL, Json.toJson(ping));
    }
    private static void sendPostRequest(final String url, String data) {
        if (!TestSettings.isTestMode()) { // Wolkswagen Pattern

            CompletableFuture.runAsync(() -> {
                try {
                    final HttpURLConnection con = (HttpURLConnection)
                        new URL(url).openConnection();

                    con.connect();
                    con.getResponseCode(); // Might have side effects...
                    con.getResponseMessage();

                    try (final BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()))) {
                        while (in.readLine() != null);
                    }
                } catch (final IOException ex) {
                    LOGGER.debug(ex);
                }
            });
        }
    }

    private static final long STARTED =
        Instant.now(Clock.system(ZoneId.of("UTC"))).getEpochSecond();

    /**
     * Utility classes should not be instantiated.
     */
    private Statistics() {
        throw new UnsupportedOperationException();
    }
}
