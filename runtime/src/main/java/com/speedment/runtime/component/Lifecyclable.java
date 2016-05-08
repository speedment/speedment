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
package com.speedment.runtime.component;

import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * A <code>Lifecyclable</code> is an object that has a defined life-cycles. The
 * different life-cycle stated are defined in the {@link State} enum and the
 * corresponding methods are defined hereunder.
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @param <T> the implementing type
 */
public interface Lifecyclable<T extends Lifecyclable<T>> {

    /**
     * Sets the {@link State} of this {@code Lifecyclable} directly. This should
     * only be called internally.
     *
     * @param newState the new state
     */
    void setState(State newState);

    /**
     * Returns the current {@link State} of this {@code Lifecyclable}.
     *
     * @return the current state
     * @see State
     */
    State getState();

    /**
     * Overridable method that can add logic before the initialize phase.
     *
     * @see #onInitialize()
     * @see #initialize()
     */
    default void preInitialize() {
    }

    /**
     * Overridable method that can add logic to the initialize phase.
     *
     * @see #preInitialize()
     * @see #initialize()
     */
    default void onInitialize() {
    }

    /**
     * Initialize this {@code Lifecyclable}. This method will call first the
     * {@link #preInitialize()} and then the {@link #onInitialize()} method.
     * <p>
     * This method should not be overriden by individual implementations of this
     * interface. Instead, to add logic to the initialization phase, override
     * the {@link #onInitialize()} method.
     *
     * @return this of type T
     * @see #preInitialize()
     * @see #onInitialize()
     */
    T initialize();

    /**
     * Overridable method that can add logic before the load phase.
     *
     * @see #onLoad()
     * @see #load()
     */
    default void preLoad() {
    }

    /**
     * Overridable method that can add logic to the load phase.
     *
     * @see #preLoad()
     * @see #load()
     */
    default void onLoad() {
    }

    /**
     * Loads this {@code Lifecyclable}. This method will make sure that the
     * {@code Lifecyclable} has been initialized before calling first the
     * {@link #preLoad()} and then the {@link #onLoad()} method.
     * <p>
     * This method should not be overriden by individual implementations of this
     * interface. Instead, to add logic to the loading phase, override the
     * {@link #onLoad()} method.
     *
     * @return this of type T
     * @see #preLoad()
     * @see #onLoad()
     */
    T load();

    /**
     * Overridable method that can add logic before the resolve phase.
     *
     * @see #onResolve()
     * @see #resolve()
     */
    default void preResolve() {
    }

    /**
     * Overridable method that can add logic to the resolve phase.
     *
     * @see #preResolve()
     * @see #resolve()
     */
    default void onResolve() {
    }

    /**
     * Resolves this {@code Lifecyclable}. This method will make sure that the
     * {@code Lifecyclable} has been initialized and loaded before calling first
     * the {@link #preResolve()} and then the {@link #onResolve()} method.
     * <p>
     * This method should not be overriden by individual implementations of this
     * interface. Instead, to add logic to the resolve phase, override the
     * {@link #onResolve()} method.
     *
     * @return this of type T
     * @see #preResolve()
     * @see #onResolve()
     */
    T resolve();

    /**
     * Overridable method that can add logic before the start phase.
     *
     * @see #onStart()
     * @see #start()
     */
    default void preStart() {
    }

    /**
     * Overridable method that can add logic to the start phase.
     *
     * @see #preStart()
     * @see #start()
     */
    default void onStart() {
    }

    /**
     * Starts this {@code Lifecyclable}. This method will make sure that the
     * {@code Lifecyclable} has been initialized, loaded and resolved before
     * calling first the {@link #preStart()} and then the {@link #onStart()}
     * method.
     * <p>
     * This method should not be overriden by individual implementations of this
     * interface. Instead, to add logic to the start phase, override the
     * {@link #onStart()} method.
     *
     * @return this of type T
     * @see #preStart()
     * @see #onStart()
     */
    T start();

    /**
     * Overridable method that can add logic before the stopping phase.
     *
     * @see #onStop()
     * @see #postStop()
     * @see #stop()
     */
    default void preStop() {
    }

    /**
     * Overridable method that can add logic to the stopping phase.
     *
     * @see #preStop()
     * @see #postStop()
     * @see #stop()
     */
    default void onStop() {
    }

    /**
     * Overridable method that can add logic after the stopping phase.
     *
     * @see #preStop()
     * @see #onStop()
     * @see #stop()
     */
    default void postStop() {
    }

    /**
     * Stops this {@code Lifecyclable}. This method will call first the
     * {@link #preStop()}, the {@link #onStop()} and then the
     * {@link #postStop()} method.
     * <p>
     * This method should not be overriden by individual implementations of this
     * interface. Instead, to add logic to the stopping phase, override the
     * {@link #onStop()} method.
     *
     * @return this of type T
     * @see #preStop()
     * @see #onStop()
     * @see #postStop()
     */
    T stop();

