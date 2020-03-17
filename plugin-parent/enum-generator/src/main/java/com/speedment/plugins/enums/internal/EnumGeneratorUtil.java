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
package com.speedment.plugins.enums.internal;

import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Utility class to assist the type mappers of this plugin.
 *
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class EnumGeneratorUtil {

    private EnumGeneratorUtil() {}

    /**
     * Returns the full name of the enum that will be generated for 
     * the specified column.
     * 
     * @param column    the column that should be implemented as an enum
     * @param injector  the injector used in the platform
     * @return          full name for the enum
     */
    public static String enumNameOf(Column column, Injector injector) {
        final TranslatorSupport<Table> support = new TranslatorSupport<>(injector, column.getParentOrThrow());
        final String shortName = support.namer().javaTypeName(column.getJavaName());

        return support.generatedEntityType().getTypeName() + "." + shortName;
    }
    
    /**
     * Returns a list of all the enum constants in a particular column.
     * The list is created each time this method is called and is therefore
     * safe to edit without affecting the column.
     * <p>
     * If no enum constants was specified in the column, an exception is
     * thrown.
     * 
     * @param column  the column to retrieve the constants from
     * @return        list of the constants
     */
    public static List<String> enumConstantsOf(Column column) {
        final Optional<String> ec = column.getEnumConstants();
        final String[] enumConstants = ec
                .orElseThrow(() -> new NoSuchElementException(
                        "Column '" + column.getId() +
                                "' in table '" + column.getParentOrThrow().getId() +
                                "' was marked as an enum but no enum constants was specified."
                ))
                .split(",");

        return Stream.of(enumConstants)
        .sorted()
        .collect(toList());
    }

    /**
     * Returns a stream of classes located inside the specified class. If the
     * specified class is {@code null}, then an empty stream is returned.
     *
     * @param entityClass  the class to look in (can be {code null})
     * @return             stream of classes
     */
    public static Stream<Class<?>> classesIn(Class<?> entityClass) {
        if (entityClass == null) {
            return Stream.empty();
        } else {
            return Stream.concat(Stream.concat(Stream.of(entityClass.getDeclaredClasses()),
                Stream.of(entityClass.getSuperclass())
                    .flatMap(EnumGeneratorUtil::classesIn)
            ), Stream.of(entityClass.getInterfaces())
                .flatMap(EnumGeneratorUtil::classesIn)
            );
        }
    }

}