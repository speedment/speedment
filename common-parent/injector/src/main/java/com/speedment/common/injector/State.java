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

import java.util.NoSuchElementException;

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
    STOPPED;

    /**
     * Returns the previous state.
     *
     * @return the previous state
     * @throws java.util.NoSuchElementException if there are no previous state
     */
    public State previous() {
        if (this == CREATED) {
            throw new NoSuchElementException();
        }
        return values()[ordinal() - 1];
    }

    /**
     * Returns the next state.
     *
     * @return the next state
     * @throws java.util.NoSuchElementException if there are no next state
     */
    public State next() {
        if (this == STOPPED) {
            throw new NoSuchElementException();
        }
        return values()[ordinal() + 1];
    }

    /**
     * Returns {@code true} if this state is strictly before the specified
     * state.
     *
     * @param state  the state to check against
     * @return       {@code true} if this is before, otherwise {@code false}
     */
    public boolean isBefore(State state) {
        return ordinal() < state.ordinal();
    }
}
