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
package com.speedment.runtime.core.component.sql.override;

import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Consumer;

/**
 *
 * @author Per Minborg
 */
public interface ReferenceStreamTerminatorOverrides {

    /**
     * Returns if the elements were consumed by the override.
     *
     * @param <T> Stream type
     * @param pipeline to use
     * @param action consumer
     * @return if the elements were consumed by the override
     */
    default <T> boolean forEach(ReferencePipeline<T> pipeline, Consumer<? super T> action) {
        return false;
    }

    default <T> boolean forEachOrdered(ReferencePipeline<T> pipeline, Consumer<? super T> action) {
        return false;
    }

    /**
     * Returns the number of elements in the stream or {@code Optional.empty()}.
     *
     * @param <T> Stream type
     * @param pipeline to use
     * @return the number of elements in the stream or {@code Optional.empty()}
     */
    default <T> OptionalLong count(ReferencePipeline<T> pipeline) {
        return OptionalLong.empty();
    }

}
