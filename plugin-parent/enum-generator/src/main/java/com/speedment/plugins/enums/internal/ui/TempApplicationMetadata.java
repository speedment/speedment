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
package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.injector.annotation.Config;
import com.speedment.common.json.Json;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.tool.core.exception.SpeedmentToolException;

import java.util.Map;

import static java.util.Objects.requireNonNull;

final class TempApplicationMetadata implements ApplicationMetadata {

    private final String json;

    public TempApplicationMetadata(@Config(name="temp.json", value="") String json) {
        this.json = requireNonNull(json);
    }

    @Override
    public Project makeProject() {
        try {
            @SuppressWarnings("unchecked") final Map<String, Object> data =
                (Map<String, Object>) Json.fromJson(json);
            return Project.create(data);
        } catch (final Exception ex) {
            throw new SpeedmentToolException(
                "Error deserializing temporary project JSON.", ex
            );
        }
    }
}
