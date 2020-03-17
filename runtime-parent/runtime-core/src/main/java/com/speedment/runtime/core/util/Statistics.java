/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.util;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import com.speedment.common.json.Json;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.internal.util.InternalEmailUtil;
import com.speedment.runtime.core.internal.util.testing.TestSettings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Utility class for reporting events to the statistics manager.
 *
 * @author Emil Forslund
 */
public final class Statistics {

    private static final String HOST_NAME = computerName();
    private static final long STARTED = Instant.now(Clock.system(ZoneId.of("UTC"))).getEpochSecond();

    private Statistics() {}

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

    private static final Logger LOGGER   = LoggerManager.getLogger(Statistics.class);
    private static final String PING_URL = "https://service.speedment.com/stats";

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
        ping.put("computerName", HOST_NAME);
        ping.put("dateStarted", STARTED);

        sendPostRequest(PING_URL, Json.toJson(ping));
    }

    private static void sendPostRequest(final String url, String data) {
        CompletableFuture.runAsync(() -> {
            try {
                final HttpURLConnection con = (HttpURLConnection)
                    new URL(url).openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                con.setUseCaches(false);
                con.setAllowUserInteraction(false);
                con.setDoOutput(true);

                con.connect();
                try (final OutputStream out = con.getOutputStream()) {
                    out.write(data.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                }

                int status = getResponseCodeFrom(con);
                final String text;

                try (final BufferedReader rd = new BufferedReader(
                    new InputStreamReader(status >= 400
                        ? con.getErrorStream()
                        : con.getInputStream()))) {

                    final StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }

                    text = sb.toString();
                }

                LOGGER.debug("Statistics response %d: %s", status, text);
            } catch (final IOException ex) {
                LOGGER.debug(ex);
            }
        });
    }

    private static String computerName() {
        final String hostName;

        try {
            hostName = InetAddress.getLocalHost().getHostName();
            if (hostName != null && !hostName.isEmpty()) {
                return hostName;
            }
        } catch (final Exception ex) {
            // Ignore exception.
        }

        return Optional.ofNullable(System.getenv("COMPUTERNAME"))
            .orElseGet(() -> Optional.ofNullable(System.getenv("HOSTNAME"))
                .orElse("unknown")
            );
    }

    private static int getResponseCodeFrom(final HttpURLConnection conn)
    throws IOException {
        try {
            return conn.getResponseCode();
        } catch (final FileNotFoundException ex) {
            return 404;
        }
    }


}
