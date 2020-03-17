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
package com.speedment.common.rest;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static com.speedment.common.rest.Option.Type.PARAM;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Exception thrown by a {@link Rest} object containing additional information
 * about the service that was requested.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
public final class RestException extends RuntimeException {

    private static final long serialVersionUID = 1783560981699960801L;

    private final Rest.Protocol protocol;
    private final Rest.Method method;
    private final String host;
    private final int port;
    private final String username;
    private final String path;

    public RestException(Throwable cause, Rest.Protocol protocol,
                         Rest.Method method, String username, String host,
                         int port, String path, Option[] options) {
        super(message(protocol, method, username, host, port, path, options), cause);
        this.protocol = requireNonNull(protocol);
        this.method   = requireNonNull(method);
        this.host     = requireNonNull(host);
        this.port     = port;
        this.username = username;
        this.path     = path;
    }

    @Override
    public String toString() {
        return "RestException{" +
            "protocol=" + protocol +
            ", method=" + method +
            ", host='" + host + '\'' +
            ", port=" + port +
            ", username='" + username + '\'' +
            ", path='" + path + '\'' +
            '}';
    }

    private static String message(Rest.Protocol protocol, Rest.Method method,
                                  String username, String host, int port,
                                  String path, Option[] options) {

        final StringBuilder str = new StringBuilder("Exception while invoking ");
        str.append(method).append(' ');
        if (username != null) str.append(username).append('@');
        str.append(protocol.name().toLowerCase()).append("://");
        str.append(host);
        if (port > 0) str.append(':').append(port);
        if (!path.isEmpty() && !path.equals("/")) {
            if (!path.startsWith("/")) str.append('/');
            str.append(path);
        }

        final Param[] params = Stream.of(options)
            .filter(Objects::nonNull)
            .filter(o -> o.getType() == PARAM)
            .filter(Param.class::isInstance)
            .map(Param.class::cast)
            .toArray(Param[]::new);

        if (params.length > 0) {
            str.append(
                Arrays.stream(params)
                    .map(Param::toString)
                    .collect(joining("&", "?", ""))
            );
        }

        return str.toString();
    }
}
