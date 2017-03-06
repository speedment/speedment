/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.DependencyManager;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.trait.*;
import com.speedment.common.codegen.util.Formatting;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;

/**
 * This control can be applied to a {@link File} to automatically add imports
 * for all types mentioned in the model hierarchy. Types mentioned inside
 * strings still need to be imported manually.
 * 
 * @author Emil Forslund
 */
public final class AutoImports implements Consumer<File> {
    
	private final DependencyManager mgr;
	
    /**
     * Initializes the {@code AutoImports}.
     * 
     * @param mgr  the dependency manager
     */
	public AutoImports(DependencyManager mgr) {
		this.mgr = requireNonNull(mgr);
	}
	
    /**
     * Adds explicit imports for all {@link Type} references mentioned in the
     * specified {@link File}.
     * 
     * @param file  the file to add imports in
     */
	@Override
	public void accept(File file) {
		findTypesIn(requireNonNull(file)).forEach(
			(s, t) -> file.add(Import.of(t))
		);
	}
	
    /**
     * Returns a map with all the types found in the specified model. The model
     * can be anything, but it will only find types if it inherits from any of
     * the supported traits.
     * <p>
     * The key of the map will be the name of the type.
     * 
     * @param model  the model
     * @return       the types found
     */
	private Map<String, Type> findTypesIn(Object model) {
		final Map<String, Type> map = new HashMap<>();
		findTypesIn(requireNonNull(model), map);
		return map;
	}
	
    /**
     * A recursive method that parses through the model hierarchy of the
     * specified object and adds all found types to the supplied map. The type
     * names will be used as keys.
     * 
     * @param model  the model to parse recursively
     * @param types  the map to add the results to
     */
	private void findTypesIn(Object model, Map<String, Type> types) {
        requireNonNull(model);
        requireNonNull(types);

		if (HasSupertype.class.isInstance(model)) {
			((HasSupertype<?>) model).getSupertype().ifPresent(t -> addType(t, types));
		}
		
		if (HasAnnotationUsage.class.isInstance(model)) {
			((HasAnnotationUsage<?>) model).getAnnotations().forEach(a -> 
				addType(a.getType(), types)
			);
		}
		
		if (HasClasses.class.isInstance(model)) {
			((HasClasses<?>) model).getClasses().forEach(c -> 
				findTypesIn(c, types)
			);
		}
		
		if (HasConstructors.class.isInstance(model)) {
			((HasConstructors<?>) model).getConstructors().forEach(c -> 
				findTypesIn(c, types)
			);
		}
		
		if (HasFields.class.isInstance(model)) {
			((HasFields<?>) model).getFields().forEach(f -> {
				addType(f.getType(), types);
				findTypesIn(f, types);
			});
		}
		
		if (HasGenerics.class.isInstance(model)) {
			((HasGenerics<?>) model).getGenerics().forEach(g -> 
				g.getUpperBounds().forEach(ub -> 
					addType(ub, types)
				)
			);
		}
		
		if (HasImplements.class.isInstance(model)) {
			((HasImplements<?>) model).getInterfaces().forEach(i -> 
				addType(i, types)
			);
		}
		
		if (HasMethods.class.isInstance(model)) {
			((HasMethods<?>) model).getMethods().forEach(m -> {
				addType(m.getType(), types);
				findTypesIn(m, types);
			});
		}
        
        if (HasThrows.class.isInstance(model)) {
			((HasThrows<?>) model).getExceptions().forEach(e -> 
				addType(e, types)
			);
		}
		
		if (HasType.class.isInstance(model)) {
			addType(((HasType<?>) model).getType(), types);
		}
	}
	
    /**
     * Add the specified {@link Type} to the supplied map. The key will be 
     * calculated using the type name. If the {@code Type} represents a
     * dependency that should be ignored according to the 
     * {@link DependencyManager}, it will not be added.
     * 
     * @param type   the type to try to add
     * @param types  the map to add it to
     */
	private void addType(Type type, Map<String, Type> types) {
        requireNonNull(type);
        requireNonNull(types);
        
		String name = type.getTypeName();
        
        // Strip any generic parts from the type name
        if (name.contains("<")) {
            name = name.substring(0, name.indexOf("<"));
        }
        
        // Strip any array parts from the type name
        if (name.contains("[")) {
            name = name.substring(0, name.indexOf("["));
        }

        // If the class is not a primitive type and it should be ignored, add
        // it to the ignore list.
		if (name.contains(".") && !mgr.isIgnored(name)) {
            final String shortName = Formatting.shortName(name);

            // If a import already exists with the same suffix, ignore it.
            if (types.keySet().stream()
                .map(Formatting::shortName)
                .noneMatch(shortName::equals)) {

                types.put(name, type);
            }

        }
        
        // Recurse over any type parameters this type might have.
        if (type instanceof ParameterizedType) {
            final ParameterizedType generic = (ParameterizedType) type;
            if (generic.getActualTypeArguments().length > 0) {
                findTypesIn(type, types);
                
                for (final Type genType : generic.getActualTypeArguments()) {
                    addType(genType, types);
                }
            }
        }
	}
}