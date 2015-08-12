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
package com.speedment.core.config.model.parameters;

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = "2.0")
public enum ColumnCompressionType implements Nameable<ColumnCompressionType> {
    
    INHERIT       ("Inherit from parent"), 
    NONE          ("None"), 
    DEDUPLICATION ("Deduplication");
    
    private static final Map<String, ColumnCompressionType> NAME_MAP = 
        EnumHelper.buildMap(values());
    
    private final String name;
    
    ColumnCompressionType(final String name) {
        this.name = name;
    }

    public static Optional<ColumnCompressionType> findByIgnoreCase(String name) {
        return EnumHelper.findByNameIgnoreCase(NAME_MAP, name);
    }
    
    public static <C extends Node & Enableable> ColumnCompressionType defaultFor(final C entity) {
        return EnumHelper.defaultFor(stream(), f -> f == INHERIT, entity, ColumnCompressionTypeable.class, NONE);
    }
    
    public static Stream<ColumnCompressionType> stream() {
        return Stream.of(values());
    }

    @Override
    public String getName() {
        return name;
    }
}