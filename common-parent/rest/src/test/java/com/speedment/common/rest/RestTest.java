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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import java.util.List;
import java.util.concurrent.ExecutionException;

final class RestTest {

    private static ClientAndServer clientAndServer;

    private static Rest rest;

    @BeforeAll
    static void setup() {
        clientAndServer = ClientAndServer.startClientAndServer(18080);
        rest = Rest.connect("localhost", clientAndServer.getLocalPort());
    }

    @AfterAll
    static void tearDown() {
        clientAndServer.stop();
    }

    @Test
    void connect() {
        assertNotNull(Rest.connect("localhost"));
        assertNotNull(Rest.connect("localhost", 8080));
        assertNotNull(Rest.connect("localhost", 8080, "username", "password"));
        assertNotNull(Rest.connect("localhost", "username", "password"));
    }

    @Test
    void connectHttps() {
        assertNotNull(Rest.connectHttps("localhost"));
        assertNotNull(Rest.connectHttps("localhost", 8080));
        assertNotNull(Rest.connectHttps("localhost", 8080, "username", "password"));
        assertNotNull(Rest.connectHttps("localhost", "username", "password"));
    }

    @Test
    void encode() {
        assertNotNull(Rest.encode("value"));
        assertDoesNotThrow(() -> Rest.encode("value"));
    }

    @Test
    void get() throws ExecutionException, InterruptedException {
        clientAndServer
            .when(request()
                .withMethod("GET")
                .withPath("/test"))
            .respond(response()
                .withStatusCode(200)
                .withBody("{\"name\": \"Test Name\"}")
                .withHeader("Content-Type", "application/json"));

        final Response response = rest.get("test").get();

        assertEquals(200, response.getStatus());
        assertEquals("{\"name\": \"Test Name\"}", response.getText());
        assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
    }

    @Test
    void post() throws ExecutionException, InterruptedException {
        clientAndServer
            .when(request()
                .withMethod("POST")
                .withPath("/test")
                .withBody("{\"name\": \"Test Name\"}")
                .withHeader("Content-Type", "application/json"))
            .respond(response()
                .withStatusCode(201)
                .withBody("{\"id\": 1}")
                .withHeader("Content-Type", "application/json")
                .withHeader("Content-Length", "9"));

        final Response response =
            rest.post("test", "{\"name\": \"Test Name\"}",
                Header.header("Content-Type", "application/json"))
            .get();

        assertEquals(201, response.getStatus());
        assertEquals("{\"id\": 1}", response.getText());
        assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
    }

    @Test
    void delete() throws ExecutionException, InterruptedException {
        clientAndServer
            .when(request()
                .withMethod("DELETE")
                .withPath("/test/1"))
            .respond(response()
                .withStatusCode(204));

        final Response response = rest.delete("test/1").get();

        assertEquals(204, response.getStatus());
    }

    @Test
    void put() throws ExecutionException, InterruptedException {
        clientAndServer
            .when(request()
                .withMethod("PUT")
                .withPath("/test/1")
                .withBody("{\"name\": \"New Name\"}")
                .withHeader("Content-Type", "application/json"))
            .respond(response()
                .withStatusCode(204));

        final Response response =
            rest.put("test/1", "{\"name\": \"New Name\"}",
                Header.header("Content-Type", "application/json"))
            .get();

        assertEquals(204, response.getStatus());
    }

    @Test
    void options() throws ExecutionException, InterruptedException {
        clientAndServer
            .when(request()
                .withMethod("OPTIONS")
                .withPath("/test"))
            .respond(response()
                .withStatusCode(204)
                .withHeader("Allow", "GET", "POST", "DELETE", "PUT", "HEAD"));

        final Response response = rest.options("test").get();

        assertEquals(204, response.getStatus());

        final List<String> headerValues = response.getHeaders().get("Allow");

        assertTrue(headerValues.contains("GET"));
        assertTrue(headerValues.contains("POST"));
        assertTrue(headerValues.contains("DELETE"));
        assertTrue(headerValues.contains("PUT"));
        assertTrue(headerValues.contains("HEAD"));
    }

    @Test
    void head() throws ExecutionException, InterruptedException {
        clientAndServer
            .when(request()
                .withMethod("HEAD")
                .withPath("/test/1"))
            .respond(response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json"));

        final Response response = rest.head("test/1").get();

        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
    }

}
