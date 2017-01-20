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
package com.speedment.common.rest;

import static com.speedment.common.rest.Option.Type.HEADER;
import static com.speedment.common.rest.Option.Type.PARAM;
import static com.speedment.common.rest.Rest.encode;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 * Default implementation of the {@link Rest}-interface.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
class RestImpl implements Rest {
    
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
    
    protected String getProtocol() {
        switch (protocol) {
            case HTTP : return "http";
            case HTTPS : return "https";
            default : throw new UnsupportedOperationException(
                "Unknown enum constant '" + protocol + "'."
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
        try {
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
            
            return new URL(url.toString());
        } catch (final MalformedURLException ex) {
            throw new RuntimeException("Error building URL.", ex);
        }
    }
    
    private final static int BUFFER_SIZE = 1024;
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
            return send(method, path, options, StreamConsumer.IGNORE);
        } else {
            return send(method, path, options, out -> {
                while (iterator.hasNext()) {
                    out.write(iterator.next().getBytes(StandardCharsets.UTF_8));
                }
            });
        }
    }
    
    private CompletableFuture<Response> send(Method method, String path, Option[] options, StreamConsumer outStreamConsumer) {
        return CompletableFuture.supplyAsync(() -> {
            final Param[] params = Stream.of(options).filter(o -> o.getType() == PARAM).toArray(Param[]::new);
            final Header[] headers = Stream.of(options).filter(o -> o.getType() == HEADER).toArray(Header[]::new);
            
            final URL url = getUrl(path, params);
            
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                
                switch (method) {
                    case POST    : conn.setRequestMethod("POST"); break;
                    case GET     : conn.setRequestMethod("GET"); break;
                    case DELETE  : conn.setRequestMethod("DELETE"); break;
                    case OPTIONS : conn.setRequestMethod("OPTIONS"); break;
                    case PUT     : conn.setRequestMethod("PUT"); break;
                    default : throw new UnsupportedOperationException(
                        "Unknown enum constant '" + method + "'."
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
                
                final boolean doOutput = outStreamConsumer != StreamConsumer.IGNORE;
                conn.setDoOutput(doOutput);
                
                conn.connect();
                if (doOutput) {
                    try (final OutputStream out = conn.getOutputStream()) {
                        outStreamConsumer.writeTo(out);
                        out.flush();
                    }
                }

                int status = getResponseCodeFrom(conn);
                final String text;
                
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
                
                return new Response(status, text, conn.getHeaderFields());
            } catch (final IOException ex) {
                throw new RuntimeException("Could not send " + method.name() + "-command.", ex);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }
    
    private static int getResponseCodeFrom(HttpURLConnection conn) throws IOException {
        try {
            return conn.getResponseCode();
        } catch (final FileNotFoundException ex) {
            return 404;
        }
    }
    
    private final static Iterator<String> NO_ITERATOR = new Iterator<String>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            throw new IllegalStateException(
                "This method should never be called."
            );
        }
    };
    
    private final static class SingletonIterator<T> implements Iterator<T> {
        
        private final AtomicBoolean first = new AtomicBoolean(true);
        private final T instance;
        
        private SingletonIterator(T instance) {
            this.instance = instance;
        }

        @Override
        public boolean hasNext() {
            return first.compareAndSet(true, false);
        }

        @Override
        public T next() {
            return instance;
        }
    }
    
    @FunctionalInterface
    private interface StreamConsumer {
        StreamConsumer IGNORE = o -> {};
        void writeTo(OutputStream out) throws IOException;
    }
}