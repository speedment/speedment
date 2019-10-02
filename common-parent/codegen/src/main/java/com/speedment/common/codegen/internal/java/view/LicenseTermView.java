/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.LicenseTerm;

import java.util.*;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.common.codegen.util.Formatting.*;
import static java.util.stream.Collectors.joining;

/**
 * Transforms from a {@link Javadoc} to java code.
 * 
 * @author Emil Forslund
 */
public final class LicenseTermView implements Transform<LicenseTerm, String> {
    
	private static final String LICENSE_DELIMITER = nl() + " * ";
    private static final String LICENSE = "/*" + nl() + " * ";
    private static final String LICENSE_SUFFIX = nl() + " */";

	@Override
	public Optional<String> transform(Generator gen, LicenseTerm model) {
        requireNonNulls(gen, model);

		return Optional.of(
            LICENSE +
            Stream.of(model.getText().split(nl()))
                .filter(s -> !s.isEmpty())
                .collect(joining(dnl()))
                .replace(nl(), LICENSE_DELIMITER) +
                LICENSE_SUFFIX
        );
	}
    

}