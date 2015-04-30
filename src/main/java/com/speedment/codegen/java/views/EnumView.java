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
package com.speedment.codegen.java.views;

import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.Enum;
import com.speedment.util.CodeCombiner;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public class EnumView extends ClassOrInterfaceView<Enum> {
	
    @Override
	protected String renderDeclarationType() {
		return ENUM_STRING;
	}

	@Override
	public String extendsOrImplementsInterfaces() {
		return IMPLEMENTS_STRING;
	}

	@Override
	protected String renderSupertype(Generator cg, Enum model) {
		return EMPTY;
	}

	@Override
	protected String onBeforeFields(Generator cg, Enum model) {
		return model.getConstants().stream()
			.map(c -> cg.on(c).get()).collect(
				CodeCombiner.joinIfNotEmpty(
					(!model.getConstants().isEmpty()
					&& !model.getConstants().get(0).getValues().isEmpty())
					? cnl() : COMMA_SPACE, 
					EMPTY, 
					SC
				)
			);
	}

    @Override
    protected String renderConstructors(Generator cg, Enum model) {
        return cg.onEach(model.getConstructors())
            .collect(joining(dnl()));
    }
}
