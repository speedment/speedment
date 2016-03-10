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
package com.speedment.component;

import com.speedment.Speedment;
import com.speedment.annotation.Api;
import com.speedment.license.Software;

/**
 * A Component represents the basic functionality for a Speedment Platform
 * Component.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.0
 */
@Api(version = "2.3")
public interface Component extends Lifecyclable<Component> {

    /**
     * Returns the Component interface Class this Component implements.
     *
     * @return the interface class
     */
    Class<? extends Component> getComponentClass();

    /**
     * Returns the Speedment platform.
     *
     * @return the Speedment platform
     */
    Speedment getSpeedment();

    /**
     * Returns information about this components title, version, license and any
     * dependencies on third-party software that it may have.
     *
     * @return the software information of this component
     */
    Software asSoftware();
    
    /**
     * Returns if this Component is part of core Speedment or not.
     * 
     * @return  {@code true} if this is an internal component
     */
    boolean isInternal();
}
