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

import com.speedment.codegen.lang.interfaces.Fieldable;
import com.speedment.codegen.lang.interfaces.Methodable;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Method;
import static com.speedment.codegen.lang.models.constants.DefaultType.*;
import java.util.function.Consumer;
import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.lang.interfaces.Importable;
import com.speedment.codegen.lang.interfaces.Nameable;
import com.speedment.codegen.lang.interfaces.Supertypeable;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.PARAM;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.RETURN;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 * @param <T>
 */
public class AutoEquals<T extends Fieldable<T>&Methodable<T>&Nameable<T>> implements Consumer<T> {

    private final Importable<?> importer;
    private final static String 
        EQUALS = "equals",
        HASHCODE = "hashCode";

    public AutoEquals(Importable<?> importer) {
        this.importer = importer;
    }
    
	@Override
	public void accept(T t) {
		if (!hasMethod(t, EQUALS, 1)) {
            if (importer != null) {
                importer.add(Import.of(Type.of(Objects.class)));
                importer.add(Import.of(Type.of(Optional.class)));
            }
            
			t.add(Method.of(EQUALS, BOOLEAN_PRIMITIVE)
                .set(
                    Javadoc.of(
                        "Compares this object with the specified one for equality.",
                        "The other object must be of the same type and not null for",
                        "the method to return true."
                    )
                    .add(PARAM.setValue("other").setText("The object to compare with."))
                    .add(RETURN.setText("True if the objects are equal."))
                ).public_()
                .add(OVERRIDE)
				.add(Field.of("other", OBJECT))
                .add("return Optional.ofNullable(other)")
                
                .call(m -> {
                    if (Supertypeable.class.isAssignableFrom(t.getClass())) {
                        final Optional<Type> supertype = ((Supertypeable<?>) t).getSupertype();
                        if (supertype.isPresent()) {
                            m.add(tab() + ".filter(o -> super.equals(o))");
                        }
                    }
                })
                
                .add(tab() + ".filter(o -> getClass().equals(o.getClass()))")
                .add(tab() + ".map(o -> (" + t.getName() + ") o)")
                .add(tab() + t.getFields().stream().map(f -> compare(f)).collect(
					Collectors.joining(nl() + tab())
				))
                .add(tab() + ".isPresent();")
			);
		}
		
		if (!hasMethod(t, HASHCODE, 0)) {
			t.add(Method.of(HASHCODE, INT_PRIMITIVE)
                .set(
                    Javadoc.of(
                        "Generates a hashCode for this object. If any field is ",
                        "changed to another value, the hashCode may be different. ",
                        "Two objects with the same values are garanteed to have ",
                        "the same hashCode. Two objects with the same hashCode are ",
                        "not garantueed to have the same hashCode."
                    )
                    .add(RETURN.setText("The hash code."))
                ).public_()
                .add(OVERRIDE)
				.add("int hash = 7;")
				.add(t.getFields().stream()
                    .map(f -> hash(f))
                    .collect(Collectors.joining(nl()))
                )
				.add("return hash;")
			);
		}
	}
	
	private String compare(Field f) {
        final StringBuilder str = new StringBuilder(".filter(o -> ");
		if (isPrimitive(f.getType())) {
			str.append("(this.")
               .append(f.getName())
               .append(" == o.")
               .append(f.getName())
               .append(")");
		} else {
			str.append("Objects.equals(this.")
               .append(f.getName())
               .append(", o.")
               .append(f.getName())
               .append(")");
		}
        
        return str.append(")").toString();
	}
	
	private String hash(Field f) {
		final String prefix = "hash = 31 * hash + ";
		final String suffix = ".hashCode(this." + f.getName() + ");";

		switch (f.getType().getName()) {
			case "byte" : return prefix + "Byte" + suffix;
			case "short" : return prefix + "Short" + suffix;
			case "int" : return prefix + "Integer" + suffix;
			case "long" : return prefix + "Long" + suffix;
			case "float" : return prefix + "Float" + suffix;
			case "double" : return prefix + "Double" + suffix;
			case "boolean" : return prefix + "Boolean" + suffix;
			case "char" : return prefix + "Character" + suffix;
			default: return prefix + "(this." + f.getName() + " == null) ? 0 : this." + f.getName() + ".hashCode();";
		}
	}
	
	private boolean isPrimitive(Type type) {
		switch (type.getName()) {
			case "byte" : case "short" : case "int" : case "long" : 
			case "float" : case "double" : case "boolean" : case "char" :
				return true;
			default:
				return false;
		}
	}
	
	private boolean hasMethod(T t, String method, int params) {
		Method found = null;
		
		for (Method m : t.getMethods()) {
			if (method.equals(m.getName())
			&&  m.getFields().size() == params) {
				found = m;
				break;
			}
		}
		
		return found != null;
	}
}
