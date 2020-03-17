/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.internal.java.view.trait.*;
import com.speedment.common.codegen.model.ClassOrInterface;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNullElements;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.common.codegen.util.Formatting.*;
import static java.util.stream.Collectors.joining;


/**
 * An abstract base class used to share functionality between different view
 * components such as {@link ClassView}, {@link EnumView} and 
 * {@link InterfaceView}.
 * 
 * @param <M>  the extending model type
 * @author     Emil Forslund
 */
abstract class ClassOrInterfaceView<M extends ClassOrInterface<M>> implements Transform<M, String>, 
        HasNameView<M>, 
        HasModifiersView<M>, 
        HasJavadocView<M>, 
        HasGenericsView<M>,
        HasImplementsView<M>, 
        HasInitializersView<M>, 
        HasMethodsView<M>,
        HasClassesView<M>, 
        HasAnnotationUsageView<M>, 
        HasFieldsView<M> {

	private static final Pattern EMPTY_BLOCK =
		Pattern.compile("^\\s*\\{\\s*}\\s*$");

	protected static final String CLASS_STRING      = "class ";
	protected static final String INTERFACE_STRING  = "interface ";
	protected static final String ENUM_STRING       = "enum ";
	protected static final String IMPLEMENTS_STRING = "implements ";
	protected static final String EXTENDS_STRING    = "extends ";

    /**
     * A hook that is executed just before the 'fields' part of the class code.
     * 
     * @param gen    the generator being used
     * @param model  the model that is generated
     * @return       code to be inserted before the fields
     */
	protected String onBeforeFields(Generator gen, M model) {
		return "";
	}

    @Override
    public String fieldSeparator(M model) {
        return nl();
    }

    @Override
    public String fieldSuffix() {
        return ";";
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

	@Override
	public Optional<String> transform(Generator gen, M model) {
        requireNonNulls(gen, model);

        String code = block(nl() + separate(
			onBeforeFields(gen, model), // Enums have constants here.´
			renderFields(gen, model),
			renderConstructors(gen, model),
			renderInitalizers(gen, model),
			renderMethods(gen, model),
			renderClasses(gen, model)
		));

		if (EMPTY_BLOCK.matcher(code).find()) {
			code = "{}";
		}
        
		return Optional.of(renderJavadoc(gen, model) +
            renderAnnotations(gen, model) +
			renderModifiers(gen, model) +
            renderDeclarationType() + 
            renderName(gen, model) + 
            renderGenerics(gen, model) + 
            (model.getGenerics().isEmpty() ? " " : "") +
            renderSupertype(gen, model) +
			renderInterfaces(gen, model) +
                
            // Code
            code
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