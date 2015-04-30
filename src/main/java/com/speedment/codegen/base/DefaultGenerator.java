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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A generator that can have multiple transform factories. Step-wise generation
 * will be handled by concatenating transform into a <code>BridgeTransform</code>.
 * 
 * @author Emil Forslund
 */
public class DefaultGenerator implements Generator {
    
	private final DependencyManager mgr;
	private final List<TransformFactory> factories;
	private final DefaultRenderStack renderStack;
	
	/**
	 * Creates a new generator. This constructor will use a <code>DefaultDependnecyManager</code>
     * with no parameters to handle any dependencies.
     * 
	 * @param factories The factories to use.
	 */
	public DefaultGenerator(TransformFactory... factories) {
		this(new DefaultDependencyManager(), factories);
	}
	
	/**
	 * Creates a new generator.
     * 
	 * @param mgr The dependency manager to use.
	 * @param factories The factories to use. 
	 */
	public DefaultGenerator(DependencyManager mgr, TransformFactory... factories) {
		this.factories = Arrays.asList(factories);
		this.mgr = mgr;
		this.renderStack = new DefaultRenderStack();
	}
	
	/**
	 * @return the dependency manager.
	 */
	@Override
	public DependencyManager getDependencyMgr() {
		return mgr;
	}
	
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
	 * framework doesn't put any constraints on what can be rendered.
	 * The elements should not be cast directly to the model class but rather
	 * to an interface describing the properties you need to read. That way,
	 * the design remains dynamic even if the exact implementation isn't the
	 * same.
	 * 
	 * The returned Stack will be immutable.
	 * 
	 * @return the current rendering stack.
	 */
	@Override
	public RenderStack getRenderStack() {
		return renderStack;
	}

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
    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to) {
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
    @Override
    public <A, B> Optional<Meta<A, B>> transform(Transform<A, B> transform, A model, TransformFactory factory) {
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