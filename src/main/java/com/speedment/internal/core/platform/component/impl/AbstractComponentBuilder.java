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
import com.speedment.component.ComponentBuilder;
import java.util.function.Function;

/**
 *
 * @author Emil Forslund
 * @param <C>
 */
public abstract class AbstractComponentBuilder<C extends Component> implements ComponentBuilder<C> {
    
    private final Function<Speedment, C> constructor;
    private Speedment speedment;
    
    protected AbstractComponentBuilder(Function<Speedment, C> constructor) {
        this.constructor = constructor;
    }

    @Override
    public final ComponentBuilder<C> withSpeedment(Speedment speedment) {
        this.speedment = speedment;
        return this;
    }

    @Override
    public final C build() {
        return constructor.apply(speedment);
    }
}