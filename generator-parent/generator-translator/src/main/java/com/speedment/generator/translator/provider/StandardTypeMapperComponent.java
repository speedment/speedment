/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.generator.translator.provider;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.generator.translator.internal.component.TypeMapperComponentImpl;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.trait.HasTypeMapper;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class StandardTypeMapperComponent implements TypeMapperComponent {

    private final TypeMapperComponentImpl inner;

    public StandardTypeMapperComponent() {
        this.inner = new TypeMapperComponentImpl();
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(Injector injector) {
        inner.setInjector(injector);
    }

    public void install(Class<?> databaseType, Supplier<TypeMapper<?, ?>> typeMapperConstructor) {
        inner.install(databaseType, typeMapperConstructor);
    }

    public Stream<TypeMapper<?, ?>> mapFrom(Class<?> databaseType) {
        return inner.mapFrom(databaseType);
    }

    public Stream<TypeMapper<?, ?>> stream() {
        return inner.stream();
    }

    public Optional<TypeMapper<?, ?>> get(String absoluteClassName) {
        return inner.get(absoluteClassName);
    }

    public TypeMapper<?, ?> get(HasTypeMapper column) {
        return inner.get(column);
    }

    public <DB_TYPE, JAVA_TYPE> Optional<Class<DB_TYPE>> findDatabaseTypeOf(TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper) {
        return inner.findDatabaseTypeOf(typeMapper);
    }

    @Override
    public Type typeOf(Column column) {
        return inner.typeOf(column);
    }

    @Override
    public TypeMapper.Category categoryOf(Column column) {
        return inner.categoryOf(column);
    }
}
