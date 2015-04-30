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

import static com.speedment.codegen.Formatting.DOT;
import com.speedment.codegen.base.DependencyManager;
import com.speedment.codegen.lang.interfaces.HasAnnotationUsage;
import com.speedment.codegen.lang.interfaces.HasClasses;
import com.speedment.codegen.lang.interfaces.HasConstructors;
import com.speedment.codegen.lang.interfaces.HasThrows;
import com.speedment.codegen.lang.interfaces.HasFields;
import com.speedment.codegen.lang.interfaces.HasGenerics;
import com.speedment.codegen.lang.interfaces.HasImplements;
import com.speedment.codegen.lang.interfaces.HasMethods;
import com.speedment.codegen.lang.interfaces.HasSupertype;
import com.speedment.codegen.lang.interfaces.HasType;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.implementation.ImportImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author Emil Forslund
 */
public class AutoImports implements Consumer<File> {
	private final DependencyManager mgr;
	
	public AutoImports(DependencyManager mgr) {
		this.mgr = mgr;
	}
	
	@Override
	public void accept(File file) {
		findTypesIn(file).forEach((s, t) -> {
			file.add(new ImportImpl(t));
		});
	}
	
	private Map<String, Type> findTypesIn(Object o) {
		final Map<String, Type> map = new HashMap<>();
		findTypesIn(o, map);
		return map;
	}
	
	private void findTypesIn(Object o, Map<String, Type> types) {
		if (HasSupertype.class.isAssignableFrom(o.getClass())) {
			((HasSupertype<?>) o).getSupertype().ifPresent(t -> addType(t, types));
		}
		
		if (HasAnnotationUsage.class.isAssignableFrom(o.getClass())) {
			((HasAnnotationUsage<?>) o).getAnnotations().forEach(a -> {
				addType(a.getType(), types);
			});
		}
		
		if (HasClasses.class.isAssignableFrom(o.getClass())) {
			((HasClasses<?>) o).getClasses().forEach(c -> {
				findTypesIn(c, types);
			});
		}
		
		if (HasConstructors.class.isAssignableFrom(o.getClass())) {
			((HasConstructors<?>) o).getConstructors().forEach(c -> {
				findTypesIn(c, types);
			});
		}
		
		if (HasFields.class.isAssignableFrom(o.getClass())) {
			((HasFields<?>) o).getFields().forEach(f -> {
				addType(f.getType(), types);
				findTypesIn(f, types);
			});
		}
		
		if (HasGenerics.class.isAssignableFrom(o.getClass())) {
			((HasGenerics<?>) o).getGenerics().forEach(g -> {
				g.getUpperBounds().forEach(ub -> {
					addType(ub, types);
				});
			});
		}
		
		if (HasImplements.class.isAssignableFrom(o.getClass())) {
			((HasImplements<?>) o).getInterfaces().forEach(i -> {
				addType(i, types);
			});
		}
		
		if (HasMethods.class.isAssignableFrom(o.getClass())) {
			((HasMethods<?>) o).getMethods().forEach(m -> {
				addType(m.getType(), types);
				findTypesIn(m, types);
			});
		}
        
        if (HasThrows.class.isAssignableFrom(o.getClass())) {
			((HasThrows<?>) o).getExceptions().forEach(e -> {
				addType(e, types);
			});
		}
		
		if (HasType.class.isAssignableFrom(o.getClass())) {
			addType(((HasType<?>) o).getType(), types);
		}
	}
	
	private void addType(Type type, Map<String, Type> types) {
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