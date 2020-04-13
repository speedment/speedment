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
package com.speedment.generator.standard;

import com.speedment.common.json.Json;
import com.speedment.runtime.application.AbstractApplicationMetadata;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public enum Projects {
    SPEEDMENT_JSON("speedment.json"),
    SPRING_PLUGIN("spring-plugin.json"),
    DATA_STORE("data-store.json"),
    FOREIGN_KEY("foreign-key.json"),
    TYPE_MAPPER("type-mapper.json"),
    SEVERAL_DBMSES("several_dbms.json")
    ;

    private final String fileName;
    private final Project project;

    Projects(String fileName) {
        this.fileName = requireNonNull(fileName);
        project = projectHelper();
    }

    public Path path() {
        return Paths.get("src", "test", "resources", fileName);
    }

    public Project project() {return project; }

    public ApplicationMetadata applicationMetadata() {
        return new AbstractApplicationMetadata() {
            @Override
            protected Optional<String> getMetadata() {
                return Optional.empty();
            }
        };
    }

    private Project projectHelper() throws SpeedmentException {
        final Project p = DocumentTranscoder.load(path(), this::fromJson);
        return p;
    }

    private Map<String, Object> fromJson(String json) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> parsed = (Map<String, Object>) Json.fromJson(json);
        return parsed;
    }

}