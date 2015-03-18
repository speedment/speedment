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
package com.speedment.codegen.java.views;

import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.base.CodeView;
import com.speedment.codegen.java.views.interfaces.AnnotableView;
import com.speedment.codegen.java.views.interfaces.ClassableView;
import com.speedment.codegen.java.views.interfaces.DocumentableView;
import com.speedment.codegen.java.views.interfaces.GenerableView;
import com.speedment.codegen.java.views.interfaces.InitalizableView;
import com.speedment.codegen.java.views.interfaces.InterfaceableView;
import com.speedment.codegen.java.views.interfaces.MethodableView;
import com.speedment.codegen.java.views.interfaces.ModifiableView;
import com.speedment.codegen.java.views.interfaces.NameableView;
import com.speedment.codegen.lang.interfaces.Constructable;
import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.Field;
import java.util.Optional;
import static com.speedment.util.CodeCombiner.*;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 *
 * @author Emil Forslund
 * @param <M>
 */
public abstract class ClassOrInterfaceView<M extends ClassOrInterface<M>> implements 
    CodeView<M>, NameableView<M>, ModifiableView<M>, DocumentableView<M>, 
    GenerableView<M>, InterfaceableView<M>, InitalizableView<M>, MethodableView<M>,
    ClassableView<M>, AnnotableView<M> {
    
	protected final static String
		CLASS_STRING = "class ",
		INTERFACE_STRING = "interface ",
		ENUM_STRING = "enum ",
		IMPLEMENTS_STRING = "implements ",
		EXTENDS_STRING = "extends ";

	protected String onBeforeFields(CodeGenerator cg, M model) {
		return EMPTY;
	}
	
	protected Object wrapField(Field field) {return field;}

	protected String onAfterFields(CodeGenerator cg, M model) {
		if (model instanceof Constructable) {
			return cg.onEach(
				((Constructable<?>) model).getConstructors()
			).collect(Collectors.joining(dnl()));
		} else {
			return EMPTY;
		}
	}
	
    @Override
	public <In, C extends Collection<In>> Collection<Object> 
		wrap(C models, Function<In, Object> wrapper) {
		return models.stream().map(wrapper).collect(Collectors.toList());
	}
	
    protected abstract String renderDeclarationType();
	protected abstract String renderSuperType(CodeGenerator cg, M model);

	@Override
	public Optional<String> render(CodeGenerator cg, M model) {
		return Optional.of(
			renderJavadoc(cg, model) +
            renderAnnotations(cg, model) +
			renderModifiers(cg, model) +
            renderDeclarationType() + 
            renderName(cg, model) + 
            renderGenerics(cg, model) +
            renderSuperType(cg, model) +
			renderInterfaces(cg, model) +
                
            // Code
			block(nl() + separate(
                // Fields
				onBeforeFields(cg, model), // Enums have constants here.
				cg.onEach(wrap(model.getFields(), f -> wrapField(f)))
					.collect(joinIfNotEmpty(scnl(), EMPTY, SC)),
				onAfterFields(cg, model),
                renderInitalizers(cg, model),
				renderMethods(cg, model),
				renderClasses(cg, model)
			))
		);
	}
	
	private String separate(Object... strings) {
		return Stream.of(strings)
			.map(o -> o.toString())
			.filter(s -> s.length() > 0)
			.collect(Collectors.joining(dnl()));
	}
}