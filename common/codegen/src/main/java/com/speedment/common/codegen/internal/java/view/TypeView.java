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
import com.speedment.common.codegen.internal.java.view.trait.HasGenericsView;
import com.speedment.common.codegen.model.Type;
import static com.speedment.common.codegen.internal.util.Formatting.*;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Transforms from a {@link Type} to java code.
 * 
 * @author Emil Forslund
 */
public final class TypeView implements Transform<Type, String>,
        HasGenericsView<Type> {
    
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Type model) {
        requireNonNulls(gen, model);
        
		if (shouldUseShortName(gen, model)) {
			return renderName(gen, model, shortName(model.getName()));
		} else {
			return renderName(gen, model, model.getName());
		}
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public String genericsSuffix() {
        return "";
    }
    
    /**
     * Renders the full name of the type with generics and array dimension. 
     * 
     * @param gen    the generator
     * @param model  the type
     * @param name   the name, short or full
     * @return       the generated name
     */
	private Optional<String> renderName(Generator gen, Type model, String name) {
        requireNonNulls(gen, model, name);
        
		return Optional.of(
			name.replace("$", ".") + 
            renderGenerics(gen, model) + 
			(model.getArrayDimension() > 0 
                ? Collections.nCopies(model.getArrayDimension(), "[]")
                    .stream().collect(Collectors.joining())
				: ""
			)
		);
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
        
        if (mgr.isIgnored(type.getName())) {
            return true;
        }
        
        if (mgr.isLoaded(type.getName())) {
            return true;
        }
        
        final Optional<String> current = mgr.getCurrentPackage();
		return current.isPresent() && type.getName().startsWith(current.get());

	}
}