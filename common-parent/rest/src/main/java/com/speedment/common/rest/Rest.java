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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

/**
 * This is the main interface of the REST system.
 * <p>
 * Usage:
 * <pre>
 *     Rest rest = Rest.connect("127.0.0.1", 8080);
 * 
 *     CompletableFuture&lt;Response&gt; future = rest.post("user", 
 *         Param.of("firstname", "Adam"), 
 *         Param.of("lastname", "Adamsson")
 *     );
 * 
 *     future.get();
 * </pre>
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface Rest {
    
    enum Method {
        POST, GET, DELETE, PUT, OPTIONS, HEAD
    }
    
    enum Protocol {
        HTTP, HTTPS
    }
    
    CompletableFuture<Response> get(String path, Option... option);
    CompletableFuture<Response> post(String path, Option... option);
    CompletableFuture<Response> delete(String path, Option... option);
    CompletableFuture<Response> put(String path, Option... option);
    CompletableFuture<Response> options(String path, Option... option);
    CompletableFuture<Response> head(String path, Option... option);

    CompletableFuture<Response> get(String path, InputStream body, Option... option);
    CompletableFuture<Response> post(String path, InputStream body, Option... option);
    CompletableFuture<Response> delete(String path, InputStream body, Option... option);
    CompletableFuture<Response> put(String path, InputStream body, Option... option);
    CompletableFuture<Response> options(String path, InputStream body, Option... option);
    CompletableFuture<Response> head(String path, InputStream body, Option... option);

    CompletableFuture<Response> get(String path, Iterator<String> uploader, Option... option);
    CompletableFuture<Response> post(String path, Iterator<String> uploader, Option... option);
    CompletableFuture<Response> delete(String path, Iterator<String> uploader, Option... option);
    CompletableFuture<Response> put(String path, Iterator<String> uploader, Option... option);
    CompletableFuture<Response> options(String path, Iterator<String> uploader, Option... option);
    CompletableFuture<Response> head(String path, Iterator<String> uploader, Option... option);

    CompletableFuture<Response> get(String path, String data, Option... option);
    CompletableFuture<Response> post(String path, String data, Option... option);
    CompletableFuture<Response> delete(String path, String data, Option... option);
    CompletableFuture<Response> put(String path, String data, Option... option);
    CompletableFuture<Response> options(String path, String data, Option... option);
    CompletableFuture<Response> head(String path, String data, Option... option);

    static Rest connect(String host) {
        return new RestImpl(Protocol.HTTP, host, 0, null, null);
    }
    
    static Rest connect(String host, int port) {
        return new RestImpl(Protocol.HTTP, host, port, null, null);
    }
    
    static Rest connect(String host, String username, String password) {
        return new RestImpl(Protocol.HTTP, host, 0, username, password);
    }
    
    static Rest connect(String host, int port, String username, String password) {
        return new RestImpl(Protocol.HTTP, host, port, username, password);
    }
    
    static Rest connectHttps(String host) {
        return new RestImpl(Protocol.HTTPS, host, 0, null, null);
    }
    
    static Rest connectHttps(String host, int port) {
        return new RestImpl(Protocol.HTTPS, host, port, null, null);
    }
    
    static Rest connectHttps(String host, String username, String password) {
        return new RestImpl(Protocol.HTTPS, host, 0, username, password);
    }
    
    static Rest connectHttps(String host, int port, String username, String password) {
        return new RestImpl(Protocol.HTTPS, host, port, username, password);
    }
    
    static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (final UnsupportedEncodingException ex) {
            throw new IllegalStateException("Error encoding value '" + value + "'.");
        }
    }
}
