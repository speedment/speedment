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
package com.speedment.internal.codegen.base;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A transform that uses a series of transforms to complete the transformation.
 * For an example, if you have two transforms A → B and A → C you can define
 * a <code>BridgeTransform</code> for A → C.
 * 
 * @author Emil Forslund
 * @param <A> the from type
 * @param <B> the to type
 */
public class BridgeTransform<A, B> implements Transform<A, B> {
    
    private final List<Transform<?, ?>> steps;
    private final Class<A> from;
    private final Class<B> to;
    private final TransformFactory factory;
    private Class<?> end;
    
    /**
     * Constructs a new transform from one model class to another. The bridge
     * requires a factory to create the intermediate steps.
     * 
     * @param from the type to transform from
     * @param to the type to transform to
     * @param factory a factory with all the required steps installed
     */
    private BridgeTransform(Class<A> from, Class<B> to, TransformFactory factory) {
        this.from    = requireNonNull(from);
        this.to      = requireNonNull(to);
        this.factory = requireNonNull(factory);
        this.steps   = new ArrayList<>();
        this.end     = requireNonNull(from);
    }
    
    /**
     * Creates a shallow copy of a bridge.
     * 
     * @param prototype the prototype
     */
    private BridgeTransform(BridgeTransform<A, B> prototype) {
        steps   = new ArrayList<>(prototype.steps);
        from    = prototype.from;
        to      = prototype.to;
        end     = prototype.end;
        factory = prototype.factory;
    }
    
    /**
     * Transforms the specified model using this transform. A code generator is
     * supplied so that the transform can initiate new generation processes to
     * resolve dependencies.
     * 
     * @param gen   the current code generator 
     * @param model the model to transform
     * @return      the transformation result or empty if any of the steps 
     *              returned empty
     */
    @Override
    public Optional<B> transform(Generator gen, A model) {
        requireNonNull(gen);
        requireNonNull(model);
        
        Object o = model;
        
        for (final Transform<?, ?> step : steps) {
            if (o == null) {
                return Optional.empty();
            } else {
                @SuppressWarnings("unchecked")
                final Transform<Object, ?> step2 = (Transform<Object, ?>) step;
                
                o = gen.transform(step2, o, factory)
                    .map(m -> m.getResult())
                    .orElse(null);
            }
        }
        
        if (o == null) {
            return Optional.empty();
        } else {
            if (to.isAssignableFrom(o.getClass())) {
                @SuppressWarnings("unchecked")
                final B result = (B) o;
                return Optional.of(result);
            } else {
                throw new IllegalStateException(
                    "The bridge between '" + 
                    from.getSimpleName() + 
                    "' to '" + 
                    to.getSimpleName() + 
                    "' is not complete."
                );
            }
        }
    }
    
    /**
     * Creates a bridge from one model type to another. A factory is supplied so
     * that intermediate steps can be resolved.
     * 
     * @param <A>     the initial type of a model to transform
     * @param <B>     the final type of a model after transformation
     * @param <T>     the type of a transform between A and B
     * @param factory a factory with all intermediate steps installed
     * @param from    the initial class of a model to transform
     * @param to      the final class of a model after transformation
     * @return        a <code>Stream</code> of all unique paths between A and B
     */
    public static <A, B, T extends Transform<A, B>> Stream<T> create(TransformFactory factory, Class<A> from, Class<B> to) {
        return create(factory, new BridgeTransform<>(from, to, factory));
    }
    
    /**
     * Takes a bridge and completes it if it is not finished. Returns all valid 
     * paths through the graph as a <code>Stream</code>.
     * 
     * @param <A>     the initial type of a model to transform
     * @param <B>     the final type of a model after transformation
     * @param <T>     the type of a transform between A and B
     * @param factory a factory with all intermediate steps installed
     * @param bridge  the incomplete bridge to finish
     * @return        a <code>Stream</code> of all unique paths between A and B
     */
    private static <A, B, T extends Transform<A, B>> Stream<T> create(TransformFactory factory, BridgeTransform<A, B> bridge) {
        requireNonNull(factory);
        requireNonNull(bridge);
        
        if (bridge.end.equals(bridge.to)) {
            @SuppressWarnings("unchecked")
            final T result = (T) bridge;
            return Stream.of(result);
        } else {
            final List<Stream<T>> bridges = new ArrayList<>();
            
            factory.allFrom(bridge.end).stream().forEachOrdered(e -> {
                
                final BridgeTransform<A, B> br = new BridgeTransform<>(bridge);
                
                @SuppressWarnings("unchecked")
                Class<Object> a = (Class<Object>) bridge.end;
                
                @SuppressWarnings("unchecked")
                Class<Object> b = (Class<Object>) e.getKey();
                
                @SuppressWarnings("unchecked")
                Transform<Object, Object> transform = (Transform<Object, Object>) e.getValue();
                
                if (br.addStep(a, b, transform)) {
                    bridges.add(create(factory, br));
                }
            });
            
            return bridges.stream().flatMap(i -> i);
        }
    }

    /**
     * Returns true if this transform is or contains the specified 
     * transformer. This is used internally by the code generator to avoid 
     * circular paths.
     * 
     * @param transformer  the type of the transformer to check
     * @return             true if this transform is or contains the input
     */
    @Override
    public boolean is(Class<? extends Transform<?, ?>> transformer) {
        return steps.stream().anyMatch(t -> t.is(requireNonNull(transformer)));
    }
    
    /**
     * Attempts to add a new step to the bridge. If the step is already part of
     * the bridge, it will not be added. Returns true if the step was added.
     * 
     * @param <A2>  the initial type of the step
     * @param <B2>  the output type of the step
     * @param from  the initial type of the step
     * @param to    the output type of the step
     * @param step  the step to attempt to add
     * @return      true if the step was added
     */
    private <A2, B2> boolean addStep(Class<A2> from, Class<B2> to, Transform<A2, B2> step) {
        requireNonNull(from);
        requireNonNull(to);
        requireNonNull(step);
        
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
}