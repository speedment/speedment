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
package com.speedment.plugins.enums;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.plugins.enums.internal.EnumGeneratorUtil;
import com.speedment.plugins.enums.internal.GeneratedEnumType;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static com.speedment.plugins.enums.internal.EnumGeneratorUtil.classesIn;
import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since 3.0.13
 */
public final class IntegerToEnumTypeMapper<T extends Enum<T>>
    implements TypeMapper<Integer, T> {

    private final AtomicReference<T[]> cachedConstants;

    @Inject public Injector injector;

    public IntegerToEnumTypeMapper() {
        cachedConstants = new AtomicReference<>();
    }

    @Override
    public String getLabel() {
        return "Integer to Enum";
    }

    @Override
    public boolean isToolApplicable() {
        return false;
    }

    @Override
    public Type getJavaType(Column column) {
        requireNonNull(injector,
            IntegerToEnumTypeMapper.class.getSimpleName() +
                ".getJavaType(Column) is not available if instantiated " +
                "without injector."
        );

        return new GeneratedEnumType(
            EnumGeneratorUtil.enumNameOf(column, injector),
            EnumGeneratorUtil.enumConstantsOf(column)
        );
    }

    @Override
    public TypeMapper.Category getJavaTypeCategory(Column column) {
        return Category.ENUM;
    }

    @Override
    public T toJavaType(Column column, Class<?> entityType, Integer value) {
        if (value == null) {
            return null;
        } else {
            if (cachedConstants.get() == null) {
                synchronized (cachedConstants) {
                    if (cachedConstants.get() == null) {
                        cachedConstants.set(constants(column, entityType));
                    }
                }

            }
            return cachedConstants.get()[value];
        }
    }

    private T[] constants(Column column, Class<?> entityType) {
        final Class<?> enumClass = classesIn(entityType)
            // Include only enum subclasses
            .filter(Enum.class::isAssignableFrom)

            // Include only enums with the correct name
            .filter(c -> c.getSimpleName().equalsIgnoreCase(
                column.getJavaName().replace("_", "")
            ))

            // Return it as the enumClass or throw an exception.
            .findAny()
            .orElseThrow(() -> new NoSuchElementException("Unable to find Enum class because entity " + entityType.getSimpleName() + " has no enum value for column " + column.getId()));

        final Method values;
        try {
            values = enumClass.getMethod("values");
        } catch (final NoSuchMethodException ex) {
            throw new IllegalArgumentException("Could not find 'values()'-method in enum class '" + enumClass.getName() + "'.", ex);
        }

        try {
            @SuppressWarnings("unchecked") final T[] result = (T[]) values.invoke(null);
            return result;
        } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IllegalArgumentException("Error executing 'values()' in generated enum class '" + enumClass.getName() + "'.", ex);
        }
    }

    @Override
    public Integer toDatabaseType(T constant) {
        if (constant == null) {
            return null;
        } else {
            final Class<?> enumClass = constant.getClass();

            final Method ordinal;
            try {
                ordinal = enumClass.getMethod("ordinal");
            } catch (final NoSuchMethodException ex) {
                throw new IllegalArgumentException("Could not find generated 'ordinal()'-method in enum class '" + constant.getClass().getName() + "'.", ex);
            }

            try {
                @SuppressWarnings("unchecked") final Integer result = (Integer) ordinal.invoke(constant);
                return result;
            } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new IllegalArgumentException("Error executing 'ordinal()' in generated enum class '" + constant.getClass().getName() + "'.", ex);
            }
        }
    }
}
