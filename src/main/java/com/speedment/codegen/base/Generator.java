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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface Generator {

    /**
     * @return the dependency manager.
     */
    DependencyManager getDependencyMgr();

    /**
     * Returns the current rendering stack. The top element will be the one most
     * recent rendered and the bottom one will be the element that was first
     * passed to the generator. Elements are removed from the stack once they
     * have finished rendering.
     *
     * If an element needs to access its parent, it can call this method and
     * peek on the second element from the top.
     *
     * The elements in the Stack will be of Object type. That is because the
     * framework doesn't put any constraints on what can be rendered. The
     * elements should not be cast directly to the model class but rather to an
     * interface describing the properties you need to read. That way, the
     * design remains dynamic even if the exact implementation isn't the same.
     *
     * @return the current rendering stack.
     */
    RenderStack getRenderStack();
    
    /**
     * Renders the specified model into a stream of code models. This is used
     * internally to provide the other interface methods.
     *
     * @param <A> The input type.
     * @param <B> The expected output type.
     * @param from The model to generate.
     * @param to The model type to transform to.
     * @return A stream of meta objects.
     */
    <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to);
    
    /**
     * Renders the specified model into a stream of code models. This is used
     * internally to provide the other interface methods.
     * 
     * If the specified transform is not installed, an empty stream will be
     * returned.
     *
     * @param <A> The input type.
     * @param <B> The expected output type.
     * @param from The model to generate.
     * @param to The model type to transform to.
     * @param transform The specified transform to use.
     * @return A stream of meta objects.
     */
    default <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to, Class<? extends Transform<A, B>> transform) {
        return metaOn(from, to)
            .filter(meta -> transform.equals(meta.getTransform().getClass()));
    }

    /**
     * Renders the specified model into a stream of code models. This is used
     * internally to provide the other interface methods.
     *
     * @param <M>
     * @param model The model to generate.
     * @return A stream of meta objects.
     */
    default <M> Stream<Meta<M, String>> metaOn(M model) {
        return metaOn(model, String.class);
    }

    /**
     * Renders all the specified models into a stream of code models. This is
     * used internally to provide the other interface methods. ¨
     *
     * @param <A> The input type.
     * @param models The models to generate.
     * @return A stream of meta objects.
     */
    default <A> Stream<Meta<A, String>> metaOn(Collection<A> models) {
        return models.stream().map(model -> metaOn(model)).flatMap(m -> m);
    }
    
    /**
     * Renders all the specified models into a stream of code models. This is
     * used internally to provide the other interface methods. ¨
     *
     * @param <A> The input type.
     * @param <B> The expected output type.
     * @param models The models to generate.
     * @param to The expected result type.
     * @return A stream of meta objects.
     */
    default <A, B> Stream<Meta<A, B>> metaOn(Collection<A> models, Class<B> to) {
        return models.stream().map(model -> metaOn(model, to)).flatMap(m -> m);
    }
    
    /**
     * Renders all the specified models into a stream of code models. This is
     * used internally to provide the other interface methods. This will only
     * return results from the specified transform.
     * 
     * If the specified transform is not installed, an empty stream will be
     * returned.
     * 
     * @param <A> The input type.
     * @param <B> The expected output type.
     * @param models The models to generate.
     * @param to The expected result type.
     * @param transform The specific transform to use.
     * @return A stream of meta objects.
     */
    default <A, B> Stream<Meta<A, B>> metaOn(Collection<A> models, Class<B> to, Class<? extends Transform<A, B>> transform) {
        return metaOn(models, to)
            .filter(meta -> transform.equals(meta.getTransform().getClass()));
    }

    /**
     * Locates the <code>CodeView</code> that corresponds to the specified model
     * and uses it to generate a String. If no view is associated with the model
     * type, an empty optional will be returned.
     *
     * @param model The model.
     * @return The generated text if any.
     */
    default Optional<String> on(Object model) {
        if (model instanceof Optional) {
            final Optional<?> result = (Optional<?>) model;
            if (result.isPresent()) {
                model = result.get();
            } else {
                return Optional.empty();
            }
        }
        
        return metaOn(model).map(c -> c.getResult()).findAny();
    }

    /**
     * Renders all the specified models into a stream of strings.
     *
     * @param <M>
     * @param models The models to generate.
     * @return A stream of meta objects.
     */
    default <M> Stream<String> onEach(Collection<M> models) {
        return metaOn(models).map(c -> c.getResult());
    }
    
    /**
     * Transforms the specified model using the specified transform from the
     * specified installer.
     * 
     * @param <A> The input type.
     * @param <B> The expected output type.
     * @param transform The transform to use.
     * @param model The inputed model.
     * @param factory The factory used when instantiating the transform.
     * @return The meta object if successful, else empty.
     */
    <A, B> Optional<Meta<A, B>> transform(Transform<A, B> transform, A model, TransformFactory factory);
}