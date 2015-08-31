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
package com.speedment.internal.core.platform.component;

import com.speedment.annotation.Api;

/**
 * A Component represents the basic functionality for a Speedment Platform
 * Component.
 *
 * @author pemi
 * @since 2.0
 */
@Api(version = "2.0")
public interface Component {

    /**
     * Returns the Component interface Class this Component implements.
     *
     * @return the Component interface Class this Component implements
     */
    Class<? extends Component> getComponentClass();

    // Lifecycle operations for plugins
    /**
     * This method is called whenever this Component is added to a Component
     * manager.
     */
    default void onAdd() {
    }

    /**
     * This method is called whenever this Component is removed from a Component
     * manager.
     */
    default void onRemove() {
    }

}
