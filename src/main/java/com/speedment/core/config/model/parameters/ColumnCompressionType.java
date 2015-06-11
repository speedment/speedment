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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.core.config.model.parameters;

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.ConfigEntity;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public enum ColumnCompressionType implements EnumHelper<ColumnCompressionType> {

    INHERIT("Inherit from parent"), NONE("None"), DEDUPLICATION("Deduplication");
    static final Map<String, ColumnCompressionType> NAME_MAP = EnumHelper.Hidden.buildMap(values());

    private ColumnCompressionType(final String name) {
        this.name = name;
    }
    private final String name;
    
    public static Optional<ColumnCompressionType> findByIgnoreCase(String name) {
        return Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }
    
    public static ColumnCompressionType defaultFor(final ConfigEntity entity) {
        return Hidden.defaultFor(stream(), f -> f == INHERIT, entity, ColumnCompressionTypeable.class, NONE);
    }
    
    public static Stream<ColumnCompressionType> stream() {
        return Stream.of(values());
    }

    @Override
    public String getName() {
        return name;
    }
}
