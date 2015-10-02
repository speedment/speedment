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
package com.speedment.internal.core.manager;

import com.speedment.Manager;
import com.speedment.Speedment;
import com.speedment.internal.core.field.encoder.JsonEncoder;
import com.speedment.internal.core.runtime.Lifecyclable;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type for this Manager
 */
public abstract class AbstractManager<ENTITY> implements Manager<ENTITY> {

    private final Speedment speedment;
    private final JsonEncoder<ENTITY> sharedJasonFormatter;
    private Lifecyclable.State state;

    public AbstractManager(Speedment speedment) {
        this.speedment            = requireNonNull(speedment);
        this.state                = Lifecyclable.State.CREATED;
        this.sharedJasonFormatter = JsonEncoder.allOf(this);
    }

    @Override
    public Manager<ENTITY> initialize() {
        state = State.INIITIALIZED;
        return this;
    }

    @Override
    public Manager<ENTITY> resolve() {
        state = State.RESOLVED;
        return this;
    }

    @Override
    public Manager<ENTITY> start() {
        state = State.STARTED;
        return this;
    }

    @Override
    public Manager<ENTITY> stop() {
        state = State.STOPPED;
        return this;
    }

    @Override
    public Lifecyclable.State getState() {
        return state;
    }
    
    @Override
    public String toJson(ENTITY entity) {
        requireNonNull(entity);
        return sharedJasonFormatter.apply(entity);
    }

    /**
     * Returns the speedment instance used when creating this manager. This can
     * for an example be used to navigate to other runtime components.
     * 
     * @return  the speedment instance
     */
    protected final Speedment speedment() {
        return speedment;
    }
}
