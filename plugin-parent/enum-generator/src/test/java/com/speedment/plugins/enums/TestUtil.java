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
package com.speedment.plugins.enums;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.nio.file.Paths;
import java.util.Map;

public final class TestUtil {
    private TestUtil() {}

    public static Project project() throws SpeedmentException {
        final Project p = DocumentTranscoder.load(Paths.get("src", "test", "resources", "speedment.json"), TestUtil::fromJson);
        return p;
    }

    private static Map<String, Object> fromJson(String json) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> parsed = (Map<String, Object>) Json.fromJson(json);
        return parsed;
    }

}