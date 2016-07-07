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
package com.speedment.runtime.internal.stream;

import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.stream.HasParallelStrategy;
import com.speedment.runtime.stream.Pipeline;
import com.speedment.runtime.stream.StreamDecorator;

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

        for (StreamDecorator sd : decorators) {
            s = sd.applyOnFinal(s);
        }

        return s;
    }

    @Override
    public <ENTITY, D, V> SpeedmentPredicate<ENTITY, D, V> apply(SpeedmentPredicate<ENTITY, D, V> predicate) {
        SpeedmentPredicate<ENTITY, D, V> s = predicate;

        for (StreamDecorator sd : decorators) {
            s = sd.apply(s);
        }

        return s;
    }

    @Override
    public <P extends Pipeline> P apply(P pipeline) {
        P p = pipeline;

        for (StreamDecorator sd : decorators) {
            p = sd.apply(p);
        }

        return p;
    }

    @Override
    public <ENTITY, S extends Stream<ENTITY>> S applyOnInitial(S stream) {
        S s = stream;

        for (StreamDecorator sd : decorators) {
            s = sd.applyOnInitial(s);
        }
        return s;
    }

    @Override
    public <H extends HasParallelStrategy> H apply(H hasParallelStrategy) {
        H h = hasParallelStrategy;

        for (StreamDecorator sd : decorators) {
            h = sd.apply(h);
        }
        return h;

    }

}
