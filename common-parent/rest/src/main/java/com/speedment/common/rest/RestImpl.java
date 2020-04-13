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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.speedment.common.rest.Option.Type.HEADER;
import static com.speedment.common.rest.Option.Type.PARAM;
import static com.speedment.common.rest.Rest.encode;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Default implementation of the {@link Rest}-interface.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
final class RestImpl implements Rest {

    private static final StreamConsumer IGNORE = o -> {};

    private final Protocol protocol;
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    RestImpl(Protocol protocol, String host, int port, String username, String password) {
        this.protocol = requireNonNull(protocol);
        this.host     = requireNonNull(host);
        this.port     = port;
        this.username = username; // Can be null
        this.password = password; // Can be null
    }
    
    @Override
    public CompletableFuture<Response> get(String path, Option... option) {
        return send(Method.GET, path, option);
    }

    @Override
    public CompletableFuture<Response> post(String path, Option... option) {
        return send(Method.POST, path, option);
    }

    @Override
    public CompletableFuture<Response> delete(String path, Option... option) {
        return send(Method.DELETE, path, option);
    }

    @Override
    public CompletableFuture<Response> put(String path, Option... option) {
        return send(Method.PUT, path, option);
    }

    @Override
    public CompletableFuture<Response> options(String path, Option... option) {
        return send(Method.OPTIONS, path, option);
    }

    @Override
    public CompletableFuture<Response> head(String path, Option... option) {
        return send(Method.HEAD, path, option);
    }
    
    @Override
    public CompletableFuture<Response> get(String path, Iterator<String> uploader, Option... option) {
        return send(Method.GET, path, option, uploader);
    }

    @Override
    public CompletableFuture<Response> post(String path, Iterator<String> uploader, Option... option) {
        return send(Method.POST, path, option, uploader);
    }

    @Override
    public CompletableFuture<Response> delete(String path, Iterator<String> uploader, Option... option) {
        return send(Method.DELETE, path, option, uploader);
    }

    @Override
    public CompletableFuture<Response> put(String path, Iterator<String> uploader, Option... option) {
        return send(Method.PUT, path, option, uploader);
    }

    @Override
    public CompletableFuture<Response> options(String path, Iterator<String> uploader, Option... option) {
        return send(Method.OPTIONS, path, option, uploader);
    }

    @Override
    public CompletableFuture<Response> head(String path, Iterator<String> uploader, Option... option) {
        return send(Method.HEAD, path, option, uploader);
    }
    
    @Override
    public CompletableFuture<Response> get(String path, InputStream body, Option... option) {
        return send(Method.GET, path, option, stream(body));
    }

    @Override
    public CompletableFuture<Response> post(String path, InputStream body, Option... option) {
        return send(Method.POST, path, option, stream(body));
    }

    @Override
    public CompletableFuture<Response> delete(String path, InputStream body, Option... option) {
        return send(Method.DELETE, path, option, stream(body));
    }

    @Override
    public CompletableFuture<Response> put(String path, InputStream body, Option... option) {
        return send(Method.PUT, path, option, stream(body));
    }

    @Override
    public CompletableFuture<Response> options(String path, InputStream body, Option... option) {
        return send(Method.OPTIONS, path, option, stream(body));
    }

    @Override
    public CompletableFuture<Response> head(String path, InputStream body, Option... option) {
        return send(Method.HEAD, path, option, stream(body));
    }
    
    @Override
    public CompletableFuture<Response> get(String path, String data, Option... option) {
        return send(Method.GET, path, option, new SingletonIterator<>(data));
    }

    @Override
    public CompletableFuture<Response> post(String path, String data, Option... option) {
        return send(Method.POST, path, option, new SingletonIterator<>(data));
    }

    @Override
    public CompletableFuture<Response> delete(String path, String data, Option... option) {
        return send(Method.DELETE, path, option, new SingletonIterator<>(data));
    }

    @Override
    public CompletableFuture<Response> put(String path, String data, Option... option) {
        return send(Method.PUT, path, option, new SingletonIterator<>(data));
    }

    @Override
    public CompletableFuture<Response> options(String path, String data, Option... option) {
        return send(Method.OPTIONS, path, option, new SingletonIterator<>(data));
    }

    @Override
    public CompletableFuture<Response> head(String path, String data, Option... option) {
        return send(Method.HEAD, path, option, new SingletonIterator<>(data));
    }
    
    protected String getProtocol() {
        switch (protocol) {
            case HTTP : return "http";
            case HTTPS : return "https";
            default : throw new UnsupportedOperationException(
                String.format("Unknown enum constant '%s'.", protocol)
            );
        }
    }

    protected String getHost() {
        return host;
    }

    protected int getPort() {
        return port;
    }
    
