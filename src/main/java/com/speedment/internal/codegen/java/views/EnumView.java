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
package com.speedment.internal.codegen.java.views;

import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.Enum;
import static com.speedment.internal.core.stream.CollectorUtil.joinIfNotEmpty;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Transforms from an {@link Enum} to java code.
 * 
 * @author Emil Forslund
 */
public final class EnumView extends ClassOrInterfaceView<Enum> {
	
    /**
     * {@inheritDoc}
     */
    @Override
	protected String renderDeclarationType() {
		return ENUM_STRING;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public String extendsOrImplementsInterfaces() {
		return IMPLEMENTS_STRING;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	protected String renderSupertype(Generator gen, Enum model) {
		return EMPTY;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	protected String onBeforeFields(Generator gen, Enum model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		return model.getConstants().stream()
			.map(c -> gen.on(c).get()).collect(
				joinIfNotEmpty(
					(!model.getConstants().isEmpty()
					&& !model.getConstants().get(0).getValues().isEmpty())
					? cnl() : COMMA_SPACE, 
					EMPTY, 
					SC
				)
			);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    protected String renderConstructors(Generator gen, Enum model) {
        requireNonNull(gen);
        requireNonNull(model);
        
        return gen.onEach(model.getConstructors())
            .collect(joining(dnl()));
    }
}