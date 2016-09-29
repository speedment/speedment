/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.stream;

import com.speedment.runtime.core.field.predicate.FieldPredicate;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.StreamDecorator;
import com.speedment.runtime.core.stream.parallel.HasParallelStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ComposedStreamDecorator implements StreamDecorator {

    private final List<StreamDecorator> decorators;

    public ComposedStreamDecorator(StreamDecorator... decorators) {
        this.decorators = Arrays.asList(decorators);
    }

    @Override
    public StreamDecorator and(StreamDecorator other) {
        decorators.add(other);
        return this;
    }

    @Override
    public <ENTITY, S extends Stream<ENTITY>> S applyOnFinal(S stream) {
        S s = stream;

        for (final StreamDecorator sd : decorators) {
            s = sd.applyOnFinal(s);
        }

        return s;
    }

    @Override
    public <ENTITY> FieldPredicate<ENTITY> apply(FieldPredicate<ENTITY> predicate) {
        FieldPredicate<ENTITY> s = predicate;

        for (final StreamDecorator sd : decorators) {
            s = sd.apply(s);
        }

        return s;
    }

    @Override
    public <P extends Pipeline> P apply(P pipeline) {
        P p = pipeline;

        for (final StreamDecorator sd : decorators) {
            p = sd.apply(p);
        }

        return p;
    }

    @Override
    public <ENTITY, S extends Stream<ENTITY>> S applyOnInitial(S stream) {
        S s = stream;

        for (final StreamDecorator sd : decorators) {
            s = sd.applyOnInitial(s);
        }
        
        return s;
    }

    @Override
    public <H extends HasParallelStrategy> H apply(H hasParallelStrategy) {
        H h = hasParallelStrategy;

        for (final StreamDecorator sd : decorators) {
            h = sd.apply(h);
        }
        
        return h;
    }
}