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

import java.util.Objects;

/**
 * This class provides an abstract implementation of a
 * <code>Lifecyclable</code>. It also introduces the following overridable
 * events for each <code>Lifecyclable</code> transition:
 * <ul>
 * <li>{@link #onInit()}</li>
 * <li>{@link #onResolve()}</li>
 * <li>{@link #onStart()}</li>
 * <li>{@link #onStop()}</li>
 * </ul>
 *
 * It also introduces the following Runnables that can be set dynamically during
 * run-time:
 * <ul>
 * <li>{@link #setPreInitialize(java.lang.Runnable)}</li>
 * <li>{@link #setPreResolve(java.lang.Runnable)}</li>
 * <li>{@link #setPreStart(java.lang.Runnable)}</li>
 * <li>{@link #setPreStop(java.lang.Runnable)}</li>
 * <li>{@link #setPostStop(java.lang.Runnable)}</li>
 * </ul>
 * The Runnable will be invoked upon its corresponding life-cycle method
 *
 * @author pemi
 * @param <T> the self type
 * @see com.speedment.internal.core.runtime.Lifecyclable
 * @since 2.0
 */
public abstract class AbstractLifecycle<T extends AbstractLifecycle<T>> implements Lifecyclable<T> {

    private State state;
    private Runnable preInit, preResolve, preStart, preStop, postStop;

    public AbstractLifecycle() {
        state = State.CREATED;
        preInit = preResolve = preStart = preStop = postStop = NOTHING;
    }

    /**
     * This method is called by the {@link #initialize()} method to support easy
     * over-riding.
     */
    protected abstract void onInit();

    /**
     * This method is called by the {@link #resolve()} method to support easy
     * over-riding.
     */
    protected abstract void onResolve();

    /**
     * This method is called by the {@link #start()} method to support easy
     * over-riding.
     */
    protected abstract void onStart();

    /**
     * This method is called by the {@link #stop()} method to support easy
     * over-riding.
     */
    protected abstract void onStop();

    @Override
    public State getState() {
        return state;
    }

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    @Override
    public T initialize() {
        getState().checkNextState(State.INIITIALIZED);
        preInit.run();
        onInit();
        state = State.INIITIALIZED;
        return self();
    }

    @Override
    public T resolve() {
        if (getState() == State.CREATED) {
            // Automatically call resolve() for conveniency
            initialize();
        }
        getState().checkNextState(State.RESOLVED);
        preResolve.run();
        onResolve();
        state = State.RESOLVED;
        return self();
    }

    @Override
    public T start() {
        if (getState() == State.CREATED) {
            // Automatically call init() for conveniency
            initialize();
        }
        if (getState() == State.INIITIALIZED) {
            // Automatically call init() for conveniency
            resolve();
        }
        getState().checkNextState(State.STARTED);
        preStart.run();
        onStart();
        state = State.STARTED;
        return self();
    }

    @Override
    public T stop() {
        getState().checkNextState(State.STOPPED);
        preStop.run();
        onStop();
        postStop.run();
        state = State.STOPPED;
        return self();
    }

    @Override
    public String toString() {
        return getState().toString();
    }

    // RUNNABLES
    private static final Runnable NOTHING = () -> {
    };

    /**
     * Sets the non-null pre-initialize {@link Runnable} that is to be run
     * before the {@link #onInit()} method is called.
     *
     * @param preInit Runnable to set
     * @return this
     */
    public T setPreInitialize(Runnable preInit) {
        this.preInit = Objects.requireNonNull(preInit);
        return self();
    }

    /**
     * Sets the non-null pre-resolve {@link Runnable} that is to be run before
     * the {@link #onResolve()} method is called.
     *
     * @param preResolve Runnable to set
     * @return this
     */
    public T setPreResolve(Runnable preResolve) {
        this.preResolve = Objects.requireNonNull(preResolve);
        return self();
    }

    /**
     * Sets the non-null pre-start {@link Runnable} that is to be run before the
     * {@link #onStart()} method is called.
     *
     * @param preStart Runnable to set
     * @return this
     */
    public T setPreStart(Runnable preStart) {
        this.preStart = Objects.requireNonNull(preStart);
        return self();
    }

    /**
     * Sets the non-null pre-stop {@link Runnable} that is to be run before the
     * {@link #onStop()} method is called.
     *
     * @param preStop Runnable to set
     * @return this
     */
    public T setPreStop(Runnable preStop) {
        this.preStop = Objects.requireNonNull(preStop);
        return self();
    }

    /**
     * Sets the non-null post-stop {@link Runnable} that is to be run after the
     * {@link #onStop()} method has been called.
     *
     * @param postStop Runnable to set
     * @return this
     */
    public T setPostStop(Runnable postStop) {
        this.postStop = Objects.requireNonNull(postStop);
        return self();
    }

}
