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

import com.speedment.common.mutablestream.internal.action.LimitActionImpl;
import com.speedment.common.mutablestream.HasNext;
import java.util.stream.BaseStream;

/**
 *
 * @param <T>  the streamed type
 * @param <TS> the main stream interface
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface LimitAction<T, TS extends BaseStream<T, TS>> extends Action<T, TS, T, TS> {

    long getLimit();
    
    static <T, TS extends BaseStream<T, TS>> LimitAction<T, TS> create(HasNext<T, TS> previous, long limit) {
        return new LimitActionImpl<>(previous, limit);
    }
}