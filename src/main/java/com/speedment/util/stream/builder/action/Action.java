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
package com.speedment.util.stream.builder.action;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.BaseStream;

/**
 *
 * @author pemi
 * @param <T>
 * @param <R>
 */
public class Action<T extends BaseStream<?, T>, R extends BaseStream<?, R>> implements Supplier<Function<T, R>> {

    private final Function<T, R> mapper;
    private final Class<? extends BaseStream> resultStreamClass;

    public Action(Function<T, R> mapper, Class<? extends BaseStream> resultStreamClass) {
        this.mapper = mapper;
        this.resultStreamClass = resultStreamClass;
    }

    @Override
    public Function<T, R> get() {
        return mapper;
    }

    public Class<? extends BaseStream> resultStreamClass() {
        return resultStreamClass;
    }

    public boolean isCountModifying() {
        return true;
    }

    public boolean isOrderModifying() {
        return true;
    }

    @Override
    public String toString() {
        final String className = getClass().getSimpleName();
        final int index = className.lastIndexOf(Action.class.getSimpleName());
        if (index > 0) {
            return className.substring(0, index);
        }
        return className;
    }

}
