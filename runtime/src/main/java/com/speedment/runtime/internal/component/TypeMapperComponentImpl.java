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
package com.speedment.runtime.internal.component;

import com.speedment.runtime.component.TypeMapperComponent;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.config.mapper.bigdecimal.BigDecimalToDouble;
import com.speedment.runtime.config.mapper.integer.IntegerZeroOneToBooleanMapper;
import com.speedment.runtime.config.mapper.largeobject.ClobToStringMapper;
import com.speedment.runtime.config.mapper.string.StringToLocaleMapper;
import com.speedment.runtime.config.mapper.string.TrueFalseStringToBooleanMapper;
import com.speedment.runtime.config.mapper.string.YesNoStringToBooleanMapper;
import com.speedment.runtime.config.mapper.time.DateToIntMapper;
import com.speedment.runtime.config.mapper.time.DateToLongMapper;
import com.speedment.runtime.config.mapper.time.TimeToIntMapper;
import com.speedment.runtime.config.mapper.time.TimeToLongMapper;
import com.speedment.runtime.config.mapper.time.TimestampToIntMapper;
import com.speedment.runtime.config.mapper.time.TimestampToLongMapper;
import com.speedment.runtime.license.Software;
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
public final class TypeMapperComponentImpl extends InternalOpenSourceComponent implements TypeMapperComponent {

    private final Map<String, List<TypeMapper<?, ?>>> mappers;

    /**
     * Constructs the component.
     */
    public TypeMapperComponentImpl() {
        this.mappers = new ConcurrentHashMap<>();

        // Special time mappers
        install(Date.class,      DateToLongMapper::new);
        install(Timestamp.class, TimestampToLongMapper::new);
        install(Time.class,      TimeToLongMapper::new);
        install(Date.class,      DateToIntMapper::new);
        install(Timestamp.class, TimestampToIntMapper::new);
        install(Time.class,      TimeToIntMapper::new);

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
    }
    
    @Override
    protected String getDescription() {
        return "Holds all the type mappers that have been installed into the Speedment Platform. " + 
            "A Type Mapper is used to convert between database types and java types.";
    }

    @Override
    public void install(Class<?> databaseType, Supplier<TypeMapper<?, ?>> typeMapperConstructor) {
        final TypeMapper<?, ?> mapper = typeMapperConstructor.get();
        mappers.computeIfAbsent(databaseType.getName(), n -> new LinkedList<>()).add(mapper);
    }

    @Override
    public final Stream<TypeMapper<?, ?>> mapFrom(Class<?> databaseType) {
        return mappers.getOrDefault(databaseType.getName(), Collections.emptyList()).stream();
    }

    @Override
    public Stream<TypeMapper<?, ?>> stream() {
        return mappers.values().stream()
            .flatMap(List::stream);
    }

    @Override
    public Optional<TypeMapper<?, ?>> get(String absoluteClassName) {
        return stream()
            .filter(tm -> tm.getClass().getName().equals(absoluteClassName))
            .findAny();
    }

    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }
}