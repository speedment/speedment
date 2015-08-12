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
public enum OrderType implements EnumHelper<OrderType> {

    ASC  ("Asc"), 
    DESC ("Desc"), 
    NONE ("None");
    
    private static final Map<String, OrderType> NAME_MAP =
        EnumHelper.Hidden.buildMap(values());
    
    private final String name;

    OrderType(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Optional<OrderType> findByIgnoreCase(String name) {
        return Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }

    public static <C extends Node & Enableable> OrderType defaultFor(final C entity) {
        return Hidden.defaultFor(stream(), p -> false, entity, OrderTypeable.class, ASC);
    }

    public static Stream<OrderType> stream() {
        return Stream.of(values());
    }
}