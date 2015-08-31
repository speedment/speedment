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

/**
 * Meta information about the generation process.
 * 
 * @author Emil Forslund
 * @param <A> the model type
 * @param <B> the result type
 */
public interface Meta<A, B> {
    
    /**
     * The model that was sent to the generator.
     * @return  the model
     */
    A getModel();
    
    /**
     * The result that was produced by the generator.
     * @return  the model
     */
    B getResult();
    
    /**
     * The transform that was used to produce the result.
     * @return  the transform
     */
    Transform<A, B> getTransform();
    
    /**
     * The factory that created the transform.
     * @return  the factory
     */
    TransformFactory getFactory();
    
    /**
     * The render stack that represents which generation processes is waiting
     * for this result.
     * 
     * @return  the current render stack
     */
    RenderStack getRenderStack();
    
    /**
     * Meta implementation.
     * 
     * @param <A> the model type
     * @param <B> the result type
     */
    class Impl<A, B> implements Meta<A, B> {
    
        private A model;
        private B result;
        private Transform<A, B> transform;
        private TransformFactory factory;
        private RenderStack stack;

        Impl() {}

        @Override
        public B getResult() {
            return result;
        }

        protected Impl<A, B> setResult(B result) {
            this.result = result;
            return this;
        }

        @Override
        public Transform<A, B> getTransform() {
            return transform;
        }

        protected Impl<A, B> setTransform(Transform<A, B> view) {
            this.transform = view;
            return this;
        }

        @Override
        public TransformFactory getFactory() {
            return factory;
        }

        protected Impl<A, B> setFactory(TransformFactory factory) {
            this.factory = factory;
            return this;
        }

        @Override
        public A getModel() {
            return model;
        }
        
        public Impl<A, B> setModel(A model) {
            this.model = model;
            return this;
        }

        @Override
        public RenderStack getRenderStack() {
            return stack;
        }
        
        public Impl<A, B> setRenderStack(RenderStack stack) {
            this.stack = stack;
            return this;
        }

        @Override
        public String toString() {
            return "Impl{" + "model=" + model + ", result=" + result + ", transform=" + transform + ", factory=" + factory + ", stack=" + stack + '}';
        }
    }
}
