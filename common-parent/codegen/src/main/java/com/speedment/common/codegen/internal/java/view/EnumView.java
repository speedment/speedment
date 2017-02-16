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

import com.speedment.common.codegen.Generator;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.util.Formatting;
import static com.speedment.common.codegen.util.Formatting.dnl;
import static com.speedment.common.codegen.util.Formatting.nl;
import java.util.List;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Transforms from an {@link Enum} to java code.
 * 
 * @author Emil Forslund
 */
public final class EnumView extends ClassOrInterfaceView<Enum> {

    @Override
	protected String renderDeclarationType() {
		return ENUM_STRING;
	}

	@Override
	public String extendsOrImplementsInterfaces() {
		return IMPLEMENTS_STRING;
	}

	@Override
	protected String renderSupertype(Generator gen, Enum model) {
		return "";
	}

	@Override
	protected String onBeforeFields(Generator gen, Enum model) {
        requireNonNulls(gen, model);
        
        final List<String> constants = model.getConstants().stream()
			.map(c -> gen.on(c).get()).collect(toList());
        
        Formatting.alignTabs(constants);
        
		return constants.stream().collect(
				joinIfNotEmpty(
					(!model.getConstants().isEmpty()
					&& !model.getConstants().get(0).getValues().isEmpty())
                        ? "," + nl() : ", ", 
					"", 
					";"
				)
			);
	}

    @Override
    protected String renderConstructors(Generator gen, Enum model) {
        requireNonNulls(gen, model);
        
        return gen.onEach(model.getConstructors())
            .collect(joining(dnl()));
    }
}