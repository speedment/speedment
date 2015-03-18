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
package com.speedment.codegen.base;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class DefaultInstaller implements Installer {
	private final Set<Map.Entry<Class<?>, Class<? extends CodeView<?>>>> modelToView;
	private final String name;
    
	public DefaultInstaller(String name) {
        this.name = name;
		this.modelToView = new HashSet<>();
	}
    
    @Override
    public String getName() {
        return name;
    }

	@Override
	public <M, V extends CodeView<M>> Installer install(Class<M> model, Class<V> view) {
		modelToView.add(new SimpleImmutableEntry<>(model, view));
        return this;
	}

	@Override
    @SuppressWarnings("unchecked")
	public <M> Stream<CodeView<M>> withAll(Class<M> model) {
		return modelToView.stream()
			.filter(e -> e.getKey().isAssignableFrom(model))
			.map(e -> (CodeView<M>) Installer.create(e.getValue()));
	}
}