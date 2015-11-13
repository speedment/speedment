/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.event;

import com.speedment.annotation.Api;
import com.speedment.config.Project;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version="2.3")
public final class AfterGenerate implements ProjectEvent {
    
    private final static String NAME = "AFTER_GENERATE";
    private final Project project;
    
    public static AfterGenerate EVENT = new AfterGenerate();
    
    private AfterGenerate() {
        this.project = null;
    }
    
    public AfterGenerate(Project project) {
        this.project = requireNonNull(project);
    }

    @Override
    public String name() {
        return NAME;
    }
    
    @Override
    public Project project() {
        return project;
    }
}