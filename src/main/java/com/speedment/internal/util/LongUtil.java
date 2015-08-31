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
import java.math.BigDecimal;
import java.math.BigInteger;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author pemi
 */
public class LongUtil {

    @SuppressWarnings("unchecked")
    public static <T extends Number> T cast(Long l, Class<T> targetClass) {
        requireNonNull(targetClass);
        // l is nullable
        if (l == null) {
            return null;
        }
        if (targetClass == Byte.class) {
            checkRange(l, Byte.MIN_VALUE, Byte.MAX_VALUE);
            return (T) (Byte) l.byteValue();
        } else if (targetClass == Short.class) {
            checkRange(l, Short.MIN_VALUE, Short.MAX_VALUE);
            return (T) (Short) l.shortValue();
        } else if (targetClass == Integer.class) {
            checkRange(l, Integer.MIN_VALUE, Integer.MAX_VALUE);
            return (T) (Integer) l.intValue();
        } else if (targetClass == Long.class) {
            return (T) l;
        } else if (targetClass == Float.class) {
            return (T) (Float) l.floatValue();
        } else if (targetClass == Double.class) {
            return (T) (Double) l.doubleValue();
        } else if (targetClass == BigInteger.class) {
            return (T) BigInteger.valueOf(l);
        } else if (targetClass == BigDecimal.class) {
            return (T) BigDecimal.valueOf(l);
        } else if (targetClass == AtomicInteger.class) {
            checkRange(l, Integer.MIN_VALUE, Integer.MAX_VALUE);
            return (T) new AtomicInteger(l.intValue());
        } else if (targetClass == AtomicLong.class) {
            return (T) new AtomicLong(l);
        }
        throw new IllegalArgumentException("Unable to cast Long to " + targetClass);
    }

    public static void checkRange(Long value, long min, long max) {
        // value is nullable
        if (value == null) {
            return; // No check
        }
        if (value > max || value < min) {
            throw new IllegalArgumentException("The value " + value + " is out of the specified range [" + min + ", " + max + "]");
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private LongUtil() { instanceNotAllowed(getClass()); }
}