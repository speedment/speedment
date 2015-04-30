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

/**
 *
 * @author Emil Forslund
 * @param <A>
 */
public interface Meta<A, B> {
    
    A getModel();
    B getResult();
    Transform<A, B> getTransform();
    TransformFactory getFactory();
    RenderStack getRenderStack();
    
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
