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
package com.speedment.internal.util;

import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * Utility class with methods for managing generated beans.
 *
 * @author pemi
 */
public final class Beans {

    public static String beanPropertyName(Method method) {
        requireNonNull(method);
        final String getterName = method.getName();
        final int startIndex = (getterName.startsWith("is")) ? 2 : 3;
        return getterName.substring(startIndex, startIndex + 1).toLowerCase() + getterName.substring(startIndex + 1);
    }

    public static Optional<String> getterBeanPropertyNameAndValue(Method method, Object invocationTarget) {
        requireNonNull(method);
        //invocationTarget nullable
        Object value;
        try {
            value = method.invoke(invocationTarget);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            return Optional.empty();
        }
        if (value instanceof Optional) {
            final Optional<?> optional = (Optional) value;
            if (optional.isPresent()) {
                value = optional.get();
            } else {
                return Optional.empty();
            }
        }

        final String quote;
        if (value instanceof String /*|| value instanceof Enum*/) {
            quote = "\"";
        } else {
            quote = "";
        }

        if (value instanceof Class) {
            value = ((Class) value).getName() + ".class";
        }

        if (value instanceof Enum) {
            value = value.getClass().getSimpleName() + "." + ((Enum) value).name();
        }

        return Optional.of(beanPropertyName(method) + " = " + quote + String.valueOf(value) + quote + ";");
    }

    /**
     * Utility classes should not be instantiated.
     */
    private Beans() {
        instanceNotAllowed(getClass());
    }
}
