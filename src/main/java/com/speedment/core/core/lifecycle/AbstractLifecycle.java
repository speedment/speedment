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
package com.speedment.core.core.lifecycle;

import com.speedment.util.analytics.AnalyticsUtil;
import static com.speedment.util.analytics.FocusPoint.APP_STARTED;
import java.util.Objects;

/**
 *
 * @author pemi
 * @param <T> the type
 */
public abstract class AbstractLifecycle<T extends AbstractLifecycle<T>> implements Lifecyclable<T> {

    private State state;
    private Runnable preInit, preResolve, preStart, preStop, postStop;

    public AbstractLifecycle() {
        state = State.INIT;
        preInit = preResolve = preStart = preStop = postStop = NOTHING;
    }

    protected abstract void onInit();

    protected abstract void onResolve();

    protected abstract void onStart();

    protected abstract void onStop();

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
        if (getState() == State.INIT) {
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
        AnalyticsUtil.notify(APP_STARTED);
		
		if (getState() == State.INIT) {
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

    public boolean isInitialized() {
        return getState().is(State.INIITIALIZED);
    }

    public boolean isResolved() {
        return getState().is(State.RESOLVED);
    }

    public boolean isStarted() {
        return getState().is(State.STARTED);
    }

    public boolean isStopped() {
        return getState().is(State.STOPPED);
    }

    @Override
    public String toString() {
        return getState().toString();
    }

    // RUNNABLES
    private static final Runnable NOTHING = () -> {
    };

    public T setPreInitialize(Runnable preInit) {
        this.preInit = Objects.requireNonNull(preInit);
        return self();
    }

    public T setPreResolve(Runnable preResolve) {
        this.preResolve = Objects.requireNonNull(preResolve);
        return self();
    }

    public T setPreStart(Runnable preStart) {
        this.preStart = Objects.requireNonNull(preStart);
        return self();
    }

    public T setPreStop(Runnable preStop) {
        this.preStop = Objects.requireNonNull(preStop);
        return self();
    }

    public T setPostStop(Runnable postStop) {
        this.postStop = Objects.requireNonNull(postStop);
        return self();
    }

}
