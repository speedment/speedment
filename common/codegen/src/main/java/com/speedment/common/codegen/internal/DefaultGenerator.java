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
package com.speedment.common.codegen.internal;

import com.speedment.common.codegen.DependencyManager;
import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.RenderStack;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * The default implementation of the {@link Generator} interface.
 * 
 * @author Emil Forslund
 */
public class DefaultGenerator implements Generator {
    
	private final DependencyManager mgr;
	private final TransformFactory factory;
	private final DefaultRenderStack renderStack;
	
	/**
	 * Creates a new generator. This constructor will use a 
     * {@link DefaultDependencyManager} with no parameters to handle any 
     * dependencies.
     * 
	 * @param factory  the factory to use
	 */
	public DefaultGenerator(TransformFactory factory) {
		this(new DefaultDependencyManager(), factory);
	}
	
	/**
	 * Creates a new generator.
     * 
	 * @param mgr      the dependency manager to use
	 * @param factory  the factory to use 
	 */
	public DefaultGenerator(DependencyManager mgr, TransformFactory factory) {
		this.factory     = requireNonNull(factory);
		this.mgr         = requireNonNull(mgr);
		this.renderStack = new DefaultRenderStack();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DependencyManager getDependencyMgr() {
		return mgr;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public RenderStack getRenderStack() {
		return renderStack;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to) {
        requireNonNull(from);
        requireNonNull(to);
        
        if (from instanceof Optional) {
            throw new UnsupportedOperationException(
                "Model must not be an Optional!"
            );
        }

        return BridgeTransform.create(factory, from.getClass(), to)
            .map(t -> (Transform<A, B>) t)
            .map(t -> transform(t, from, factory))
            .filter(Optional::isPresent)
            .map((Function<Optional<Meta<A, B>>, Meta<A, B>>) Optional::get)
        ;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public <A, B> Optional<Meta<A, B>> transform(Transform<A, B> transform, A model, TransformFactory factory) {
        requireNonNull(transform);
        requireNonNull(model);
        requireNonNull(factory);

        renderStack.push(model);

        final Optional<Meta<A, B>> meta = transform
            .transform(this, model)
            .map(s -> Meta.builder(model, s)
                .withTransform(transform)
                .withFactory(factory)
                .withRenderStack(new DefaultRenderStack(renderStack))
                .build()
        );
        
        renderStack.pop();
        
        return meta;
    }
}