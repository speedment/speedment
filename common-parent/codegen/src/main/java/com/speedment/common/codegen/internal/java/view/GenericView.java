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
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.model.Generic;

import java.util.Optional;

import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;

/**
 * Transforms from a {@link Generic} to java code.
 * 
 * @author Emil Forslund
 */
public final class GenericView implements Transform<Generic, String> {

	@Override
	public Optional<String> transform(Generator gen, Generic model) {
        requireNonNulls(gen, model);
        
		if (!model.getLowerBound().isPresent() 
		&&   model.getUpperBounds().isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(
				model.getLowerBound().orElse("") +
				gen.onEach(model.getUpperBounds()).collect(joinIfNotEmpty("&", 
						model.getLowerBound().isPresent() ? 
							model.getBoundType() == Generic.BoundType.EXTENDS ?
							" extends " : " super "
						: "", 
						""
					)
				)
			);
		}
	}
}