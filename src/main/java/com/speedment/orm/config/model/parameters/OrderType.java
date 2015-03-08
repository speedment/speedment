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
package com.speedment.orm.config.model.parameters;

import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.orm.config.model.aspects.Parent;
import com.speedment.orm.config.model.aspects.Child;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public enum OrderType implements EnumHelper<OrderType> {

    ASC("Asc"), DESC("Desc");
    static final Map<String, OrderType> NAME_MAP = EnumHelper.Hidden.buildMap(values());

    private OrderType(final String name) {
        this.name = name;
    }
    private final String name;

    @Override
    public String getName() {
        return name;
    }
//
//    public static Optional<OrderType> findByNameIgnoreCase(final String name) {
//        return Nameable.Hidden.findByNameIgnoreCase(NAME_MAP, name);
//    }
//
//    public static <T extends ConfigEntity<T, ?, ?>> Stream<OrderType> streamFor(final T entity) {
//        Objects.requireNonNull(entity);
//        if (entity instanceof IndexColumn) {
//            return stream();
//        }
//        return Stream.empty();
//    }
//
//    public static <T extends ConfigEntity<T, ?, ?>> OrderType defaultFor(final T entity) {
//        Objects.requireNonNull(entity);
//        return ASC;
//    }
//
//    public static Stream<OrderType> stream() {
//        return Stream.of(values());
//    }

    public static Optional<OrderType> findByIgnoreCase(String name) {
        return Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }
    
    public static OrderType defaultFor(final ConfigEntity entity) {
        return Hidden.defaultFor(stream(), p -> false, entity, OrderTypeable.class, ASC);
    }
    
    public static Stream<OrderType> streamFor(final Child entity) {
        return Hidden.streamFor(stream(), p -> false, entity, OrderTypeable.class);
    }

    public static Stream<OrderType> stream() {
        return Stream.of(values());
    }

}
