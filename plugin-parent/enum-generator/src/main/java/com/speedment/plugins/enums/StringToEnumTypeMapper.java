/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.plugins.enums;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.lazy.specialized.LazyClass;
import com.speedment.plugins.enums.internal.EnumGeneratorUtil;
import com.speedment.plugins.enums.internal.GeneratedEnumType;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.stream.Stream;

import static com.speedment.plugins.enums.internal.GeneratedEntityDecorator.FROM_DATABASE_METHOD;
import static com.speedment.plugins.enums.internal.GeneratedEntityDecorator.TO_DATABASE_METHOD;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the enum type
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class StringToEnumTypeMapper<T extends Enum<T>> implements TypeMapper<String, T> {

    private final LazyClass cachedEnum;
   
    private @Inject Injector injector;
    
    public StringToEnumTypeMapper() {
        cachedEnum = LazyClass.create();
    }

    @Override
    public String getLabel() {
        return "String to Enum";
    }

    @Override
    public Type getJavaType(Column column) {
        requireNonNull(injector, 
            StringToEnumTypeMapper.class.getSimpleName() + 
            ".getJavaType(Column) is not available if instantiated without injector."
        );
        
        return new GeneratedEnumType(
            EnumGeneratorUtil.enumNameOf(column, injector), 
            EnumGeneratorUtil.enumConstantsOf(column)
        );
    }

    @Override
    public Category getJavaTypeCategory(Column column) {
        return Category.ENUM;
    }

    @Override
    public T toJavaType(Column column, Class<?> entityType, String value) {
        if (value == null) {
            return null;
        } else {
            final Class<?> enumClass = cachedEnum.getOrCompute(
                () -> EnumGeneratorUtil.classesIn(entityType)

                    // Include only enum subclasses
                    .filter(Enum.class::isAssignableFrom)
                    
                    // Include only enums with the correct name
                    .filter(c -> c.getSimpleName().equalsIgnoreCase(
                        column.getJavaName().replace("_", "")
                    ))

                    // Include only enums with a method called fromDatabase()
                    // that takes the right parameters
                    .filter(c -> Stream.of(c.getMethods())
                        .filter(m -> m.getName().equals("fromDatabase"))
                        .anyMatch(m -> {
                            final Class<?>[] params = m.getParameterTypes();
                            return params.length == 1 
                                && params[0] == column.findDatabaseType();
                        })
                    )

                    // Return it as the enumClass or throw an exception.
                    .findAny()
                    .orElse(null)
            );

            final Method fromDatabase;
            try {
                fromDatabase = enumClass.getMethod(FROM_DATABASE_METHOD, String.class);
            } catch (final NoSuchMethodException ex) {
                throw new RuntimeException(
                    "Could not find generated '" + FROM_DATABASE_METHOD + 
                    "'-method in enum class '" + enumClass.getName() + "'.", ex
                );
            }

            try {
                @SuppressWarnings("unchecked")
                final T result = (T) fromDatabase.invoke(null, value);
                return result;
            } catch (final IllegalAccessException 
                         | IllegalArgumentException 
                         | InvocationTargetException ex) {
                throw new RuntimeException(
                    "Error executing '" + FROM_DATABASE_METHOD + 
                    "' in generated enum class '" + enumClass.getName() + "'.",
                    ex
                );
            }
        }
    }

    @Override
    public String toDatabaseType(T constant) {
        if (constant == null) {
            return null;
        } else {
            final Class<?> enumClass = constant.getClass();

            final Method toDatabase;
            try {
                toDatabase = enumClass.getMethod(TO_DATABASE_METHOD);

            } catch (final NoSuchMethodException ex) {
                throw new RuntimeException(
                    "Could not find generated '" + TO_DATABASE_METHOD + 
                    "'-method in enum class '" + 
                    constant.getClass().getName() + "'.", ex
                );
            }

            try {
                @SuppressWarnings("unchecked")
                final String result = (String) toDatabase.invoke(constant);
                return result;
            } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException(
                    "Error executing '" + TO_DATABASE_METHOD + 
                    "' in generated enum class '" + constant.getClass().getName() + "'.", ex
                );
            }
        }
    }
}