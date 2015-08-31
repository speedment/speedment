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

import static com.speedment.internal.codegen.util.Formatting.DOT;
import com.speedment.internal.codegen.base.DependencyManager;
import com.speedment.internal.codegen.lang.interfaces.HasAnnotationUsage;
import com.speedment.internal.codegen.lang.interfaces.HasClasses;
import com.speedment.internal.codegen.lang.interfaces.HasConstructors;
import com.speedment.internal.codegen.lang.interfaces.HasThrows;
import com.speedment.internal.codegen.lang.interfaces.HasFields;
import com.speedment.internal.codegen.lang.interfaces.HasGenerics;
import com.speedment.internal.codegen.lang.interfaces.HasImplements;
import com.speedment.internal.codegen.lang.interfaces.HasMethods;
import com.speedment.internal.codegen.lang.interfaces.HasSupertype;
import com.speedment.internal.codegen.lang.interfaces.HasType;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.implementation.ImportImpl;
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
     * Initializes the <code>AutoImports</code>.
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
		findTypesIn(requireNonNull(file)).forEach((s, t) -> {
			file.add(new ImportImpl(t));
		});
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
        
		if (HasSupertype.class.isAssignableFrom(model.getClass())) {
			((HasSupertype<?>) model).getSupertype().ifPresent(t -> addType(t, types));
		}
		
		if (HasAnnotationUsage.class.isAssignableFrom(model.getClass())) {
			((HasAnnotationUsage<?>) model).getAnnotations().forEach(a -> {
				addType(a.getType(), types);
			});
		}
		
		if (HasClasses.class.isAssignableFrom(model.getClass())) {
			((HasClasses<?>) model).getClasses().forEach(c -> {
				findTypesIn(c, types);
			});
		}
		
		if (HasConstructors.class.isAssignableFrom(model.getClass())) {
			((HasConstructors<?>) model).getConstructors().forEach(c -> {
				findTypesIn(c, types);
			});
		}
		
		if (HasFields.class.isAssignableFrom(model.getClass())) {
			((HasFields<?>) model).getFields().forEach(f -> {
				addType(f.getType(), types);
				findTypesIn(f, types);
			});
		}
		
		if (HasGenerics.class.isAssignableFrom(model.getClass())) {
			((HasGenerics<?>) model).getGenerics().forEach(g -> {
				g.getUpperBounds().forEach(ub -> {
					addType(ub, types);
				});
			});
		}
		
		if (HasImplements.class.isAssignableFrom(model.getClass())) {
			((HasImplements<?>) model).getInterfaces().forEach(i -> {
				addType(i, types);
			});
		}
		
		if (HasMethods.class.isAssignableFrom(model.getClass())) {
			((HasMethods<?>) model).getMethods().forEach(m -> {
				addType(m.getType(), types);
				findTypesIn(m, types);
			});
		}
        
        if (HasThrows.class.isAssignableFrom(model.getClass())) {
			((HasThrows<?>) model).getExceptions().forEach(e -> {
				addType(e, types);
			});
		}
		
		if (HasType.class.isAssignableFrom(model.getClass())) {
			addType(((HasType<?>) model).getType(), types);
		}
	}
	
    /**
     * Add the specified {@link Type} to the supplied map. The key will be 
     * calculated using the type name. If the <code>Type</code> represents a
     * dependency that should be ignored according to the 
     * {@link DependencyManager}, it will not be added.
     * 
     * @param type   the type to try to add
     * @param types  the map to add it to
     */
	private void addType(Type type, Map<String, Type> types) {
        requireNonNull(type);
        requireNonNull(types);
        
		final String name = type.getName();

		if (name.contains(DOT)) {
			if (!mgr.isIgnored(name)) {
				types.put(name, type);
			}
		}
        
        if (!type.getGenerics().isEmpty()) {
            findTypesIn(type, types);
        }
	}
}