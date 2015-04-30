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
package com.speedment.codegen.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class BridgeTransform<A, B> implements Transform<A, B> {
    
    private final List<Transform<?, ?>> steps;
    private final Class<A> from;
    private final Class<B> to;
    private final TransformFactory installer;
    private Class<?> end;
    
    public BridgeTransform(Class<A> from, Class<B> to, TransformFactory installer) {
        this.steps = new ArrayList<>();
        this.from = from;
        this.to = to;
        this.end = from;
        this.installer = installer;
    }
    
    private BridgeTransform(BridgeTransform<A, B> prototype) {
        steps     = new ArrayList<>(prototype.steps);
        from      = prototype.from;
        to        = prototype.to;
        end       = prototype.end;
        installer = prototype.installer;
    }
    
    public <A2, B2> boolean addStep(Class<A2> from, Class<B2> to, Transform<A2, B2> step) {
        if (end == null || from.equals(end)) {
            if (steps.contains(step)) {
                return false;
            } else {
                steps.add(step);
                end = to;
                return true;
            }
        } else {
            throw new IllegalArgumentException("Transform " + step + " has a different entry class (" + from + ") than the last class in the current build (" + end + ").");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<B> transform(Generator gen, A model) {
        Object o = model;
        
        for (final Transform<?, ?> step : steps) {
            if (o == null) {
                return Optional.empty();
            } else {
                final Transform<Object, ?> step2 = (Transform<Object, ?>) step;
                o = gen.transform(step2, o, installer)
                    .map(m -> m.getResult())
                    .orElse(null);
            }
        }
        
        return Optional.ofNullable((B) o);
    }
    
    public static <A, B, T extends Transform<A, B>> Stream<T> create(TransformFactory installer, Class<A> from, Class<B> to) {
        return create(installer, new BridgeTransform<>(from, to, installer));
    }
    
    @SuppressWarnings("unchecked")
    public static <A, B, T extends Transform<A, B>> Stream<T> create(TransformFactory installer, BridgeTransform<A, B> bridge) {
        if (bridge.end.equals(bridge.to)) {
            return Stream.of((T) bridge);
        } else {
            final List<Stream<T>> bridges = new ArrayList<>();
            
            installer.allFrom(bridge.end).stream().forEachOrdered(e -> {
                
                final BridgeTransform<A, B> br = new BridgeTransform<>(bridge);
                
                Class<Object> a = (Class<Object>) bridge.end;
                Class<Object> b = (Class<Object>) e.getKey();
                Transform<Object, Object> transform = (Transform<Object, Object>) e.getValue();
                
                if (br.addStep(a, b, transform)) {
                    bridges.add(create(installer, br));
                }
            });
            
            return bridges.stream().flatMap(i -> i);
        }
    }
}