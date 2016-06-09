/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.event;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.EventComponent;

/**
 *
 * @author  Emil Forslund
 * @since   2.3
 */
@Api(version="2.4")
public interface Event {
    
    default String name() {
        return getClass().getSimpleName();
    }
    
    default void publish(EventComponent eventComponent) {
        eventComponent.notify(this);
    }
}