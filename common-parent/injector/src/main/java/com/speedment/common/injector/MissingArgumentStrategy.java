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
package com.speedment.common.injector;

import com.speedment.common.injector.execution.Execution;

/**
 * Enumeration of the possible ways to deal with an argument being missing in
 * the injector when an {@link Execution} should be invoked.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
public enum MissingArgumentStrategy {

    /**
     * If the instance is not available for injection, throw an exception during
     * the injector build phase.
     */
    THROW_EXCEPTION,

    /**
     * If the instance is not available for injection, show a message in the
     * injector builder DEBUG log and then skip invoking this method.
     */
    SKIP_INVOCATION

}
