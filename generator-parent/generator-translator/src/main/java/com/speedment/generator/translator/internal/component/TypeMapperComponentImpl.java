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
package com.speedment.generator.translator.internal.component;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.translator.exception.SpeedmentTranslatorException;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.bigdecimal.BigDecimalToDouble;
import com.speedment.runtime.typemapper.integer.IntegerZeroOneToBooleanMapper;
import com.speedment.runtime.typemapper.largeobject.ClobToStringMapper;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;
import com.speedment.runtime.typemapper.string.StringToLocaleMapper;
import com.speedment.runtime.typemapper.string.TrueFalseStringToBooleanMapper;
import com.speedment.runtime.typemapper.string.YesNoStringToBooleanMapper;
import com.speedment.runtime.typemapper.time.DateToIntMapper;
import com.speedment.runtime.typemapper.time.DateToLocalDateMapper;
import com.speedment.runtime.typemapper.time.DateToLongMapper;
import com.speedment.runtime.typemapper.time.TimeToIntMapper;
import com.speedment.runtime.typemapper.time.TimeToLocalTimeMapper;
import com.speedment.runtime.typemapper.time.TimeToLongMapper;
import com.speedment.runtime.typemapper.time.TimestampToIntMapper;
import com.speedment.runtime.typemapper.time.TimestampToLocalDateTimeMapper;
import com.speedment.runtime.typemapper.time.TimestampToLongMapper;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author  Emil Forslund
 * @since   2.2.0
 */
public final class TypeMapperComponentImpl implements TypeMapperComponent {

    private final Map<String, List<Supplier<TypeMapper<?, ?>>>> mappers;
    private @Inject Injector injector;

    /**
     * Constructs the component.
     */
    public TypeMapperComponentImpl() {
        this.mappers = new ConcurrentHashMap<>();

        // Special time mappers
        install(Date.class,      DateToLocalDateMapper::new);
        install(Date.class,      DateToLongMapper::new);
        install(Date.class,      DateToIntMapper::new);
        install(Timestamp.class, TimestampToLongMapper::new);
        install(Timestamp.class, TimestampToIntMapper::new);
        install(Timestamp.class, TimestampToLocalDateTimeMapper::new);
        install(Time.class,      TimeToLongMapper::new);
        install(Time.class,      TimeToIntMapper::new);
        install(Time.class,      TimeToLocalTimeMapper::new);
        
        // Special string mappers
        install(String.class, StringToLocaleMapper::new);
        install(String.class, TrueFalseStringToBooleanMapper::new);
        install(String.class, YesNoStringToBooleanMapper::new);

        // Special BigDecimal object mappers
        install(BigDecimal.class, BigDecimalToDouble::new);

        // Special Large object mappers
        install(Clob.class, ClobToStringMapper::new);
        
        // Special integer mappers
        install(Integer.class, IntegerZeroOneToBooleanMapper::new);
        
        // Primitive mappers
        install(Byte.class,      PrimitiveTypeMapper<Byte>::new);
        install(Short.class,     PrimitiveTypeMapper<Short>::new);
        install(Integer.class,   PrimitiveTypeMapper<Integer>::new);
        install(Long.class,      PrimitiveTypeMapper<Long>::new);
        install(Float.class,     PrimitiveTypeMapper<Float>::new);
        install(Double.class,    PrimitiveTypeMapper<Double>::new);
        install(Boolean.class,   PrimitiveTypeMapper<Boolean>::new);
        install(Character.class, PrimitiveTypeMapper<Character>::new);
    }

    @Override
    public void install(Class<?> databaseType, Supplier<TypeMapper<?, ?>> typeMapperConstructor) {
        mappers.computeIfAbsent(
            databaseType.getName(), 
            n -> new LinkedList<>()
        ).add(typeMapperConstructor);
    }

    @Override
    public final Stream<TypeMapper<?, ?>> mapFrom(Class<?> databaseType) {
        return mappers.getOrDefault(databaseType.getName(), Collections.emptyList())
            .stream()
            .map(Supplier::get)
            .map(injector::inject);
    }

    @Override
    public Stream<TypeMapper<?, ?>> stream() {
        return mappers.values().stream()
            .flatMap(List::stream)
            .map(Supplier::get)
            .map(injector::inject);
    }

    @Override
    public Optional<TypeMapper<?, ?>> get(String absoluteClassName) {
        return stream()
            .filter(tm -> tm.getClass().getName().equals(absoluteClassName))
            .findAny();
    }
    
    @Override
    public TypeMapper<?, ?> get(Column column) {
        return injector.inject(
            column.getTypeMapper().map(name -> {
                try {
                    @SuppressWarnings("unchecked")
                    final TypeMapper<Object, Object> mapper = 
                        (TypeMapper<Object, Object>) Class.forName(name).newInstance();
                    return mapper;
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                    throw new SpeedmentTranslatorException("Could not instantiate TypeMapper: '" + name + "'.", ex);
                }
            }).orElseGet(TypeMapper::identity)
        );
    }
}