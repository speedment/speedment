/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal;

import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.RenderStack;
import com.speedment.common.codegen.RenderTree;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;
import static java.util.Objects.requireNonNull;

/**
 * Meta implementation.
 *
 * @param <A> the model type
 * @param <B> the result type
 */
public final class MetaImpl<A, B> implements Meta<A, B> {
    
    private final A model;
    private final B result;
    private final Transform<A, B> transform;
    private final TransformFactory factory;
    private final RenderStack stack;
    private final RenderTree tree;

    private MetaImpl(
            A model, 
            B result, 
            Transform<A, B> transform, 
            TransformFactory factory, 
            RenderStack stack, 
            RenderTree tree) {
        
        this.model     = requireNonNull(model);
        this.result    = requireNonNull(result);
        this.transform = requireNonNull(transform);
        this.factory   = requireNonNull(factory);
        this.stack     = requireNonNull(stack);
        this.tree      = requireNonNull(tree);
    }

    @Override
    public B getResult() {
        return result;
    }

    @Override
    public Transform<A, B> getTransform() {
        return transform;
    }

    @Override
    public TransformFactory getFactory() {
        return factory;
    }
    
    @Override
    public A getModel() {
        return model;
    }

    @Override
    public RenderStack getRenderStack() {
        return stack;
    }
    
    @Override
    public RenderTree getRenderTree() {
        return tree;
    }

    @Override
    public String toString() {
        return "MetaImpl{" + "model=" + model + ", result=" + result + ", transform=" + transform + ", factory=" + factory + ", stack=" + stack + ", tree=" + tree + '}';
    }
   
    public static final class Builder<A, B> implements Meta.Builder<A, B> {
        
        private A model;
        private B result;
        private Transform<A, B> transform;
        private TransformFactory factory;
        private RenderStack stack;
        private RenderTree tree;
        
        public Builder(A model, B result) {
            this.model  = requireNonNull(model);
            this.result = requireNonNull(result);
        }
        
        @Override
        public Meta.Builder<A, B> withResult(B result) {
            this.result = requireNonNull(result);
            return this;
        }
        
        @Override
        public Meta.Builder<A, B> withTransform(Transform<A, B> transform) {
            this.transform = requireNonNull(transform);
            return this;
        }
        
        @Override
        public Meta.Builder<A, B> withFactory(TransformFactory factory) {
            this.factory = requireNonNull(factory);
            return this;
        }
        
        @Override
        public Meta.Builder<A, B> withModel(A model) {
            this.model = requireNonNull(model);
            return this;
        }
        
        @Override
        public Meta.Builder<A, B> withRenderStack(RenderStack stack) {
            this.stack = requireNonNull(stack);
            return this;
        }
        
        @Override
        public Meta.Builder<A, B> withRenderTree(RenderTree tree) {
            this.tree = requireNonNull(tree);
            return this;
        }
        
        @Override
        public Meta<A, B> build() {
            return new MetaImpl<>(
                model,
                result,
                transform,
                factory,
                stack,
                tree
            );
        }
    }
}
