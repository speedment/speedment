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
import com.speedment.internal.core.config.mapper.identity.IntegerIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.LongIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.StringIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.TimestampIdentityMapper;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 2.2
 */
public final class TypeMapperComponentImpl extends Apache2AbstractComponent implements TypeMapperComponent {

    private final Map<String, TypeMapper<?, ?>> mappers;
    
    public final static class TypeMapperProvider implements Function<Speedment, Stream<TypeMapper<?, ?>>> {

        @Override
        public Stream<TypeMapper<?, ?>> apply(Speedment speedment) {
            return speedment.get(TypeMapperComponent.class).stream();
        }
        
    }

    /**
     * Constructs the component.
     * 
     * @param speedment  the speedment instance
     */
    public TypeMapperComponentImpl(Speedment speedment) {
        super(speedment);
        this.mappers = new ConcurrentHashMap<>();
        
        install(IntegerIdentityMapper::new);
        install(LongIdentityMapper::new);
        install(StringIdentityMapper::new);
        install(TimestampIdentityMapper::new);
    }
    
    @Override
    public final Class<TypeMapperComponent> getComponentClass() {
        return TypeMapperComponent.class;
    }

    @Override
    public final void install(Supplier<TypeMapper<?, ?>> typeMapperConstructor) {
        final TypeMapper<?, ?> mapper = typeMapperConstructor.get();
        mappers.put(mapper.getClass().getName(), mapper);
    }

    @Override
    public final Stream<TypeMapper<?, ?>> stream() {
        return mappers.values().stream();
    }

    @Override
    public Optional<TypeMapper<?, ?>> get(String absoluteClassName) {
        return Optional.ofNullable(mappers.get(absoluteClassName));
    }
}