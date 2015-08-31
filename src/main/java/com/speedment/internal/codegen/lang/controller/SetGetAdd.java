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
package com.speedment.internal.codegen.lang.controller;

import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.internal.codegen.lang.models.Method;
import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.JavadocTag;
import com.speedment.internal.codegen.lang.models.Type;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.OPTIONAL;
import java.util.Collection;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import static java.util.Objects.requireNonNull;

/**
 * Control that is used to generate setters, getters and adders for fields in 
 * the specified class. 
 * 
 * @author Emil Forslund
 */
public final class SetGetAdd implements Consumer<Class> {
    
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
        TO_THIS = " to this ",
		PARAM = "param",
		THE_NEW_VALUE = "the new value.",
		A_REFERENCE_TO_THIS = "a reference to this object.",
		OF_THIS = " of this ",
		BRACKETS = BS + BE;
	
	private final BiPredicate<Field, Method> onlyInclude;
	
    /**
     * Initializes the control with all methods included.
     */
	public SetGetAdd() {
		onlyInclude = (f, m) -> true;
	}
	
    /**
     * Initializes the control but with only some {@link Method Methods} 
     * included. The input of the <code>BiPredicate</code> will be the field
     * in the class that the method is generated for and the suggested method
     * to add to the class.
     * 
     * @param onlyInclude  a filter for methods to include
     */
	public SetGetAdd(BiPredicate<Field, Method> onlyInclude) {
		this.onlyInclude = requireNonNull(onlyInclude);
	}
	
    /**
     * Generates getters, setters and adders for the fields in the specified 
     * model and sets the fields to private. Method signatures that has been 
     * excluded in the construction of this control will be excluded from the 
     * generation.
     * <p>
     * For fields of a subtype to {@link Collection}, an 'adder' will be
     * generated instead of a 'setter'.
     * <p>
     * For fields of type {@link Optional}, the inner type will be used in the
     * setter parameter but the wrapped type will be used as output for the
     * getter.
     * 
     * @param model  the model to generate for
     */
	@Override
	public void accept(Class model) {
		requireNonNull(model).getFields().forEach(f -> {
			f.private_();
			
			if (isCollection(f.getType())) {
				f.final_();
				
				final Field param = Field.of(singular(f.getName()), f.getType().getGenerics().get(0).getUpperBounds().get(0));
				final Method add = Method.of(ADD, Type.of(model.getName()))
					.set(Javadoc.of()
						.setText(ADDS_THE_SPECIFIED + lcfirst(shortName(param.getType().getName())) + TO_THIS + shortName(model.getName()) + DOT)
						.add(JavadocTag.of(PARAM, param.getName(), THE_NEW_VALUE))
						.add(JavadocTag.of(RETURN, A_REFERENCE_TO_THIS))
					).public_()
					.add(param)
					.add(THIS + f.getName() + ADD_TO + param.getName() + PE + SC)
					.add(RETURN_THIS + SC);
				
				if (onlyInclude.test(f, add)) {
					model.add(add);
				}
			} else {
				final Method set = Method.of(SET + ucfirst(f.getName()), Type.of(model.getName()))
					.set(Javadoc.of()
						.setText(S + ETS_THE + f.getName() + OF_THIS + shortName(model.getName()) + DOT)
						.add(JavadocTag.of(PARAM, f.getName(), THE_NEW_VALUE))
						.add(JavadocTag.of(RETURN, A_REFERENCE_TO_THIS))
					).public_();
                
				if (isOptional(f.getType())) {
					set.add(Field.of(f.getName(), f.getType().getGenerics().get(0).getUpperBounds().get(0)))
						.add(THIS + f.getName() + ASSIGN + OPTIONAL_OF + f.getName() + PE + SC)
						.add(RETURN_THIS + SC);
				} else {
					set.add(Field.of(f.getName(), f.getType()))
						.add(THIS + f.getName() + ASSIGN + f.getName() + SC)
						.add(RETURN_THIS + SC);
				}
				
				if (onlyInclude.test(f, set)) {
					model.add(set);
				}
			}
			
			final Method get = Method.of(GET + ucfirst(f.getName()), f.getType())
				.set(Javadoc.of()
					.setText(G + ETS_THE + f.getName() + OF_THIS + shortName(model.getName()) + DOT)
					.add(JavadocTag.of(RETURN, THE + f.getName() + DOT))
				).public_()
				.add(RETURN_THIS + DOT + f.getName() + SC);
			
			if (onlyInclude.test(f, get)) {
				model.add(get);
			}
		});
	}
	
    /**
     * Checks if the specified type is a java {@link Collection}.
     * 
     * @param type  the type to check
     * @return      <code>true</code> if collection, else <code>false</code>
     */
	private boolean isCollection(Type type) {
		if (requireNonNull(type).getJavaImpl().isPresent()) {
			return Collection.class.isAssignableFrom(type.getJavaImpl().get());
		} else {
			return false;
		}
	}
    
    /**
     * Checks if the specified type is a java {@link Optional}.
     * 
     * @param type  the type to check
     * @return      <code>true</code> if Optional, else <code>false</code>
     */
	private boolean isOptional(Type type) {
		return requireNonNull(type).getName().equals(OPTIONAL.getName())
		&& !type.getGenerics().isEmpty()
		&& !type.getGenerics().get(0).getUpperBounds().isEmpty();
	}
	
    /**
     * Returns the specified word in singular.
     * 
     * @param word  the word
     * @return      the word in singular
     */
	private String singular(String word) {
		if (requireNonNull(word).endsWith("ies")) {
			return word.substring(0, word.length() - 3) + Y;
		} else if (word.endsWith("s")) {
			return word.substring(0, word.length() - 1);
		} else {
			return word;
		}
	}
}