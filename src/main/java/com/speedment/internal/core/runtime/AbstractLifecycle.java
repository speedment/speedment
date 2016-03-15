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
package com.speedment.internal.core.runtime;

import com.speedment.component.Lifecyclable;
import static java.util.Objects.requireNonNull;

/**
 * This class provides an abstract implementation of a {@link Lifecyclable}. It 
 * also introduces the following overridable events for each 
 * {@code Lifecyclable} transition:
 * <ul>
 *      <li>{@link #onInit()}</li>
 *      <li>{@link #onLoad()}</li>
 *      <li>{@link #onResolve()}</li>
 *      <li>{@link #onStart()}</li>
 *      <li>{@link #onStop()}</li>
 * </ul>
 *<p>
 * It also introduces the following Runnables that can be set dynamically during
 * run-time:
 * <ul>
 *      <li>{@link #setPreInitialize(Runnable)}</li>
 *      <li>{@link #setPreLoad(Runnable)}</li>
 *      <li>{@link #setPreResolve(Runnable)}</li>
 *      <li>{@link #setPreStart(Runnable)}</li>
 *      <li>{@link #setPreStop(Runnable)}</li>
 *      <li>{@link #setPostStop(Runnable)}</li>
 * </ul>
 * <p>
 * The Runnable will be invoked upon its corresponding life-cycle method.
 *
 * @author     Per Minborg
 * @author     Emil Forslund
 * @param <T>  the self type
 * @see        Lifecyclable
 * @since      2.0
 */
public abstract class AbstractLifecycle<T extends Lifecyclable<T>> implements Lifecyclable<T> {
    
    private static final Runnable NOTHING = () -> {};

    private State state;
    private Runnable preInit, preLoad, preResolve, preStart, preStop, postStop;
    
    protected AbstractLifecycle() {
        state = State.CREATED;
        preInit = preLoad = preResolve = preStart = preStop = postStop = NOTHING;
    }

    @Override
    public final void setState(State newState) {
        this.state = requireNonNull(newState);
    }

    @Override
    public final State getState() {
        return state;
    }

    /**
     * Sets the non-null pre-initialize {@link Runnable} that is to be run
     * before the {@link #onInit()} method is called.
     *
     * @param preInit Runnable to set
     * @return this
     */
    public final T setPreInitialize(Runnable preInit) {
        this.preInit = requireNonNull(preInit);
        return self();
    }
    
    /**
     * Sets the non-null pre-load {@link Runnable} that is to be run
     * before the {@link #onLoad()} method is called.
     *
     * @param preLoad Runnable to set
     * @return this
     */
    public final T setPreLoad(Runnable preLoad) {
        this.preLoad = requireNonNull(preLoad);
        return self();
    }

    /**
     * Sets the non-null pre-resolve {@link Runnable} that is to be run before
     * the {@link #onResolve()} method is called.
     *
     * @param preResolve Runnable to set
     * @return this
     */
    public final T setPreResolve(Runnable preResolve) {
        this.preResolve = requireNonNull(preResolve);
        return self();
    }

    /**
     * Sets the non-null pre-start {@link Runnable} that is to be run before the
     * {@link #onStart()} method is called.
     *
     * @param preStart Runnable to set
     * @return this
     */
    public final T setPreStart(Runnable preStart) {
        this.preStart = requireNonNull(preStart);
        return self();
    }

    /**
     * Sets the non-null pre-stop {@link Runnable} that is to be run before the
     * {@link #onStop()} method is called.
     *
     * @param preStop Runnable to set
     * @return this
     */
    public final T setPreStop(Runnable preStop) {
        this.preStop = requireNonNull(preStop);
        return self();
    }

    /**
     * Sets the non-null post-stop {@link Runnable} that is to be run after the
     * {@link #onStop()} method has been called.
     *
     * @param postStop Runnable to set
     * @return this
     */
    public final T setPostStop(Runnable postStop) {
        this.postStop = requireNonNull(postStop);
        return self();
    }
    
    @Override
    public T preInitialize() {
        preInit.run();
        return Lifecyclable.super.preInitialize();
    }
    
    @Override
    public T preLoad() {
        preLoad.run();
        return Lifecyclable.super.preLoad();
    }
    
    @Override
    public T preResolve() {
        preResolve.run();
        return Lifecyclable.super.preResolve();
    }
    
    @Override
    public T preStart() {
        preStart.run();
        return Lifecyclable.super.preStart();
    }
    
    @Override
    public T preStop() {
        preStop.run();
        return Lifecyclable.super.preStop();
    }

    @Override
    public T postStop() {
        postStop.run();
        return Lifecyclable.super.postStop();
    }
    
    @Override
    public String toString() {
        return getState().toString();
    }
    
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }
}