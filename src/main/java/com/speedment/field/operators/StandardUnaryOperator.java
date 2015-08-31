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
package com.speedment.field.operators;

import com.speedment.annotation.Api;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 */
@Api(version = "2.1")
public enum StandardUnaryOperator implements UnaryOperator {

    IS_NULL(Objects::isNull),
    IS_NOT_NULL(Objects::nonNull);

    private final Predicate<Object> predicate;

    StandardUnaryOperator(Predicate<Object> predicate) {
        this.predicate = Objects.requireNonNull(predicate);
    }

    @Override
    public Predicate<Object> getUnaryFilter() {
        return predicate;
    }
}