    /**
     * Returns {@code true} if this {@code Lifecyclable} is in the
     * {@link State#INIITIALIZED} or a later {@link State}. If that state has
     * not yet been reached, {@code false} is returned.
     *
     * @return {@code true} if this is initialized
     * @see #initialize()
     * @see State#INIITIALIZED
     */
    default boolean isInitialized() {
        return getState().onOrAfter(State.INIITIALIZED);
    }

    /**
     * Returns {@code true} if this {@code Lifecyclable} is in the
     * {@link State#LOADED} or a later {@link State}. If that state has not yet
     * been reached, {@code false} is returned.
     *
     * @return {@code true} if this is loaded
     * @see #load()
     * @see State#LOADED
     */
    default boolean isLoaded() {
        return getState().onOrAfter(State.LOADED);
    }

    /**
     * Returns {@code true} if this {@code Lifecyclable} is in the
     * {@link State#RESOLVED} or a later {@link State}. If that state has not
     * yet been reached, {@code false} is returned.
     *
     * @return {@code true} if this is resolved
     * @see #resolve()
     * @see State#RESOLVED
     */
    default boolean isResolved() {
        return getState().onOrAfter(State.RESOLVED);
    }

    /**
     * Returns {@code true} if this {@code Lifecyclable} is in the
     * {@link State#STARTED} or a later {@link State}. If that state has not yet
     * been reached, {@code false} is returned.
     *
     * @return {@code true} if this is started
     * @see #start()
     * @see State#STARTED
     */
    default boolean isStarted() {
        return getState().onOrAfter(State.STARTED);
    }

    /**
     * Returns {@code true} if this {@code Lifecyclable} is in the
     * {@link State#STOPPED} {@link State}. If that state has not yet been
     * reached, {@code false} is returned.
     *
     * @return {@code true} if this is stopped
     * @see #stop()
     * @see State#STOPPED
     */
    default boolean isStopped() {
        return getState().onOrAfter(State.STOPPED);
    }

    /**
     * The state of the {@code Lifecyclable} is defined by the enum constants.
     * Transition from one state to another must only be made sequentially in
     * strict ordinal order using the {@link #nextState()} method or an
     * equivalent.
     *
     * @see #CREATED
     * @see #INIITIALIZED
     * @see #LOADED
     * @see #RESOLVED
     * @see #STARTED
     * @see #STOPPED
     */
    public enum State {

        /**
         * The {@code Lifecyclable} has been created but no {@code Lifecyclable}
         * methods has been called on it yet.
         */
        CREATED,
        /**
         * The {@code Lifecyclable} has been initialized.
         * <p>
         * The following method(s) has been called and has completed on the
         * {@code Lifecyclable}:
         * <ul>
         * <li>{@link #initialize()}</li>
         * </ul>
         *
         */
        INIITIALIZED,
        /**
         * The {@code Lifecyclable} has been initialized and loaded.
         * <p>
         * The following method(s) has been called and has completed on the
         * {@code Lifecyclable}:
         * <ul>
         * <li>{@link #initialize()}</li>
         * <li>{@link #load()}</li>
         * </ul>
         *
         */
        LOADED,
        /**
         * The {@code Lifecyclable} has been initialized, loaded and resolved.
         * <p>
         * The following method(s) has been called and has completed on the
         * {@code Lifecyclable}:
         * <ul>
         * <li>{@link #initialize()}</li>
         * <li>{@link #load()}</li>
         * <li>{@link #resolve()}</li>
         * </ul>
         *
         */
        RESOLVED,
        /**
         * The {@code Lifecyclable} has been initialized, loaded, resolved and
         * started.
         * <p>
         * The following method(s) has been called and has completed on the
         * {@code Lifecyclable}:
         * <ul>
         * <li>{@link #initialize()}</li>
         * <li>{@link #load()}</li>
         * <li>{@link #resolve()}</li>
         * <li>{@link #start()}</li>
         * </ul>
         *
         */
        STARTED,
        /**
         * The {@code Lifecyclable} has been initialized, loaded, resolved,
         * started and stopped.
         * <p>
         * The following method(s) has been called and has completed on the
         * {@code Lifecyclable}:
         * <ul>
         * <li>{@link #initialize()}</li>
         * <li>{@link #load()}</li>
         * <li>{@link #resolve()}</li>
         * <li>{@link #start()}</li>
         * <li>{@link #stop()}</li>
         * </ul>
         *
         */
        STOPPED;

        /**
         * Asserts that the specified state is the correct next one. If it is
         * not, an {@code IllegalStateException} is thrown.
         *
         * @param nextState the proposed next state
         */
        public void checkNextState(State nextState) {
            requireNonNull(nextState);
            if (ordinal() + 1 != nextState.ordinal()) {
                throw new IllegalStateException("Cannot move from " + this + " to " + nextState);
            }
        }

        /**
         * Returns the next {@code State} after this, if any. If there is no
         * following {@code State}, an empty optional is returned.
         *
         * @return the next {@code State} or empty if there is none
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
         * returns if this State is the same or has already passed the provided
         * state.
         *
         * @param compareToState the {@code State} to compare this with
         * @return if same or earlier
         */
        public boolean onOrAfter(State compareToState) {
            requireNonNull(compareToState);
            return ordinal() >= compareToState.ordinal();
        }
    }

}
