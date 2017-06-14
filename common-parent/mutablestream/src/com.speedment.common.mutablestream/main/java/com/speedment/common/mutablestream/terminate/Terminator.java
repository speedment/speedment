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
package com.speedment.common.mutablestream.terminate;

import com.speedment.common.mutablestream.HasPrevious;
import com.speedment.common.mutablestream.internal.util.CastUtil;
import java.util.Optional;
import java.util.stream.BaseStream;

/**
 *
 * @param <T>   the terminated stream type
 * @param <TS>  the type of the stream itself
 * @param <R>   the final type returned upon execution
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface Terminator<T, TS extends BaseStream<T, TS>, R> extends HasPrevious<T, TS> {

    boolean isParallel();
    
    R execute();
    
    @SuppressWarnings("unchecked")
    default Optional<CountTerminator<T, TS>> ifCount() {
        return CastUtil.castIf(this, CountTerminator.class)
            .map(a -> (CountTerminator<T, TS>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<AverageTerminator<T, TS>> ifAverage() {
        return CastUtil.castIf(this, AverageTerminator.class)
            .map(a -> (AverageTerminator<T, TS>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<AllMatchTerminator<T>> ifAllMatch() {
        return CastUtil.castIf(this, AllMatchTerminator.class)
            .map(a -> (AllMatchTerminator<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<AnyMatchTerminator<T>> ifAnyMatch() {
        return CastUtil.castIf(this, AnyMatchTerminator.class)
            .map(a -> (AnyMatchTerminator<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<NoneMatchTerminator<T>> ifNoneMatch() {
        return CastUtil.castIf(this, NoneMatchTerminator.class)
            .map(a -> (NoneMatchTerminator<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<FindAnyTerminator<T>> ifFindAny() {
        return CastUtil.castIf(this, FindAnyTerminator.class)
            .map(a -> (FindAnyTerminator<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<FindFirstTerminator<T>> ifFindFirst() {
        return CastUtil.castIf(this, FindFirstTerminator.class)
            .map(a -> (FindFirstTerminator<T>) a);
    }
    
    @SuppressWarnings("unchecked")
    default Optional<ForEachTerminator<T>> ifForEach() {
        return CastUtil.castIf(this, ForEachTerminator.class)
            .map(a -> (ForEachTerminator<T>) a);
    }
}