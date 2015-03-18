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
package com.speedment.codegen.lang.controller;

import com.speedment.codegen.Formatting;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.Method;
import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultType.OPTIONAL;
import com.speedment.codegen.lang.models.implementation.FieldImpl;
import com.speedment.codegen.lang.models.implementation.JavadocImpl;
import com.speedment.codegen.lang.models.implementation.JavadocTagImpl;
import com.speedment.codegen.lang.models.implementation.MethodImpl;
import com.speedment.codegen.lang.models.implementation.TypeImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 */
public class SetGetAdd implements Consumer<Class> {
	private final static String 
		SET = "set",
		GET = "get",
		THIS = "this.",
		ASSIGN = " = ",
		OPTIONAL_OF = "Optional.of(",
		RETURN = "return",
		RETURN_THIS = RETURN + " this",
		ADD = "add",
		ADD_TO = ".add(",
		THE = "the ",
		G = "G", S = "S", Y = "y",
		ETS_THE = "ets the ",
		ADDS_THE_SPECIFIED = "Adds the specified ",
		PARAM = "param",
		THE_NEW_VALUE = "the new value.",
		A_REFERENCE_TO_THIS = "a reference to this object.",
		OF_THIS = " of this ",
		BRACKETS = BS + BE;
	
	private final List<String> methods;
	
	public SetGetAdd() {
		this.methods = new ArrayList<>();
	}
	
	public SetGetAdd(Method... methods) {
		this.methods = Arrays.stream(methods)
			.map(m -> getSignature(m))
			.collect(Collectors.toList());
	}
	
	@Override
	public void accept(Class model) {
		model.getFields().stream().forEach(f -> {
			f.private_();
			
			if (isCollection(f.getType())) {
				f.final_();
				
				final Field param = new FieldImpl(singular(f.getName()), f.getType().getGenerics().get(0).getUpperBounds().get(0));
				final Method add = new MethodImpl(ADD, new TypeImpl(model.getName()))
					.set(new JavadocImpl()
						.add(ADDS_THE_SPECIFIED + lcfirst(shortName(param.getType().getName())) + " to this " + shortName(model.getName()) + DOT)
						.add(new JavadocTagImpl(PARAM, param.getName(), THE_NEW_VALUE))
						.add(new JavadocTagImpl(RETURN, A_REFERENCE_TO_THIS))
					).public_()
					.add(param)
					.add(THIS + f.getName() + ADD_TO + param.getName() + PE + SC)
					.add(RETURN_THIS + SC);
				
				if (includeMethod(model, add)) {
					model.add(add);
				}
			} else {
				final Method set = new MethodImpl(SET + ucfirst(f.getName()), new TypeImpl(model.getName()))
					.set(new JavadocImpl()
						.add(S + ETS_THE + f.getName() + OF_THIS + shortName(model.getName()) + DOT)
						.add(new JavadocTagImpl(PARAM, f.getName(), THE_NEW_VALUE))
						.add(new JavadocTagImpl(RETURN, A_REFERENCE_TO_THIS))
					).public_();
                
				if (isOptional(f.getType())) {
					set.add(new FieldImpl(f.getName(), f.getType().getGenerics().get(0).getUpperBounds().get(0)))
						.add(THIS + f.getName() + ASSIGN + OPTIONAL_OF + f.getName() + PE + SC)
						.add(RETURN_THIS + SC);
				} else {
					set.add(new FieldImpl(f.getName(), f.getType()))
						.add(THIS + f.getName() + ASSIGN + f.getName() + SC)
						.add(RETURN_THIS + SC);
				}
				
				if (includeMethod(model, set)) {
					model.add(set);
				}
			}
			
			final Method get = new MethodImpl(GET + ucfirst(f.getName()), f.getType())
				.set(new JavadocImpl()
					.add(G + ETS_THE + f.getName() + OF_THIS + shortName(model.getName()) + DOT)
					.add(new JavadocTagImpl(RETURN, THE + f.getName() + DOT))
				).public_()
				.add(RETURN_THIS + DOT + f.getName() + SC);
			
			if (includeMethod(model, get)) {
				model.add(get);
			}
		});
	}
	
	private boolean isCollection(Type type) {
		if (type.getJavaImpl().isPresent()) {
			return Collection.class.isAssignableFrom(type.getJavaImpl().get());
		} else {
			return false;
		}
	}
	
	private String singular(String name) {
		if (name.endsWith("ies")) {
			return name.substring(0, name.length() - 3) + Y;
		} else if (name.endsWith("s")) {
			return name.substring(0, name.length() - 1);
		} else {
			return name;
		}
	}
	
	private boolean isOptional(Type fieldType) {
		return fieldType.getName().equals(OPTIONAL.getName())
		&& !fieldType.getGenerics().isEmpty()
		&& !fieldType.getGenerics().get(0).getUpperBounds().isEmpty();
	}

	private boolean includeMethod(Class class_, Method method) {
		if (methods.isEmpty() || methods.contains(getSignature(method))) {
			return !class_.getMethods().stream().anyMatch(
					m -> getSignature(method).equals(getSignature(m))
			);
		} else {
			return false;
		}
	}
	
	private String getSignature(Method method) {
		return method.getName() + PS +
			method.getFields().stream().map(f -> 
				f.getType().getName() + 
				Formatting.repeat(BRACKETS, f.getType().getArrayDimension())
			).collect(Collectors.joining(COMMA)) + PE;
	}
}