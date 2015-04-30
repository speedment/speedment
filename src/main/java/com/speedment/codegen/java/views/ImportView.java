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

import com.speedment.util.CodeCombiner;
import com.speedment.codegen.lang.models.Import;
import java.util.Optional;
import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Type;

/**
 *
 * @author Emil Forslund
 */
public class ImportView implements Transform<Import, String> {
	private final static String IMPORT_STRING = "import ";

	@Override
	public Optional<String> transform(Generator cg, Import model) {
		if (shouldImport(cg, model.getType())) {
			return Optional.of(
				IMPORT_STRING +
				cg.onEach(model.getModifiers()).collect(CodeCombiner.joinIfNotEmpty(SPACE, EMPTY, SPACE)) +
				model.getType().getName() +
                model.getStaticMember().map(str -> DOT + str).orElse(EMPTY) +
				SC
			).filter(x -> {
				cg.getDependencyMgr().load(model.getType().getName());
				return true;
			});
		} else return Optional.empty();
	}
	
	private boolean shouldImport(Generator cg, Type type) {
        return cg.getRenderStack().fromBottom(File.class)
            .map(f -> fileToClassName(f.getName()))
            .filter(f -> f.isPresent())
            .map(f -> f.get())
            .filter(n -> {
                final Optional<String> pack = packageName(n);
                return !(pack.isPresent() && type.getName().startsWith(pack.get() + DOT));
            }).findAny().isPresent();
	}
}