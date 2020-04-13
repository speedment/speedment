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
package com.speedment.runtime.core.util;

import com.speedment.common.function.OptionalBoolean;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * A utility class for converting optional types to their boxed equivalents.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 */
public final class OptionalUtil {

    private OptionalUtil() {}

    /**
     * If the specified object is an {@code Optional}, the inner value will be
     * returned. Otherwise, the object is returned directly.
     * 
     * @param potentiallyOptional  the object that might be an {@code Optional}
     * @return                     the object or its inner value
     */
    public static Object unwrap(Object potentiallyOptional) {
        if (potentiallyOptional instanceof Optional<?>) {
            return unwrap((Optional<?>) potentiallyOptional);
        } else {
            return potentiallyOptional;
        }
    }

    /**
     * Returns the inner value of the {@code Optional} unless the optional
     * is {@code null}, in which case {@code null} is returned.
     * 
     * @param <T>       the inner type
     * @param optional  the optional
     * @return          the inner value or {@code null}
     */
    public static <T> T unwrap(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<T> optional) {
        if ((Object) optional == null) { // (Object) cast suppress SonarCube warning
            return null;
        }
        return optional.orElse(null);
    }
    
    /**
     * Convert an optional value to a boxed one. If the inner value is
     * empty, {@code null} will be returned.
     * 
     * @param optional  the optional value (or null)
     * @return          the inner value or {@code null}
     */
    public static Integer unwrap(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") OptionalInt optional) {
        if (optional == null) {
            return null;
        } else {
            return optional.isPresent() ? optional.getAsInt() : null;
        }
    }
    
    /**
     * Convert an optional value to a boxed one. If the inner value is
     * empty, {@code null} will be returned.
     * 
     * @param optional  the optional value (or null)
     * @return          the inner value or {@code null}
     */
    public static Boolean unwrap(OptionalBoolean optional) {
        if (optional == null) {
            return null;
        } else {
            return optional.isPresent() ? optional.getAsBoolean() : null;
        }
    }
    
    /**
     * Convert an optional value to a boxed one. If the inner value is
     * empty, {@code null} will be returned.
     * 
     * @param optional  the optional value (or null)
     * @return          the inner value or {@code null}
     */
    public static Long unwrap(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") OptionalLong optional) {
        if (optional == null) {
            return null;
        } else {
            return optional.isPresent() ? optional.getAsLong() : null;
        }
    }
    
    /**
     * Convert an optional value to a boxed one. If the inner value is
     * empty, {@code null} will be returned.
     * 
     * @param optional  the optional value (or null)
     * @return          the inner value or {@code null}
     */
    public static Double unwrap(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") OptionalDouble optional) {
        if (optional == null) {
            return null;
        } else {
            return optional.isPresent() ? optional.getAsDouble() : null;
        }
    }

    /**
     * Wraps the specified nullable value in an {@code OptionalLong}.
     * 
     * @param l  the value
     * @return   the wrapped optional
     */
    public static OptionalLong ofNullable(Long l) {
        if (l == null) {
            return OptionalLong.empty();
        } else {
            return OptionalLong.of(l);
        }
    }

    /**
     * Wraps the specified nullable value in an {@code OptionalInteger}.
     * 
     * @param i  the value
     * @return   the wrapped optional
     */
    public static OptionalInt ofNullable(Integer i) {
        if (i == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(i);
        }
    }

    /**
     * Wraps the specified nullable value in an {@code OptionalDouble}.
     * 
     * @param d  the value
     * @return   the wrapped optional
     */
    public static OptionalDouble ofNullable(Double d) {
        if (d == null) {
            return OptionalDouble.empty();
        } else {
            return OptionalDouble.of(d);
        }
    }
    
    /**
     * Wraps the specified nullable value in an {@code OptionalBoolean}.
     * 
     * @param b  the value
     * @return   the wrapped optional
     */
    public static OptionalBoolean ofNullable(Boolean b) {
        if (b == null) {
            return OptionalBoolean.empty();
        } else {
            return OptionalBoolean.of(b);
        }
    }

    /**
     * Parses the specified value into an optional unless it is {@code null}, 
     * in which case an empty optional is returned.
     * 
     * @param value  the value (or null)
     * @return       the wrapped optional
     */
    public static OptionalLong parseLong(String value) {
        if (value == null) {
            return OptionalLong.empty();
        } else {
            return OptionalLong.of(Long.parseLong(value));
        }
    }

    /**
     * Parses the specified value into an optional unless it is {@code null}, 
     * in which case an empty optional is returned.
     * 
     * @param value  the value (or null)
     * @return       the wrapped optional
     */
    public static OptionalInt parseInt(String value) {
        if (value == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(Integer.parseInt(value));
        }
    }

    /**
     * Parses the specified value into an optional unless it is {@code null}, 
     * in which case an empty optional is returned.
     * 
     * @param value  the value (or null)
     * @return       the wrapped optional
     */
    public static OptionalDouble parseDouble(String value) {
        if (value == null) {
            return OptionalDouble.empty();
        } else {
            return OptionalDouble.of(Double.parseDouble(value));
        }
    }
    
    /**
     * Parses the specified value into an optional unless it is {@code null}, 
     * in which case an empty optional is returned.
     * 
     * @param value  the value (or null)
     * @return       the wrapped optional
     */
    public static OptionalBoolean parseBoolean(String value) {
        if (value == null) {
            return OptionalBoolean.empty();
        } else {
            return OptionalBoolean.of(Boolean.parseBoolean(value));
        }
    }

}
