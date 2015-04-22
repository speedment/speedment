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
package com.speedment.util.stream.builder.demo;

import com.speedment.util.stream.builder.ReferenceStreamBuilder;
import com.speedment.util.stream.builder.pipeline.BasePipeline;
import com.speedment.util.stream.builder.pipeline.IntPipeline;
import com.speedment.util.stream.builder.pipeline.ReferencePipeline;
import com.speedment.util.stream.builder.streamterminator.StreamTerminator;
import java.util.Collection;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <E> E type of the Collection
 */
public class CollectionsStreamTerminator<E> implements StreamTerminator {

    private final Collection<E> collection;

    public CollectionsStreamTerminator(Collection<E> Collection) {
        this.collection = Collection;
    }


    @Override
    public <T> long count(ReferencePipeline<T> pipeline) {
            if (pipeline.stream().allMatch(a -> !a.isCountModifying())) {
            return collection.size();
        }
        return StreamTerminator.super.count(pipeline);
    }

    
    @Override
    public <T> long count(IntPipeline pipeline) {
        // Todo: Check pipeline first
        if (pipeline.stream().allMatch(a -> !a.isCountModifying())) {
            return collection.size();
        }
        return StreamTerminator.super.count(pipeline);
    }


    public Stream<E> stream() {
        return new ReferenceStreamBuilder<>(new BasePipeline(collection::stream), this);
    }

    public static <E> Stream<E> streamOf(Collection<E> Collection) {
        return new CollectionsStreamTerminator<>(Collection).stream();
    }

}
