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
package com.speedment.generator.core.event;

import com.speedment.generator.core.component.EventComponent;

/**
 * An event passed to the {@link EventComponent}.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public interface Event {
    
    /**
     * The name of the event. This is the key that will be used
     * to determine which listeners might be interested.
     * <p>
     * The default name of any event is the simple class name.
     * 
     * @return  the name of the event
     */
    default String name() {
        return getClass().getSimpleName();
    }
    
    /**
     * Publishes this event to the specified {@link EventComponent}.
     * 
     * @param eventComponent  the component to publish to
     */
    default void publish(EventComponent eventComponent) {
        eventComponent.notify(this);
    }
}