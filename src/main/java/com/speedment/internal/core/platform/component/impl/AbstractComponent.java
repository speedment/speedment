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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.internal.core.runtime.Lifecyclable;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public abstract class AbstractComponent implements Component {

    private final Speedment speedment;
        private Lifecyclable.State state;

    public AbstractComponent(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
    }

    @Override
    public Speedment getSpeedment() {
        return speedment;
    }

    @Override
    public String getTitle() {
        return getClass().getSimpleName();
    }
    
        @Override
    public AbstractComponent initialize() {
        state = State.INIITIALIZED;
        return this;
    }

    @Override
    public AbstractComponent resolve() {
        state = State.RESOLVED;
        return this;
    }

    @Override
    public AbstractComponent start() {
        state = State.STARTED;
        return this;
    }

    @Override
    public AbstractComponent stop() {
        state = State.STOPPED;
        return this;
    }

    @Override
    public Lifecyclable.State getState() {
        return state;
    }

}
