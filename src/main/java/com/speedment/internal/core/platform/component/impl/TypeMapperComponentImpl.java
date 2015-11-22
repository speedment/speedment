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
import com.speedment.internal.core.config.mapper.identity.ArrayIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.BigDecimalIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.BlobIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.BooleanIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.ByteIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.ClobIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.DateIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.DoubleIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.FloatIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.IntegerIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.LongIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.NClobIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.ObjectIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.RefIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.RowIdIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.ShortIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.SQLXMLIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.StringIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.TimeIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.TimestampIdentityMapper;
import com.speedment.internal.core.config.mapper.identity.URLIdentityMapper;
import com.speedment.internal.core.config.mapper.string.StringToLocaleMapper;
import com.speedment.internal.core.config.mapper.string.TrueFalseStringToBooleanMapper;
import com.speedment.internal.core.config.mapper.string.YesNoStringToBooleanMapper;
import com.speedment.internal.core.config.mapper.time.DateToLongMapper;
import com.speedment.internal.core.config.mapper.time.TimeToLongMapper;
import com.speedment.internal.core.config.mapper.time.TimestampToLongMapper;

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
public final class TypeMapperComponentImpl extends Apache2AbstractComponent implements TypeMapperComponent {

    private final Map<String, TypeMapper<?, ?>> mappers;

    /**
     * Constructs the component.
     * 
     * @param speedment  the speedment instance
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
        
        // Special string mappers
        install(StringToLocaleMapper::new);
        install(TrueFalseStringToBooleanMapper::new);
        install(YesNoStringToBooleanMapper::new);
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