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
package com.speedment.internal.core.stream.builder.action.ints;

import com.speedment.internal.core.stream.builder.action.Action;
import com.speedment.internal.core.stream.builder.action.StandardBasicAction;
import static java.util.Objects.requireNonNull;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 *
 * @author pemi
 */
public final class IntFlatMapAction extends Action<IntStream, IntStream> {

    public IntFlatMapAction(IntFunction<? extends IntStream> mapper) {
        super(s -> s.flatMap(requireNonNull(mapper)), IntStream.class, StandardBasicAction.FLAT_MAP);
    }

}
