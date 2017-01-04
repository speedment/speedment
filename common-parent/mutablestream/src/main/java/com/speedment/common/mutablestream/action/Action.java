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
package com.speedment.common.mutablestream.action;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.HasPrevious;
import com.speedment.common.mutablestream.internal.util.CastUtil;
import java.util.Optional;
import java.util.stream.BaseStream;

/**
 *
 * @param <T>  the ingoing type
 * @param <TS> the ingoing stream type
 * @param <R>  the outgoing type
 * @param <RS> the outgoing stream type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface Action<
    T, TS extends BaseStream<T, TS>, 
    R, RS extends BaseStream<R, RS>
> extends HasPrevious<T, TS>, HasNext<R, RS> {

    @SuppressWarnings("unchecked")
    default Optional<FilterAction<T>> ifFilter() {
        return CastUtil.castIf(this, FilterAction.class)
            .map(a -> (FilterAction<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<MapAction<T, R>> ifMap() {
        return CastUtil.castIf(this, MapAction.class)
            .map(a -> (MapAction<T, R>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<MapToIntAction<T>> ifMapToInt() {
        return CastUtil.castIf(this, MapToIntAction.class)
            .map(a -> (MapToIntAction<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<MapToLongAction<T>> ifMapToLong() {
        return CastUtil.castIf(this, MapToLongAction.class)
            .map(a -> (MapToLongAction<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<MapToDoubleAction<T>> ifMapToDouble() {
        return CastUtil.castIf(this, MapToDoubleAction.class)
            .map(a -> (MapToDoubleAction<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<FlatMapAction<T, R>> ifFlatMap() {
        return CastUtil.castIf(this, FlatMapAction.class)
            .map(a -> (FlatMapAction<T, R>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<FlatMapToIntAction<T>> ifFlatMapToInt() {
        return CastUtil.castIf(this, FlatMapToIntAction.class)
            .map(a -> (FlatMapToIntAction<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<FlatMapToLongAction<T>> ifFlatMapToLong() {
        return CastUtil.castIf(this, FlatMapToLongAction.class)
            .map(a -> (FlatMapToLongAction<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<FlatMapToDoubleAction<T>> ifFlatMapToDouble() {
        return CastUtil.castIf(this, FlatMapToDoubleAction.class)
            .map(a -> (FlatMapToDoubleAction<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<LimitAction<T, TS>> ifLimit() {
        return CastUtil.castIf(this, LimitAction.class)
            .map(a -> (LimitAction<T, TS>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<SkipAction<T, TS>> ifSkip() {
        return CastUtil.castIf(this, SkipAction.class)
            .map(a -> (SkipAction<T, TS>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<DistinctAction<T, TS>> ifDistinct() {
        return CastUtil.castIf(this, DistinctAction.class)
            .map(a -> (DistinctAction<T, TS>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<SortedAction<T, TS>> ifSorted() {
        return CastUtil.castIf(this, SortedAction.class)
            .map(a -> (SortedAction<T, TS>) a);
    }
}