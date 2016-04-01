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
import com.speedment.component.TypeMapperComponent;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.config.db.mapper.identity.ArrayIdentityMapper;
import com.speedment.config.db.mapper.identity.BigDecimalIdentityMapper;
import com.speedment.config.db.mapper.identity.BlobIdentityMapper;
import com.speedment.config.db.mapper.identity.BooleanIdentityMapper;
import com.speedment.config.db.mapper.identity.ByteIdentityMapper;
import com.speedment.config.db.mapper.identity.ClobIdentityMapper;
import com.speedment.config.db.mapper.identity.DateIdentityMapper;
import com.speedment.config.db.mapper.identity.DoubleIdentityMapper;
import com.speedment.config.db.mapper.identity.FloatIdentityMapper;
import com.speedment.config.db.mapper.identity.IntegerIdentityMapper;
import com.speedment.config.db.mapper.identity.LongIdentityMapper;
import com.speedment.config.db.mapper.identity.NClobIdentityMapper;
import com.speedment.config.db.mapper.identity.ObjectIdentityMapper;
import com.speedment.config.db.mapper.identity.RefIdentityMapper;
import com.speedment.config.db.mapper.identity.RowIdIdentityMapper;
import com.speedment.config.db.mapper.identity.ShortIdentityMapper;
import com.speedment.config.db.mapper.identity.SQLXMLIdentityMapper;
import com.speedment.config.db.mapper.identity.StringIdentityMapper;
import com.speedment.config.db.mapper.identity.TimeIdentityMapper;
import com.speedment.config.db.mapper.identity.TimestampIdentityMapper;
import com.speedment.config.db.mapper.identity.URLIdentityMapper;
import com.speedment.config.db.mapper.identity.UUIDIdentityMapper;
import com.speedment.config.db.mapper.string.StringToLocaleMapper;
import com.speedment.config.db.mapper.string.TrueFalseStringToBooleanMapper;
import com.speedment.config.db.mapper.string.YesNoStringToBooleanMapper;
import com.speedment.config.db.mapper.time.DateToIntMapper;
import com.speedment.config.db.mapper.time.DateToLongMapper;
import com.speedment.config.db.mapper.time.TimeToIntMapper;
import com.speedment.config.db.mapper.time.TimeToLongMapper;
import com.speedment.config.db.mapper.time.TimestampToIntMapper;
import com.speedment.config.db.mapper.time.TimestampToLongMapper;
import com.speedment.license.Software;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 2.2
 */
public final class TypeMapperComponentImpl extends InternalOpenSourceComponent implements TypeMapperComponent {

    private final Map<String, TypeMapper<?, ?>> mappers;

    /**
     * Constructs the component.
     *
     * @param speedment the speedment instance
     */
    public TypeMapperComponentImpl(Speedment speedment) {
        super(speedment);
        this.mappers = new ConcurrentHashMap<>();

        // Identity mappers
        install(ArrayIdentityMapper::new);
        install(BigDecimalIdentityMapper::new);
        install(BlobIdentityMapper::new);
        install(BooleanIdentityMapper::new);
        install(ByteIdentityMapper::new);
        install(ClobIdentityMapper::new);
        install(DateIdentityMapper::new);
        install(DoubleIdentityMapper::new);
        install(FloatIdentityMapper::new);
        install(IntegerIdentityMapper::new);
        install(LongIdentityMapper::new);
        install(NClobIdentityMapper::new);
        install(ObjectIdentityMapper::new);
        install(RefIdentityMapper::new);
        install(RowIdIdentityMapper::new);
        install(ShortIdentityMapper::new);
        install(SQLXMLIdentityMapper::new);
        install(StringIdentityMapper::new);
        install(TimeIdentityMapper::new);
        install(TimestampIdentityMapper::new);
        install(URLIdentityMapper::new);

        // Special time mappers
        install(DateToLongMapper::new);
        install(TimestampToLongMapper::new);
        install(TimeToLongMapper::new);
        install(DateToIntMapper::new);
        install(TimestampToIntMapper::new);
        install(TimeToIntMapper::new);

        // Special string mappers
        install(StringToLocaleMapper::new);
        install(TrueFalseStringToBooleanMapper::new);
        install(YesNoStringToBooleanMapper::new);

        // Other mappers
        install(UUIDIdentityMapper::new);
    }

    @Override
    public void install(Supplier<TypeMapper<?, ?>> typeMapperConstructor) {
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

    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }

    @Override
    public TypeMapperComponent defaultCopy(Speedment speedment) {
        return new TypeMapperComponentImpl(speedment);
    }
    
}
