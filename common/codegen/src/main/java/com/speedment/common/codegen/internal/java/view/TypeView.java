/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.DependencyManager;
import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;

import java.util.Optional;

import static com.speedment.common.codegen.internal.util.Formatting.shortName;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import java.lang.reflect.Type;

/**
 * Transforms from a {@link Type} to java code.
 * 
 * @author Emil Forslund
 */
public final class TypeView implements Transform<Type, String> {
    
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Type model) {
        requireNonNulls(gen, model);
        
		if (shouldUseShortName(gen, model)) {
			return Optional.of(shortName(model.getTypeName()));
		} else {
			return Optional.of(model.getTypeName());
		}
	}
    
    /**
     * Returns whether or not to use the short name for the type.
     * 
     * @param gen   the generator
     * @param type  the type
     * @return      {@code true} if the short name should be used.
     */
    private boolean shouldUseShortName(Generator gen, Type type) {
        requireNonNulls(gen, type);
        
        final DependencyManager mgr = gen.getDependencyMgr();
        
        if (mgr.isIgnored(type.getTypeName())) {
            return true;
        }
        
        if (mgr.isLoaded(type.getTypeName())) {
            return true;
        }
        
        final Optional<String> current = mgr.getCurrentPackage();
		return current.isPresent() && type.getTypeName().startsWith(current.get());

	}
}