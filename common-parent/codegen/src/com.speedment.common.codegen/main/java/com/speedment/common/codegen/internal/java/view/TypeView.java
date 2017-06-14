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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.DependencyManager;
import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import com.speedment.common.codegen.util.Formatting;
import static com.speedment.common.codegen.util.Formatting.shortName;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Transforms from a {@link Type} to java code.
 * 
 * @author Emil Forslund
 */
public final class TypeView implements Transform<Type, String> {

	@Override
	public Optional<String> transform(Generator gen, Type model) {
        requireNonNulls(gen, model);
        
		if (shouldUseShortName(gen, model)) {
			return Optional.of(shortName(renderTypeName(gen, model)));
		} else {
			return Optional.of(renderTypeName(gen, model));
		}
	}
    
    /**
     * Renders the type name of the specified type, including any type 
     * parameters it might have.
     * 
     * @param gen    the generator used
     * @param model  the model
     * @return       the rendered type name
     */
    private String renderTypeName(Generator gen, Type model) {
        final StringBuilder name = new StringBuilder();
        name.append(model.getTypeName().replace('$', '.'));
        
        if (model instanceof ParameterizedType) {
            final ParameterizedType hasTypes = (ParameterizedType) model;
            name.append(
                Stream.of(hasTypes.getActualTypeArguments())
                    .map(gen::on)
                    .map(Optional::get)
                    .collect(joinIfNotEmpty(", ", "<", ">"))
            );
        }
        
        return name.toString();
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
        final String name = Formatting.stripGenerics(type.getTypeName().replace('$', '.'));
        
        if (mgr.isIgnored(name)) {
            return true;
        }
        
        if (mgr.isLoaded(name)) {
            return true;
        }
        
        final Optional<String> current = mgr.getCurrentPackage();
		return current.isPresent() && name.startsWith(current.get());
	}
}