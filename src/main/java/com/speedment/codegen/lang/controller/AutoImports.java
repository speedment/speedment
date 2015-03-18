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
import com.speedment.codegen.lang.interfaces.Annotable;
import com.speedment.codegen.lang.interfaces.Classable;
import com.speedment.codegen.lang.interfaces.Constructable;
import com.speedment.codegen.lang.interfaces.Fieldable;
import com.speedment.codegen.lang.interfaces.Generable;
import com.speedment.codegen.lang.interfaces.Interfaceable;
import com.speedment.codegen.lang.interfaces.Methodable;
import com.speedment.codegen.lang.interfaces.Supertypeable;
import com.speedment.codegen.lang.interfaces.Typeable;
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
		if (Supertypeable.class.isAssignableFrom(o.getClass())) {
			((Supertypeable<?>) o).getSupertype().ifPresent(t -> addType(t, types));
		}
		
		if (Annotable.class.isAssignableFrom(o.getClass())) {
			((Annotable<?>) o).getAnnotations().forEach(a -> {
				addType(a.getType(), types);
			});
		}
		
		if (Classable.class.isAssignableFrom(o.getClass())) {
			((Classable<?>) o).getClasses().forEach(c -> {
				findTypesIn(c, types);
			});
		}
		
		if (Constructable.class.isAssignableFrom(o.getClass())) {
			((Constructable<?>) o).getConstructors().forEach(c -> {
				findTypesIn(c, types);
			});
		}
		
		if (Fieldable.class.isAssignableFrom(o.getClass())) {
			((Fieldable<?>) o).getFields().forEach(f -> {
				addType(f.getType(), types);
				findTypesIn(f, types);
			});
		}
		
		if (Generable.class.isAssignableFrom(o.getClass())) {
			((Generable<?>) o).getGenerics().forEach(g -> {
				g.getUpperBounds().forEach(ub -> {
					addType(ub, types);
				});
			});
		}
		
		if (Interfaceable.class.isAssignableFrom(o.getClass())) {
			((Interfaceable<?>) o).getInterfaces().forEach(i -> {
				addType(i, types);
			});
		}
		
		if (Methodable.class.isAssignableFrom(o.getClass())) {
			((Methodable<?>) o).getMethods().forEach(m -> {
				addType(m.getType(), types);
				findTypesIn(m, types);
			});
		}
		
		if (Typeable.class.isAssignableFrom(o.getClass())) {
			addType(((Typeable<?>) o).getType(), types);
		}
	}
	
	private void addType(Type type, Map<String, Type> types) {
		final String name = type.getName();
		if (name.contains(DOT)) {
			if (!mgr.isIgnored(name)) {
				types.put(name, type);
			}
		}
	}
}