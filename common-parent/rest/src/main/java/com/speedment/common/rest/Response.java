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

import static java.util.Objects.requireNonNull;

import com.speedment.common.json.Json;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A response from the server than can be parsed into json, or just read to get
 * the status code.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class Response {
    
    private final int status;
    private final String text;
    private final Map<String, List<String>> headers;

    public Response(int status, String text, Map<String, List<String>> headers) {
        this.status  = status;
        this.text    = requireNonNull(text);
        this.headers = requireNonNull(headers);
    }

    public int getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
    
    public boolean success() {
        switch (status) {
            case 200: case 201: case 202: case 203:
            case 204: case 205: case 206: return true;
            default: return false;
        }
    }
    
    public Optional<Object> decodeJson() {
        if (!success()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(Json.fromJson(text));
        }
    }
    
    public Stream<Object> decodeJsonArray() {
        @SuppressWarnings("unchecked")
        final Collection<Object> results = (Collection<Object>) Json.fromJson(text);
        return results.stream();
    }
}
