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
package com.speedment.util.stream.builder.streamterminator;

import com.speedment.util.stream.builder.pipeline.Pipeline;

/**
 *
 * @author pemi
 */
public interface BaseStreamTerminator {

    default <T extends Pipeline> T optimize(T initialPipeline) {
        //System.out.println("default optimize(" + initialPipeline + ")");
        return initialPipeline;
    }

//    default <T extends Pipeline> long count(T pipeline, Function<T, Long> defaultHandler) {
//        return defaultHandler.apply(pipeline);
//    }
//    default <T extends Pipeline, P> boolean anyMatch(T pipeline, BiFunction<T, P, Boolean> defaultHandler, P predicate) {
//        return defaultHandler.apply(pipeline, predicate);
//    }
}
