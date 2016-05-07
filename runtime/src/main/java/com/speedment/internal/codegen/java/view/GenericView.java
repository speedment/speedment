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
package com.speedment.internal.codegen.java.view;

import com.speedment.codegen.Generator;
import com.speedment.codegen.Transform;
import com.speedment.codegen.model.Generic;
import static com.speedment.internal.codegen.util.Formatting.*;
import static com.speedment.util.CollectorUtil.joinIfNotEmpty;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import static java.util.Objects.requireNonNull;

/**
 * Transforms from a {@link Generic} to java code.
 * 
 * @author Emil Forslund
 */
public final class GenericView implements Transform<Generic, String> {
    
	private final static String 
			EXTENDS_STRING = " extends ", 
			SUPER_STRING = " super ";

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Generic model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		if (!model.getLowerBound().isPresent() 
		&&   model.getUpperBounds().isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(
				model.getLowerBound().orElse(EMPTY) +
				gen.onEach(model.getUpperBounds()).collect(joinIfNotEmpty(AND, 
						model.getLowerBound().isPresent() ? 
							model.getBoundType() == Generic.BoundType.EXTENDS ?
							EXTENDS_STRING : SUPER_STRING
						: EMPTY, 
						EMPTY
					)
				)
			);
		}
	}
}