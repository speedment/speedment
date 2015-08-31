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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A hook to the generator that can be passed to various stages in the pipeline.
 * Contains multiple methods for generating model-to-model or model-to-text.
 * 
 * @author Emil Forslund
 */
public interface Generator {

    /**
     * Returns the {@link DependencyManager} currently being used.
     * 
     * @return  the dependency manager
     * 
     * @see DependencyManager
     */
    DependencyManager getDependencyMgr();

    /**
     * Returns the current rendering stack. The top element will be the one most
     * recent rendered and the bottom one will be the element that was first
     * passed to the generator. Elements are removed from the stack once they
     * have finished rendering.
     * <p>
     * If an element needs to access its parent, it can call this method and
     * peek on the second element from the top.
     *
     * @return  the current rendering stack
     * 
     * @see RenderStack
     */
    RenderStack getRenderStack();
    
    /**
     * Renders the specified model into a stream of code models. This is used
     * internally to provide the other interface methods.
     *
     * @param <A>   the input type
     * @param <B>   the expected output type
     * @param from  the model to generate
     * @param to    the model type to transform to
     * @return      a stream of meta objects
     * 
     * @see Meta
     */
    <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to);
    
    /**
     * Renders the specified model into a stream of code models. This is used
     * internally to provide the other interface methods.
     * <p>
     * If the specified transform is not installed, an empty stream will be
     * returned.
     *
     * @param <A>        the input type
     * @param <B>        the expected output type
     * @param from       the model to generate
     * @param to         the model type to transform to
     * @param transform  the specified transform to use
     * @return           a stream of meta objects
     * 
     * @see Meta
     */
    default <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to, Class<? extends Transform<A, B>> transform) {
        return metaOn(from, to)
            .filter(meta -> transform.equals(meta.getTransform().getClass()));
    }

    /**
     * Renders the specified model into a stream of code models. This is used
     * internally to provide the other interface methods.
     *
     * @param <M>    the model type
     * @param model  the model to generate
     * @return       a stream of meta objects
     * 
     * @see Meta
     */
    default <M> Stream<Meta<M, String>> metaOn(M model) {
        return metaOn(model, String.class);
    }

    /**
     * Renders all the specified models into a stream of code models. This is
     * used internally to provide the other interface methods.
     *
     * @param <A>     the input type
     * @param models  the models to generate
     * @return        a stream of meta objects
     * 
     * @see Meta
     */
    default <A> Stream<Meta<A, String>> metaOn(Collection<A> models) {
        return models.stream().map(model -> metaOn(model)).flatMap(m -> m);
    }
    
    /**
     * Renders all the specified models into a stream of code models. This is
     * used internally to provide the other interface methods.
     *
     * @param <A>     the input type
     * @param <B>     the expected output type
     * @param models  the models to generate
     * @param to      the expected result type
     * @return        a stream of meta objects
     * 
     * @see Meta
     */
    default <A, B> Stream<Meta<A, B>> metaOn(Collection<A> models, Class<B> to) {
        return models.stream().map(model -> metaOn(model, to)).flatMap(m -> m);
    }
    
    /**
     * Renders all the specified models into a stream of code models. This is
     * used internally to provide the other interface methods. This will only
     * return results from the specified transform.
     * <p>
     * If the specified transform is not installed, an empty stream will be
     * returned.
     * 
     * @param <A>        the input type
     * @param <B>        the expected output type
     * @param models     the models to generate
     * @param to         the expected result type
     * @param transform  the specific transform to use
     * @return           a stream of meta objects
     * 
     * @see Meta
     */
    default <A, B> Stream<Meta<A, B>> metaOn(Collection<A> models, Class<B> to, Class<? extends Transform<A, B>> transform) {
        return metaOn(models, to)
            .filter(meta -> meta.getTransform().is(transform));
    }

    /**
     * Locates the {@link Transform} that corresponds to the specified model
     * and uses it to generate a <code>String</code>. If no view is associated 
     * with the model type, an empty <code>Optional</code> will be returned.
     *
     * @param model  the model
     * @return       the generated text if any
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
     * @param <M>     the model type
     * @param models  the models to generate
     * @return        a stream of meta objects
     */
    default <M> Stream<String> onEach(Collection<M> models) {
        return metaOn(models).map(c -> c.getResult());
    }
    
    /**
     * Transforms the specified model using the specified {@link Transform} from 
     * the specified {@link TransformFactory}.
     * 
     * @param <A>        the input type
     * @param <B>        the expected output type
     * @param transform  the transform to use
     * @param model      the inputed model
     * @param factory    the factory used when instantiating the transform
     * @return           the meta object if successful, else empty
     * 
     * @see    Transform
     * @see    TransformFactory
     */
    <A, B> Optional<Meta<A, B>> transform(Transform<A, B> transform, A model, TransformFactory factory);
}