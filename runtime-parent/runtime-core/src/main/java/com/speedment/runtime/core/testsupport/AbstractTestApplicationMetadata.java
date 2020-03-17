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
package com.speedment.runtime.core.testsupport;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.ProjectUtil;
import com.speedment.runtime.config.trait.HasNameUtil;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.ApplicationMetadata;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 * @deprecated This class should be replaced
 * 
 * This is a temporary support class that has been replaced in runtime-application.
 * Once the module system is reworked, this class should go away.
 * 
 */
@Deprecated
public abstract class AbstractTestApplicationMetadata implements ApplicationMetadata {

    protected AbstractTestApplicationMetadata() {}
    
    /**
     * Returns the meta data as a String that shall be used to build up the
     * complete Project meta data. If no metadata exists, returns an
     * empty optional.
     *
     * @return the meta data or empty if none exists for this session
     */
    protected abstract Optional<String> getMetadata();
    
    @Override
    public Project makeProject() {
        return getMetadata()
            .map(json -> DocumentTranscoder.load(json, this::fromJson)).orElseGet(() -> {
            final Map<String, Object> data = new ConcurrentHashMap<>();
            data.put(HasNameUtil.NAME, "Project");
            data.put(ProjectUtil.APP_ID, UUID.randomUUID().toString());
            return Project.create(data);
        });
    }
    
    private Map<String, Object> fromJson(String json) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> parsed =
            (Map<String, Object>) Json.fromJson(json);
        return parsed;
    }
}