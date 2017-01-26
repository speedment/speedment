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
package com.speedment.common.tuple;

import java.util.stream.Stream;

/**
 * {@inheritDoc}
 * 
 * This {@link Tuple} holds one non-null element.
 *
 * @author pemi
 * @param <T0> Type of 0:th argument
 */
public interface Tuple1<T0> extends Tuple {

    T0 get0();
    
    @Override
    default Stream<Object> stream() {
        return Stream.of(get0());
    }

    @Override
    default int length() {
        return 1;
    }

    @Override
    default Object get(int index) {
        if (index == 0) {
            return get0();
        } else {
            throw new IllegalArgumentException(String.format(
                "Index %d is outside bounds of tuple of length %s", index, length()
            ));
        }
    }
}