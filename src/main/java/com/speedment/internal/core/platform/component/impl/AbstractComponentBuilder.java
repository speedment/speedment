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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.component.ComponentConstructor;
import static java.util.Objects.requireNonNull;

/**
 * Abstract builder for a {@link Component} implementation.
 * 
 * @author     Emil Forslund
 * @param <C>  the component type
 */
public abstract class AbstractComponentBuilder<C extends Component> 
    implements ComponentConstructor<C> {
    
    private final ComponentConstructor<C> constructor;
    
    protected AbstractComponentBuilder(ComponentConstructor<C> constructor) {
        this.constructor = requireNonNull(constructor);
    }

    @Override
    public final C create(Speedment speedment) {
        return constructor.create(speedment);
    }
}