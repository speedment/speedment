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
package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultCollectSupplierAccumulatorCombinerTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface CollectSupplierAccumulatorCombinerTerminator<ENTITY> extends ReferenceTerminator {

    <T, R> R apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline,
        Supplier<R> supplier,
        BiConsumer<R, ? super T> accumulator,
        BiConsumer<R, R> combiner
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> CollectSupplierAccumulatorCombinerTerminator<ENTITY> defaultTerminator() {
        return (CollectSupplierAccumulatorCombinerTerminator<ENTITY>) DEFAULT;
    }

}
