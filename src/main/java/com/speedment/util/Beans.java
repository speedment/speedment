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
package com.speedment.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Beans {

    private Beans() {
    }

    public static <P, T> T with(final T thizz, final P item, final Consumer<P> consumer) {
        consumer.accept(item);
        return thizz;
    }
    
    public static <P, T> T run(final T thizz, final Runnable runnable) {
        runnable.run();
        return thizz;
    }

    public static <P, T> T withSeveral(final T thizz, final P[] restOfParameters, final Consumer<P> consumer) {
        Stream.of(restOfParameters).forEach(consumer::accept);
        return thizz;
    }

    public static <P1, P2, T> T with(final T thizz, final P1 firstParameter, final P2 secondParameter, final BiConsumer<P1, P2> biConsumer) {
        biConsumer.accept(firstParameter, secondParameter);
        return thizz;
    }

    public static String beanPropertyName(String getterName) {
        final int startIndex = (getterName.startsWith("is")) ? 2 : 3;
        return getterName.substring(startIndex, startIndex + 1).toLowerCase() + getterName.substring(startIndex + 1);
    }

    public static String beanPropertyName(Method m) {
        return beanPropertyName(m.getName());
    }

    public static Optional<String> getterBeanPropertyNameAndValue(Method m, Object invocationTarget) {

        Object value;
        try {
            value = m.invoke(invocationTarget);
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
        if (value instanceof String) {
            quote = "\"";
        } else {
            quote = "";
        }
        
        if (value instanceof Class) {
            value = ((Class) value).getName() + ".class";
        }
        
        return Optional.of(beanPropertyName(m.getName()) + " = " + quote + String.valueOf(value) + quote + ";");
    }

}
