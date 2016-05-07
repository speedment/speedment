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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.codegen;

import com.speedment.codegen.Meta;
import com.speedment.codegen.RenderStack;
import com.speedment.codegen.Transform;
import com.speedment.codegen.TransformFactory;

/**
 * Meta implementation.
 *
 * @param <A> the model type
 * @param <B> the result type
 */
public class MetaImpl<A, B> implements Meta<A, B> {
    
        private A model;
        private B result;
        private Transform<A, B> transform;
        private TransformFactory factory;
        private RenderStack stack;

        public MetaImpl() {}

        @Override
        public B getResult() {
            return result;
        }

        protected MetaImpl<A, B> setResult(B result) {
            this.result = result;
            return this;
        }

        @Override
        public Transform<A, B> getTransform() {
            return transform;
        }

        protected MetaImpl<A, B> setTransform(Transform<A, B> view) {
            this.transform = view;
            return this;
        }

        @Override
        public TransformFactory getFactory() {
            return factory;
        }

        protected MetaImpl<A, B> setFactory(TransformFactory factory) {
            this.factory = factory;
            return this;
        }

        @Override
        public A getModel() {
            return model;
        }
        
        public MetaImpl<A, B> setModel(A model) {
            this.model = model;
            return this;
        }

        @Override
        public RenderStack getRenderStack() {
            return stack;
        }
        
        public MetaImpl<A, B> setRenderStack(RenderStack stack) {
            this.stack = stack;
            return this;
        }

        @Override
        public String toString() {
            return "MetaImpl{" + "model=" + model + ", result=" + result + ", transform=" + transform + ", factory=" + factory + ", stack=" + stack + '}';
        }
   
}
