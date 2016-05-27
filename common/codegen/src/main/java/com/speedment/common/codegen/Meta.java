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
package com.speedment.common.codegen;

import com.speedment.common.codegen.internal.MetaImpl;

/**
 * Meta information about the generation process.
 *
 * @param <A> the model type
 * @param <B> the result type
 * @author Emil Forslund
 * @since 2.0
 */
public interface Meta<A, B> {

    /**
     * The model that was sent to the generator.
     *
     * @return the model
     */
    A getModel();

    /**
     * The result that was produced by the generator.
     *
     * @return the model
     */
    B getResult();

    /**
     * The transform that was used to produce the result.
     *
     * @return the transform
     */
    Transform<A, B> getTransform();

    /**
     * The factory that created the transform.
     *
     * @return the factory
     */
    TransformFactory getFactory();

    /**
     * The render stack that represents which generation processes is waiting for this result.
     *
     * @return the current render stack
     */
    RenderStack getRenderStack();

    /**
     * Returns a new builder.
     *
     * @param <A> the model type
     * @param <B> the result type
     * @param model the model
     * @param result the result
     * @return the builder instance.
     */
    static <A, B> Meta.Builder<A, B> builder(A model, B result) {
        return new MetaImpl.Builder<>(model, result);
    }

    /**
     * Builder for {@link Meta} objects.
     * 
     * @param <A>  the model type
     * @param <B>  the result type
     */
    interface Builder<A, B> {

        /**
         * The model that was sent to the generator.
         *
         * @param model
         * @return the model
         */
        Builder<A, B> withModel(A model);

        /**
         * The result that was produced by the generator.
         *
         * @param result
         * @return the model
         */
        Builder<A, B> withResult(B result);

        /**
         * The transform that was used to produce the result.
         *
         * @param transform
         * @return the transform
         */
        Builder<A, B> withTransform(Transform<A, B> transform);

        /**
         * The factory that created the transform.
         *
         * @param factory
         * @return the factory
         */
        Builder<A, B> withFactory(TransformFactory factory);

        /**
         * The render stack that represents which generation processes is waiting for this result.
         *
         * @param stack
         * @return the current render stack
         */
        Builder<A, B> withRenderStack(RenderStack stack);
        
        /**
         * Builds this instance.
         * 
         * @return  the built instance
         */
        Meta<A, B> build();
    }
}
