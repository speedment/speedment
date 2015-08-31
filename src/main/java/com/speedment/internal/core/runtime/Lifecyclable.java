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
package com.speedment.internal.core.runtime;

import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * A <code>Lifecyclable</code> is an object that has a defined life-cycles. The
 * different life-cycle stated are defined in the {@link State} enum and the
 * corresponding methods are defined hereunder.
 *
 * @author pemi
 * @param <T> Return type
 */
public interface Lifecyclable<T extends Lifecyclable<T>> {

    /**
     * Initialize this <code>Lifecyclable</code>. This method can be used to set
     * initialize internal settings.
     *
     * @return this of type T
     */
    T initialize();

    /**
     * Resolve is called when all the <code>Lifecyclables</code> has been
     * initialized. This method can be used to link to other
     * <code>Lifecyclables</code>.
     *
     * @return this of type T
     */
    T resolve();

    /**
     * Starts this <code>Lifecyclable</code>. Starts the actual service after
     * which it can serve requests.
     *
     * @return this of type T
     */
    T start();

    /**
     * Stops this <code>Lifecyclable</code>. The method shall also deallocate
     * any resources previously allocated to this <code>Lifecyclable</code>.
     *
     * @return this of type T
     */
    T stop();

    /**
     * Returns the current {@link State} of this <code>Lifecyclable</code>.
     *
     * @return the current {@link State} of this <code>Lifecyclable</code>
     * @see State
     */
    State getState();

    /**
     * Returns if this <code>Lifecyclable</code> is initialized.
     *
     * @return if this <code>Lifecyclable</code> is initialized
     * @see #initialize()
     * @see State#INIITIALIZED
     */
    default boolean isInitialized() {
        return getState().is(State.INIITIALIZED);
    }

    /**
     * Returns if this <code>Lifecyclable</code> is resolved.
     *
     * @return if this <code>Lifecyclable</code> is resolved
     * @see #resolve()
     * @see State#RESOLVED
     */
    default boolean isResolved() {
        return getState().is(State.RESOLVED);
    }

    /**
     * Returns if this <code>Lifecyclable</code> is started.
     *
     * @return if this <code>Lifecyclable</code> is started
     * @see #start()
     * @see State#STARTED
     */
    default boolean isStarted() {
        return getState().is(State.STARTED);
    }

    /**
     * Returns if this <code>Lifecyclable</code> is stopped.
     *
     * @return if this <code>Lifecyclable</code> is stopped
     * @see #stop()
     * @see State#STOPPED
     */
    default boolean isStopped() {
        return getState().is(State.STOPPED);
    }

    /**
     * The state of the <code>Lifecyclable</code> is defined by the enum
     * constants. Transition from one state to another must only be made
     * sequentially in strict ordinal order using the {@link #nextState()}
     * method or an equivalent.
     *
     * @see #CREATED
     * @see #INIITIALIZED
     * @see #RESOLVED
     * @see #STARTED
     * @see #STOPPED
     *
     */
    public enum State {

        /**
         * The <code>Lifecyclable</code> has been created but no
         * <code>Lifecyclable</code> methods has been called on it yet.
         */
        CREATED,
        /**
         * The <code>Lifecyclable</code> has been initialized.
         *
         * The following method(s) has been called and has completed on the
         * <code>Lifecyclable</code>:
         * <ul>
         * <li>{@link #initialize()}</li>
         * </ul>
         *
         */
        INIITIALIZED,
        /**
         * The <code>Lifecyclable</code> has been initialized and resolved.
         *
         * The following method(s) has been called and has completed on the
         * <code>Lifecyclable</code>:
         * <ul>
         * <li>{@link #initialize()}</li>
         * <li>{@link #resolve()}</li>
         * </ul>
         *
         */
        RESOLVED,
        /**
         * The <code>Lifecyclable</code> has been initialized, resolved and
         * started.
         *
         * The following method(s) has been called and has completed on the
         * <code>Lifecyclable</code>:
         * <ul>
         * <li>{@link #initialize()}</li>
         * <li>{@link #resolve()}</li>
         * <li>{@link #start()}</li>
         * </ul>
         *
         */
        STARTED,
        /**
         * The <code>Lifecyclable</code> has been initialized, resolved, started
         * and stopped.
         *
         * The following method(s) has been called and has completed on the
         * <code>Lifecyclable</code>:
         * <ul>
         * <li>{@link #initialize()}</li>
         * <li>{@link #resolve()}</li>
         * <li>{@link #start()}</li>
         * <li>{@link #stop()}</li>
         * </ul>
         *
         */
        STOPPED;

        protected void checkNextState(State nextState) {
            requireNonNull(nextState);
            if (ordinal() + 1 != nextState.ordinal()) {
                throw new IllegalStateException("Cannot move from " + this + " to " + nextState);
            }
        }

        /**
         * Returns the next state after this, if any. If there is no following
         * State,
         *
         * @return if this State is on or after the provided state
         */
        public Optional<State> nextState() {
            final State[] states = State.values();
            if (states.length >= ordinal()) {
                return Optional.empty();
            }
            return Optional.of(states[ordinal() + 1]);
        }

        /**
         * Returns if this State is on or after the provided state. Thus, it
         * returns if this State is the same or has passed the provided state.
         *
         * @param compareToState the State to compare this State with
         * @return if this State is on or after the provided state
         */
        public boolean is(State compareToState) {
            requireNonNull(compareToState);
            return ordinal() >= compareToState.ordinal();
        }

    }

}
