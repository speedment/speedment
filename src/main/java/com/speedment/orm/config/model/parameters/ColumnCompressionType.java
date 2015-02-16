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
public enum ColumnCompressionType implements Nameable<ColumnCompressionType> {

    INHERIT("Inherit from parent"), NONE("None"), DEDUPLICATION("Deduplication");
    static final Map<String, ColumnCompressionType> NAME_MAP = Nameable.Hidden.buildMap(values());

    private ColumnCompressionType(final String name) {
        this.name = name;
    }
    private final String name;

    @Override
    public String getName() {
        return name;
    }

    public static Optional<ColumnCompressionType> findByNameIgnoreCase(final String name) {
        return Nameable.Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }

    private static final Predicate<ColumnCompressionType> IS_INHERIT_PREDICATE = (f) -> f == INHERIT;

    public static <T extends ConfigEntity<T, ?, ?>> Stream<ColumnCompressionType> streamFor(final T entity) {
        Objects.requireNonNull(entity);
        try {
            final Class<? extends ConfigEntity<?, ?, ?>> parentClass = entity.getParentInterfaceMainClass().orElse(null);
            if (parentClass != null) {
                // Check if the parent can have FieldStorageTypes. 
                parentClass.getMethod("get" + ColumnCompressionType.class.getSimpleName());
                return stream();
            }
        } catch (NoSuchMethodException nsm) {
            // Ignore
        }
        return stream().filter(IS_INHERIT_PREDICATE.negate());
    }

    public static <T extends ConfigEntity<T, ?, ?>> ColumnCompressionType defaultFor(final T entity) {
        Objects.requireNonNull(entity);
        return streamFor(entity).filter(IS_INHERIT_PREDICATE).findAny().orElse(NONE);
    }

    public static Stream<ColumnCompressionType> stream() {
        return Stream.of(values());
    }

}
