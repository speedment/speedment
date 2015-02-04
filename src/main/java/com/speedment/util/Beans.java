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

    public static <P, T> T withSeveral(final T thizz, final P[] restOfParameters, final Consumer<P> consumer) {
        Stream.of(restOfParameters).forEach(consumer::accept);
        return thizz;
    }

    public static <P1, P2, T> T with(final T thizz, final P1 firstParameter, final P2 secondParameter, final BiConsumer<P1, P2> biConsumer) {
        biConsumer.accept(firstParameter, secondParameter);
        return thizz;
    }

}
