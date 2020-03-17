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
package com.speedment.runtime.core.internal.stream.builder.action;

import com.speedment.runtime.core.stream.action.BasicAction;
import com.speedment.runtime.core.stream.action.Property;
import com.speedment.runtime.core.stream.action.Statement;

import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNullElements;
import static com.speedment.runtime.core.stream.action.Property.*;
import static com.speedment.runtime.core.stream.action.Verb.PRESERVE;
import static com.speedment.runtime.core.stream.action.Verb.SET;

/**
 *
 * @author pemi
 */
public enum StandardBasicAction implements BasicAction {

    FILTER(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE)
    ),
    DISTINCT(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE)
    ),
    FLAT_MAP(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, STREAM_TYPE)
    ),
    FLAT_MAP_TO(
        Statement.of(PRESERVE, ORDER)
    ),
    LIMIT(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE),
        Statement.of(SET, Property.FINITE)
    ),
    MAP(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, SIZE),
        Statement.of(PRESERVE, STREAM_TYPE)
    ),
    MAP_TO_SAME(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, SIZE),
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE)
    ),
    MAP_TO(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, SIZE)
    ),
    PEEK(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE),
        Statement.of(PRESERVE, SIZE),
        Statement.of(SET, SIDE_EFFECT)
    ),
    SKIP(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE)
    ),
    SORTED(
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE),
        Statement.of(PRESERVE, SIZE),
        Statement.of(SET, com.speedment.runtime.core.stream.action.Property.SORTED)
    ),
    BOXED(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, SIZE),
        Statement.of(SET, STREAM_TYPE)
    ),
    AS(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, SIZE),
        Statement.of(SET, STREAM_TYPE)
    ),
    TAKE_WHILE(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE)
    ),
    DROP_WHILE(
        Statement.of(PRESERVE, ORDER),
        Statement.of(PRESERVE, TYPE),
        Statement.of(PRESERVE, STREAM_TYPE)
    );

    private final Statement[] statements;

    StandardBasicAction(Statement... statements) {
        this.statements = requireNonNullElements(statements);
    }

    @Override
    public Stream<Statement> statements() {
        return Stream.of(statements);
    }

}
