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
package com.speedment.codegen.control;

import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.class_.Class_;
import com.speedment.codegen.model.dependency_.Dependency_;
import static com.speedment.codegen.CodeUtil.*;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.class_.ClassAndInterfaceBase;
import com.speedment.codegen.model.method.Method_;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Duncan
 */
public class AutomaticDependencies implements Controller<Class_> {
	@Override
	public void apply(Class_ model) {
		model.getAnnotations().stream()
			.filter(a -> isDependantOf(model, a.getAnnotationClass()))
			.forEach(a -> model.add(dependency(a)));
		
		// TODO: constructors can have dependencies.
		// TODO: super class is a dependency.
		// TODO: interfaces are dependencies.
		Stream.concat(
			model.getMethods().stream()
				.flatMap(m -> typesUsedIn(m).stream()),
			model.getFields().stream()
				.map(f -> f.getType())
		).filter(t -> isDependantOf(model, t))
		.forEach(t -> model.add(dependency(t)));
	}
	
	protected static Set<Type_> typesUsedIn(Method_ method) {
		final Set<Type_> types = method.getParameters().stream()
			.map(p -> p.getType())
			.collect(Collectors.toSet());
		
		types.add(method.getType());
		
		return types;
	}
	
	protected static boolean isDependantOf(ClassAndInterfaceBase model, Type_ type) {
		return isDependantOf(model, type.getTypeName().toString());
	}
	
	protected static boolean isDependantOf(ClassAndInterfaceBase model, Class<?> clazz) {
		return isDependantOf(model, clazz.getName());
	}
	
	protected static boolean isDependantOf(ClassAndInterfaceBase model, String typeName) {
		final String flatten = flattenName(model).toString();
		final String pack = packageName(flatten).toString();
		final String test = null;
		if (false) {}
		
		return !packageName(typeName)
			.toString()
			.equalsIgnoreCase(
				packageName(flattenName(model).toString())
				.toString()
			);
	}
	
	protected static Dependency_ dependency(Annotation_ anno) {
		return Dependency_.of(anno.getAnnotationClass());
	}
	
	protected static Dependency_ dependency(Type_ type) {
		return Dependency_.of(type.getTypeName());
	}
}