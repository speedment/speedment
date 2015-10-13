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
import com.speedment.component.TypeMapperComponent;
import com.speedment.config.mapper.TypeMapper;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 2.2
 */
public final class TypeMapperComponentImpl extends Apache2AbstractComponent implements TypeMapperComponent {

    private final Set<TypeMapper<?, ?>> mappers;

    /**
     * Constructs the component.
     * 
     * @param speedment  the speedment instance
     */
    public TypeMapperComponentImpl(Speedment speedment) {
        super(speedment);
        this.mappers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }
    
    @Override
    public Class<TypeMapperComponent> getComponentClass() {
        return TypeMapperComponent.class;
    }

    @Override
    public <DB_TYPE, JAVA_TYPE> void install(TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper) {
        mappers.add(typeMapper);
    }

    @Override
    public Stream<TypeMapper<?, ?>> stream() {
        return mappers.stream();
    }
}