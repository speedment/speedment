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
package com.speedment.generator.translator.internal.component;

import static java.util.Objects.requireNonNull;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.generator.translator.exception.SpeedmentTranslatorException;
import com.speedment.runtime.config.trait.HasTypeMapper;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.bigdecimal.BigDecimalToDouble;
import com.speedment.runtime.typemapper.bytes.ByteZeroOneToBooleanMapper;
import com.speedment.runtime.typemapper.doubles.DoubleToFloatMapper;
import com.speedment.runtime.typemapper.doubles.PrimitiveDoubleToFloatMapper;
import com.speedment.runtime.typemapper.integer.DateIntToPrimitiveShortMapper;
import com.speedment.runtime.typemapper.integer.DateIntToShortMapper;
import com.speedment.runtime.typemapper.integer.IntegerToByteMapper;
import com.speedment.runtime.typemapper.integer.IntegerToShortMapper;
import com.speedment.runtime.typemapper.integer.IntegerZeroOneToBooleanMapper;
import com.speedment.runtime.typemapper.integer.PrimitiveIntegerToByteMapper;
import com.speedment.runtime.typemapper.integer.PrimitiveIntegerToShortMapper;
import com.speedment.runtime.typemapper.integer.PrimitiveIntegerZeroOneToBooleanMapper;
import com.speedment.runtime.typemapper.largeobject.BlobToBigIntegerMapper;
import com.speedment.runtime.typemapper.largeobject.BlobToByteArrayMapper;
import com.speedment.runtime.typemapper.largeobject.ClobToStringMapper;
import com.speedment.runtime.typemapper.longs.LongToByteMapper;
import com.speedment.runtime.typemapper.longs.LongToIntegerMapper;
import com.speedment.runtime.typemapper.longs.LongToShortMapper;
import com.speedment.runtime.typemapper.longs.PrimitiveLongToByteMapper;
import com.speedment.runtime.typemapper.longs.PrimitiveLongToIntegerMapper;
import com.speedment.runtime.typemapper.longs.PrimitiveLongToShortMapper;
import com.speedment.runtime.typemapper.other.BinaryToBigIntegerMapper;
import com.speedment.runtime.typemapper.other.BinaryToByteArrayMapper;
import com.speedment.runtime.typemapper.other.BinaryToUuidMapper;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;
import com.speedment.runtime.typemapper.shorts.PrimitiveShortToByteMapper;
import com.speedment.runtime.typemapper.shorts.ShortToByteMapper;
import com.speedment.runtime.typemapper.string.StringToBigDecimalMapper;
import com.speedment.runtime.typemapper.string.StringToBigIntegerMapper;
import com.speedment.runtime.typemapper.string.StringToLocaleMapper;
import com.speedment.runtime.typemapper.string.TrueFalseStringToBooleanMapper;
import com.speedment.runtime.typemapper.string.YNStringToBooleanMapper;
import com.speedment.runtime.typemapper.string.YesNoStringToBooleanMapper;
import com.speedment.runtime.typemapper.time.DateToIntMapper;
import com.speedment.runtime.typemapper.time.DateToLocalDateMapper;
import com.speedment.runtime.typemapper.time.DateToLongMapper;
import com.speedment.runtime.typemapper.time.DateToPrimitiveIntMapper;
import com.speedment.runtime.typemapper.time.DateToPrimitiveLongMapper;
import com.speedment.runtime.typemapper.time.IntEpochDaysToLocalDateMapper;
import com.speedment.runtime.typemapper.time.ShortEpochDaysToLocalDateMapper;
import com.speedment.runtime.typemapper.time.TimeToIntMapper;
import com.speedment.runtime.typemapper.time.TimeToLocalTimeMapper;
import com.speedment.runtime.typemapper.time.TimeToLongMapper;
import com.speedment.runtime.typemapper.time.TimeToPrimitiveIntMapper;
import com.speedment.runtime.typemapper.time.TimeToPrimitiveLongMapper;
import com.speedment.runtime.typemapper.time.TimestampToIntMapper;
import com.speedment.runtime.typemapper.time.TimestampToLocalDateTimeMapper;
import com.speedment.runtime.typemapper.time.TimestampToLongMapper;
import com.speedment.runtime.typemapper.time.TimestampToPrimitiveIntMapper;
import com.speedment.runtime.typemapper.time.TimestampToPrimitiveLongMapper;

import java.math.BigDecimal;
import java.sql.Blob;
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
    private Injector injector;

    /**
     * Constructs the component.
     */
    public TypeMapperComponentImpl() {
        this.mappers = new ConcurrentHashMap<>();

        // Special time mappers
        install(Date.class,      DateToLocalDateMapper::new);
        install(Date.class,      DateToLongMapper::new);
        install(Date.class,      DateToIntMapper::new);
        install(Date.class,      DateToPrimitiveIntMapper::new);
        install(Date.class,      DateToPrimitiveLongMapper::new);
        install(Timestamp.class, TimestampToLongMapper::new);
        install(Timestamp.class, TimestampToIntMapper::new);
        install(Timestamp.class, TimestampToPrimitiveLongMapper::new);
        install(Timestamp.class, TimestampToPrimitiveIntMapper::new);
        install(Timestamp.class, TimestampToLocalDateTimeMapper::new);
        install(Time.class,      TimeToLongMapper::new);
        install(Time.class,      TimeToIntMapper::new);
        install(Time.class,      TimeToPrimitiveLongMapper::new);
        install(Time.class,      TimeToPrimitiveIntMapper::new);
        install(Time.class,      TimeToLocalTimeMapper::new);
        install(Short.class,     ShortEpochDaysToLocalDateMapper::new);
        install(Integer.class,   IntEpochDaysToLocalDateMapper::new);

        // Special string mappers
        install(String.class, StringToLocaleMapper::new);
        install(String.class, TrueFalseStringToBooleanMapper::new);
        install(String.class, YesNoStringToBooleanMapper::new);
        install(String.class, YNStringToBooleanMapper::new);
        install(String.class, StringToBigIntegerMapper::new);
        install(String.class, StringToBigDecimalMapper::new);

        // Special BigDecimal object mappers
        install(BigDecimal.class, BigDecimalToDouble::new);

        // Special Large object mappers
        install(Clob.class, ClobToStringMapper::new);
        install(Blob.class, BlobToByteArrayMapper::new);
        install(Blob.class, BlobToBigIntegerMapper::new);

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

        // Special Byte mappers
        install(Byte.class, ByteZeroOneToBooleanMapper::new);

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
        install(Object.class, BinaryToByteArrayMapper::new);
        install(Object.class, BinaryToBigIntegerMapper::new);
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(Injector injector) {
        this.injector = requireNonNull(injector);
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
                            .loadClass(name)
                            .getConstructor()
                            .newInstance();
                    return mapper;
                } catch (final ReflectiveOperationException ex) {
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
