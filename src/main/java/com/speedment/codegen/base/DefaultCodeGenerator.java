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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * A <code>DefaultCodeGenerator</code> is the class that is called to view a model. The
 * class is instantiated with a particular <code>VersionEnum</code> so that it
 * can map inputed models to the appropriate view. 
 * 
 * The code generator can also be used to keep track of which dependencies has
 * been imported.
 * 
 * @author Emil Forslund
 */
public class DefaultCodeGenerator implements CodeGenerator {
	private final Installer installer;
	private final DependencyManager dependencyMgr;
	private final Stack<Object> renderStack;
	
	/**
	 * Initialises the code generator using a default dependency manager.
	 * See <code>CodeGenerator(DefaultInstaller, DefaultDependencyManager)</code> for a full
	 * description.
	 * @param installer
	 */
	public DefaultCodeGenerator(Installer installer) {
		this(installer, new DefaultDependencyManager());
	}
	
	/**
	 * Initialises a code generator based on the specified code version. The
	 * code version will be used to map models to the appropriate view.
	 * @param installer A subclass of <code>DefaultInstaller</code>.
	 * @param mgr A DefaultDependencyManager to keep track of dependencies.
	 */
	public DefaultCodeGenerator(Installer installer, DependencyManager mgr) {
		this.installer = installer;
		this.dependencyMgr = mgr;
		this.renderStack = new Stack<>();
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
	public List<Object> getRenderStack() {
		return Collections.unmodifiableList(renderStack);
	}
	
	/**
	 * @return the dependency manager.
	 */
	@Override
	public DependencyManager getDependencyMgr() {
		return dependencyMgr;
	}

    /**
     * Renders the specified model into a stream of code models.
     * This is used internally to provide the other interface methods.
     * 
     * @param model The model to generate.
     * @return A stream of code objects.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <M> Stream<Code<M>> codeOn(M model) {
        if (model instanceof Optional) {
            throw new UnsupportedOperationException(
                "Model must not be an Optional!"
            );
        }
        
        return installer.withAll(model.getClass())
            .map(v -> (CodeView<M>) v)
            .map(v -> render(v, model))
            .filter(s -> s.isPresent())
            .map(s -> s.get());
    }

	private <M> Optional<Code<M>> render(CodeView<M> view, M model) {
        renderStack.push(model);
		final Optional<String> result = view.render(this, model);
		renderStack.pop();
        
		return result.map(s -> new Code.Impl<M>()
            .setCode(s)
            .setInstaller(installer)
            .setView(view)
            .setModel(model)
        );
	}
}