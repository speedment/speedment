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
package com.speedment.runtime.internal.core.platform.component.impl;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.internal.core.runtime.AbstractLifecycle;
import com.speedment.runtime.internal.logging.Logger;
import com.speedment.runtime.internal.logging.LoggerManager;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 */
public abstract class AbstractComponent extends AbstractLifecycle<Component> implements Component {
    
    private final static Logger LOGGER = LoggerManager.getLogger(AbstractComponent.class);

    private final Speedment speedment;

    public AbstractComponent(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
    }
    
    @Override
    public boolean isInternal() {
        return false;
    }
    
    @Override
    public Speedment getSpeedment() {
        return speedment;
    }
}