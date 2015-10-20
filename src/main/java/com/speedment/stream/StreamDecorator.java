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
package com.speedment.stream;

import com.speedment.annotation.Api;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.internal.core.stream.builder.ReferenceStreamBuilder;
import com.speedment.internal.core.stream.builder.pipeline.Pipeline;

/**
 *
 * @author Emil Forslund
 * @since 2.2
 */
@Api(version = "2.2")
public interface StreamDecorator {
    
    final static StreamDecorator IDENTITY = new StreamDecorator() {
        @Override
        public StreamDecorator and(StreamDecorator other) {
            return other;
        }

        @Override
        public <ENTITY> ReferenceStreamBuilder<ENTITY> apply(ReferenceStreamBuilder<ENTITY> stream) {
            return stream;
        }

        @Override
        public <ENTITY, V> SpeedmentPredicate<ENTITY, V> apply(SpeedmentPredicate<ENTITY, V> predicate) {
            return predicate;
        }
        
        @Override
        public <P extends Pipeline> P apply(P pipeline) {
            return pipeline;
        }
    };
    
    StreamDecorator and(StreamDecorator other);
    
    <ENTITY> ReferenceStreamBuilder<ENTITY> apply(ReferenceStreamBuilder<ENTITY> stream);
    
    <ENTITY, V> SpeedmentPredicate<ENTITY, V> apply(SpeedmentPredicate<ENTITY, V> predicate);
    
    <P extends Pipeline> P apply(P pipeline);
}