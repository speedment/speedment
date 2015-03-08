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
package com.speedment.orm.config.model.parameters;

import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.orm.config.model.aspects.Childable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public enum FieldStorageType implements EnumHelper<FieldStorageType> {

    INHERIT("Inherit from parent"), WRAPPER("Wrapper class"), PRIMITIVE("Primitive class");
    static final Map<String, FieldStorageType> NAME_MAP = EnumHelper.Hidden.buildMap(values());

    private FieldStorageType(final String name) {
        this.name = name;
    }
    
    private final String name;

    @Override
    public String getName() {
        return name;
    }
    
    public static Optional<FieldStorageType> findByIgnoreCase(String name) {
        return Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }
    
    public static FieldStorageType defaultFor(final ConfigEntity entity) {
        return Hidden.defaultFor(stream(), f -> f == INHERIT, entity, WRAPPER);
    }

    public static Stream<FieldStorageType> stream() {
        return Stream.of(values());
    }
}