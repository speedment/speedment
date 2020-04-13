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
package com.speedment.generator.core.event.trait;

import com.speedment.generator.core.event.Event;
import com.speedment.runtime.config.Project;

/**
 * An event that has something to do with a particular project.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */

public interface ProjectEvent extends Event {
    
    /**
     * Returns the project that this event is about.
     * 
     * @return  the project
     */
    Project project();
}
