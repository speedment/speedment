/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.injector;

/**
 * The state of an injectable instance.
 *
 * @author Emil Forslund
 * @since 1.0.0
 */
public enum State {

    /**
     * The Injectable has been created but it has not been exposed anywhere yet.
     */
    CREATED,
    /**
     * The Injectable has been initialized.
     */
    INITIALIZED,
    /**
     * The Injectable has been initialized and resolved.
     */
    RESOLVED,
    /**
     * The Injectable has been initialized, resolved and started.
     */
    STARTED,
    /**
     * The Injectable has been initialized, resolved, started and stopped.
     */
    STOPPED
}
