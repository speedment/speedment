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
package com.speedment.internal.codegen.java.view;

import com.speedment.codegen.Generator;
import com.speedment.codegen.Transform;
import com.speedment.codegen.model.ClassOrInterface;
import com.speedment.internal.codegen.java.view.trait.HasAnnotationUsageView;
import com.speedment.internal.codegen.java.view.trait.HasClassesView;
import com.speedment.internal.codegen.java.view.trait.HasFieldsView;
import com.speedment.internal.codegen.java.view.trait.HasGenericsView;
import com.speedment.internal.codegen.java.view.trait.HasImplementsView;
import com.speedment.internal.codegen.java.view.trait.HasInitalizersView;
import com.speedment.internal.codegen.java.view.trait.HasJavadocView;
import com.speedment.internal.codegen.java.view.trait.HasMethodsView;
import com.speedment.internal.codegen.java.view.trait.HasModifiersView;
import com.speedment.internal.codegen.java.view.trait.HasNameView;
import static com.speedment.internal.codegen.util.Formatting.*;
import static com.speedment.util.NullUtil.requireNonNullElements;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;


/**
 * An abstract base class used to share functionality between different view
 * components such as {@link ClassView}, {@link EnumView} and 
 * {@link InterfaceView}.
 * 
 * @param <M> The extending model type
 * @author Emil Forslund
 */
abstract class ClassOrInterfaceView<M extends ClassOrInterface<M>> implements 
    Transform<M, String>, HasNameView<M>, HasModifiersView<M>, HasJavadocView<M>, 
    HasGenericsView<M>, HasImplementsView<M>, HasInitalizersView<M>, HasMethodsView<M>,
    HasClassesView<M>, HasAnnotationUsageView<M>, HasFieldsView<M> {
    
	protected final static String
		CLASS_STRING = "class ",
		INTERFACE_STRING = "interface ",
		ENUM_STRING = "enum ",
		IMPLEMENTS_STRING = "implements ",
		EXTENDS_STRING = "extends ";

    /**
     * A hook that is executed just before the 'fields' part of the class code.
     * 
     * @param gen    the generator being used
     * @param model  the model that is generated
     * @return       code to be inserted before the fields
     */
	protected String onBeforeFields(Generator gen, M model) {
		return EMPTY;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public String fieldSeparator() {
        return nl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String fieldSuffix() {
        return SC;
    }
	
    /**
     * Returns the declaration type of this model. This can be either 'class',
     * 'interface' or 'enum'.
     * 
     * @return  the declaration type
     */
    protected abstract String renderDeclarationType();
    
    /**
     * Returns the supertype of this model. The supertype should include any
     * declaration like 'implements' or 'extends'.
     * <p>
     * Example: <pre>"implements List"</pre>
     * 
     * @param gen    the generator to use
     * @param model  the model of the component
     * @return       the supertype part
     */
	protected abstract String renderSupertype(Generator gen, M model);
    
    /**
     * Should render the constructors part of the code and return it.
     * 
     * @param gen    the generator to use
     * @param model  the model of the component
     * @return       generated constructors or an empty string if there shouldn't
     *               be any
     */
    protected abstract String renderConstructors(Generator gen, M model);
    
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, M model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		return Optional.of(renderJavadoc(gen, model) +
            renderAnnotations(gen, model) +
			renderModifiers(gen, model) +
            renderDeclarationType() + 
            renderName(gen, model) + 
            renderGenerics(gen, model) +
            renderSupertype(gen, model) +
			renderInterfaces(gen, model) +
                
            // Code
            block(nl() + separate(
				onBeforeFields(gen, model), // Enums have constants here.´
                renderFields(gen, model),
				renderConstructors(gen, model),
                renderInitalizers(gen, model),
				renderMethods(gen, model),
				renderClasses(gen, model)
			))
		);
	}
	
    /**
     * Converts the specified elements into strings using their
     * <code>toString</code>-method and combines them with two new-line-characters.
     * Empty strings will be discarded.
     * 
     * @param strings  the strings to combine
     * @return         the combined string
     */
	private String separate(Object... strings) {
        requireNonNullElements(strings);
		return Stream.of(strings)
			.map(Object::toString)
			.filter(s -> s.length() > 0)
			.collect(joining(dnl()));
	}
}