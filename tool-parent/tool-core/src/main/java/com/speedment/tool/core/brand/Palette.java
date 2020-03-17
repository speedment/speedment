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
package com.speedment.tool.core.brand;



/**
 * Container for colors that are used as part of a {@link Brand}.
 *
 * @author  Emil Forslund
 * @since   2.3.2
 */

public enum Palette {

    /**
     * A special color to use for catching the users attention to informational
     * messages.
     */
    INFO,
    /**
     * A special color to use for catching the users attention for when a task
     * is completed successfully.
     */
    SUCCESS,
    /**
     * A special color to use for warning the user when something might be
     * wrong.
     */
    WARNING,
    /**
     * A special color to use for displaying errors to the user, often as a
     * result of an exception.
     */
    ERROR,
    /**
     * A special color to use when asking for the users confirmation on
     * something.
     */
    CONFIRMATION,
    /**
     * A special color to use when asking for user credentials required for an
     * example when connecting to a database.
     */
    AUTHENTICATION

}