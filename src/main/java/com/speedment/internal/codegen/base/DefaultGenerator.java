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

import static com.speedment.internal.util.NullUtil.requireNonNulls;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link Generator} interface.
 * 
 * @author Emil Forslund
 */
public class DefaultGenerator implements Generator {
    
	private final DependencyManager mgr;
	private final List<TransformFactory> factories;
	private final DefaultRenderStack renderStack;
	
	/**
	 * Creates a new generator. This constructor will use a 
     * {@link DefaultDependencyManager} with no parameters to handle any 
     * dependencies.
     * 
	 * @param factories  the factories to use
	 */
	public DefaultGenerator(TransformFactory... factories) {
		this(new DefaultDependencyManager(), requireNonNulls(factories));
	}
	
	/**
	 * Creates a new generator.
     * 
	 * @param mgr        the dependency manager to use
	 * @param factories  the factories to use 
	 */
	public DefaultGenerator(DependencyManager mgr, TransformFactory... factories) {
		this.factories = Arrays.asList(requireNonNulls(factories));
		this.mgr = requireNonNull(mgr);
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

        return factories.stream().flatMap(factory ->
            BridgeTransform.create(factory, from.getClass(), to)
            .map(t -> (Transform<A, B>) t)
            .map(t -> transform(t, from, factory))
            .filter(o -> o.isPresent())
            .map(o -> o.get())
        );
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
            .map(s -> new Meta.Impl<A, B>()
            .setModel(model)
            .setResult(s)
            .setTransform(transform)
            .setFactory(factory)
            .setRenderStack(new DefaultRenderStack(renderStack))
        );
        
        renderStack.pop();
        
        return meta;
    }
}