    protected final URL getUrl(String path, Param... param) {
        final StringBuilder url = new StringBuilder()
            .append(getProtocol())
            .append("://")
            .append(host);

        if (port > 0) {
            url.append(":").append(port);
        }

        url.append("/").append(path);

        if (param.length > 0) {
            url.append(
                Stream.of(param)
                    .map(p ->
                        encode(p.getKey()) +
                        "=" +
                        encode(p.getValue())
                    )
                    .collect(joining("&", "?", ""))
            );
        }

        final String urlStr = url.toString();
        try {
            return new URL(urlStr);
        } catch (final MalformedURLException ex) {
            throw new IllegalArgumentException(String.format(
                "Error building URL: '%s'.", urlStr
            ), ex);
        }
    }
    
    private static final int BUFFER_SIZE = 1024;
    private StreamConsumer stream(InputStream in) {
        return out -> {
            final byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        };
    }
    
    private CompletableFuture<Response> send(Method method, String path, Option[] options) {
        return send(method, path, options, NO_ITERATOR);
    }
    
    private CompletableFuture<Response> send(Method method, String path, Option[] options, Iterator<String> iterator) {
        if (iterator == NO_ITERATOR) {
            return send(method, path, options, IGNORE);
        } else {
            return send(method, path, options, out -> {
                int i = 0;
                while (iterator.hasNext()) {
                    final String it = iterator.next();
                    if (it == null) {
                        throw new NullPointerException(String.format(
                            "Null element at position %d in iterator over strings.",
                            i));
                    } else {
                        out.write(it.getBytes(StandardCharsets.UTF_8));
                    }
                    i++;
                }
            });
        }
    }
    
    private CompletableFuture<Response> send(Method method, String path, Option[] options, StreamConsumer outStreamConsumer) {
        return CompletableFuture.supplyAsync(() -> {
            HttpURLConnection conn = null;
            try {
                final Param[] params = Stream.of(options).filter(o -> o.getType() == PARAM).map(Param.class::cast).toArray(Param[]::new);
                final Header[] headers = Stream.of(options).filter(o -> o.getType() == HEADER).map(Header.class::cast).toArray(Header[]::new);
                final URL url = getUrl(path, params);

                conn = (HttpURLConnection) url.openConnection();

                switch (method) {
                    case POST    : conn.setRequestMethod("POST");    break;
                    case GET     : conn.setRequestMethod("GET");     break;
                    case DELETE  : conn.setRequestMethod("DELETE");  break;
                    case OPTIONS : conn.setRequestMethod("OPTIONS"); break;
                    case PUT     : conn.setRequestMethod("PUT");     break;
                    case HEAD    : conn.setRequestMethod("HEAD");    break;
                    default : throw new UnsupportedOperationException(
                        String.format("Unknown enum constant '%s'.", method)
                    );
                }

                if (username != null && password != null) {
                    final byte[] authentication = (username + ":" + password).getBytes();
                    final String encoding = Base64.getEncoder().encodeToString(authentication);
                    conn.setRequestProperty("Authorization", "Basic " + encoding);
                }

                for (final Header header : headers) {
                    conn.setRequestProperty(
                        header.getKey(),
                        header.getValue());
                }

                conn.setUseCaches(false);
                conn.setAllowUserInteraction(false);

                final boolean doOutput = outStreamConsumer != IGNORE;
                conn.setDoOutput(doOutput);

                conn.connect();
                if (doOutput) {
                    try (final OutputStream out = conn.getOutputStream()) {
                        outStreamConsumer.writeTo(out);
                        out.flush();
                    }
                }

                int status = getResponseCodeFrom(conn);
                final String text = tryGetResponseTextFrom(conn, status);

                return new Response(status, text, conn.getHeaderFields());
            } catch (final Exception ex) {
                throw new RestException(ex, protocol, method, username, host, port, path, options);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }

    private static String tryGetResponseTextFrom(HttpURLConnection conn, int status) throws IOException {
        String text;
        try (final BufferedReader rd = new BufferedReader(
                new InputStreamReader(status >= 400
                    ? conn.getErrorStream()
                    : conn.getInputStream()))) {

            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            text = sb.toString();
        }
        return text;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        if (username != null) str.append(username).append('@');
        str.append(protocol.name().toLowerCase()).append("://");
        str.append(host);
        if (port > 0) str.append(':').append(port);
        return str.toString();
    }

    private static int getResponseCodeFrom(HttpURLConnection conn) throws IOException {
        try {
            return conn.getResponseCode();
        } catch (final FileNotFoundException ex) {
            return 404;
        }
    }
    
    private static final Iterator<String> NO_ITERATOR = new Iterator<String>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            throw new NoSuchElementException(
                "This method should never be called."
            );
        }
    };

    private static final class SingletonIterator<E> implements Iterator<E> {

        private final E e;
        private boolean hasNext = true;

        private SingletonIterator(E e) {
            this.e = e;
        }

        public boolean hasNext() {
            return hasNext;
        }

        public E next() {
            if (hasNext) {
                hasNext = false;
                return e;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            if (hasNext) {
                action.accept(e);
                hasNext = false;
            }
        }
    }
    
    @FunctionalInterface
    private interface StreamConsumer {

        void writeTo(OutputStream out) throws IOException;
    }
}