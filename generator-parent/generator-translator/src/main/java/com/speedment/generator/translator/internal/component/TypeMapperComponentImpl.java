/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.generator.translator.exception.SpeedmentTranslatorException;
import com.speedment.runtime.config.trait.HasTypeMapper;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.bigdecimal.BigDecimalToDouble;
import com.speedment.runtime.typemapper.doubles.DoubleToFloatMapper;
import com.speedment.runtime.typemapper.doubles.PrimitiveDoubleToFloatMapper;
import com.speedment.runtime.typemapper.integer.*;
import com.speedment.runtime.typemapper.largeobject.BlobToByteArrayMapper;
import com.speedment.runtime.typemapper.largeobject.ClobToStringMapper;
import com.speedment.runtime.typemapper.longs.*;
import com.speedment.runtime.typemapper.other.BinaryToUuidMapper;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;
import com.speedment.runtime.typemapper.shorts.PrimitiveShortToByteMapper;
import com.speedment.runtime.typemapper.shorts.ShortToByteMapper;
import com.speedment.runtime.typemapper.string.StringToLocaleMapper;
import com.speedment.runtime.typemapper.string.TrueFalseStringToBooleanMapper;
import com.speedment.runtime.typemapper.string.YesNoStringToBooleanMapper;
import com.speedment.runtime.typemapper.time.*;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
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
        install(Blob.class, BlobToByteArrayMapper::new);
        
        // Special Long mappers
        install(Long.class, LongToIntegerMapper::new);
        install(Long.class, LongToShortMapper::new);
        install(Long.class, LongToByteMapper::new);
        install(Long.class, PrimitiveLongToIntegerMapper::new);
        install(Long.class, PrimitiveLongToShortMapper::new);
        install(Long.class, PrimitiveLongToByteMapper::new);
        
        // Special Integer mappers
        install(Integer.class, IntegerZeroOneToBooleanMapper::new);
        install(Integer.class, IntegerToShortMapper::new);
        install(Integer.class, IntegerToByteMapper::new);
        install(Integer.class, PrimitiveIntegerZeroOneToBooleanMapper::new);
        install(Integer.class, PrimitiveIntegerToShortMapper::new);
        install(Integer.class, PrimitiveIntegerToByteMapper::new);
        install(Integer.class, DateIntToShortMapper::new);
        install(Integer.class, DateIntToPrimitiveShortMapper::new);

        // Special Short mappers
        install(Short.class, ShortToByteMapper::new);
        install(Short.class, PrimitiveShortToByteMapper::new);
        
        // Special Double mappers
        install(Double.class, DoubleToFloatMapper::new);
        install(Double.class, PrimitiveDoubleToFloatMapper::new);
        
        // Primitive mappers
        install(Byte.class,      PrimitiveTypeMapper<Byte>::new);
        install(Short.class,     PrimitiveTypeMapper<Short>::new);
        install(Integer.class,   PrimitiveTypeMapper<Integer>::new);
        install(Long.class,      PrimitiveTypeMapper<Long>::new);
        install(Float.class,     PrimitiveTypeMapper<Float>::new);
        install(Double.class,    PrimitiveTypeMapper<Double>::new);
        install(Boolean.class,   PrimitiveTypeMapper<Boolean>::new);
        install(Character.class, PrimitiveTypeMapper<Character>::new);

        // Others
        install(Object.class, BinaryToUuidMapper::new);
    }

    @Override
    public void install(
            Class<?> databaseType, 
            Supplier<TypeMapper<?, ?>> typeMapperConstructor) {
        
        mappers.computeIfAbsent(
            databaseType.getName(), 
            n -> new LinkedList<>()
        ).add(typeMapperConstructor);
    }

    @Override
    public final Stream<TypeMapper<?, ?>> mapFrom(Class<?> databaseType) {
        return mappers.getOrDefault(
                databaseType.getName(), 
                Collections.emptyList()
            )
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
    public TypeMapper<?, ?> get(HasTypeMapper column) {
        return injector.inject(
            column.getTypeMapper().map(name -> {
                try {
                    @SuppressWarnings("unchecked")
                    final TypeMapper<Object, Object> mapper = 
                        (TypeMapper<Object, Object>) injector.classLoader()
                            .loadClass(name).newInstance();
                    return mapper;
                } catch (final ClassNotFoundException 
                             | IllegalAccessException 
                             | InstantiationException ex) {
                    
                    throw new SpeedmentTranslatorException(
                        "Could not instantiate TypeMapper: '" + name + "'.", ex
                    );
                }
            }).orElseGet(TypeMapper::identity)
        );
    }

    @Override
    public <DB_TYPE, JAVA_TYPE> Optional<Class<DB_TYPE>> findDatabaseTypeOf(
            TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper) {
        
        final Class<?> needle = typeMapper.getClass();
        return mappers.entrySet().stream()
            .filter(e -> e.getValue().stream()
                .map(Supplier::get)
                .map(TypeMapper::getClass)
                .anyMatch(needle::equals)
            )
            .map(Map.Entry::getKey)
            .findAny()
            .map(key -> {
                try {
                    @SuppressWarnings("unchecked")
                    final Class<DB_TYPE> result = (Class<DB_TYPE>) 
                        injector.classLoader().loadClass(key);
                    
                    return result;
                } catch (final ClassNotFoundException ex) {
                    throw new SpeedmentTranslatorException(
                        "Could not find installed type mapper key '" + key + 
                        "' on class path.", ex
                    );
                } catch (final ClassCastException ex) {
                    throw new SpeedmentTranslatorException(
                        "An error occured when an installed type mapper key " + 
                        "was thrown to the expected type '" + key + "'.", ex
                    );
                }
            });
    }
}