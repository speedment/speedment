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
import com.speedment.codegen.base.CodeView;
import com.speedment.codegen.lang.models.Import;
import java.util.Optional;
import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Type;
import java.util.List;

/**
 *
 * @author Emil Forslund
 */
public class ImportView implements CodeView<Import> {
	private final static String IMPORT_STRING = "import ";

	@Override
	public Optional<String> render(CodeGenerator cg, Import model) {
		if (shouldImport(cg, model.getType())) {
			return Optional.of(
				IMPORT_STRING +
				cg.onEach(model.getModifiers()).collect(CodeCombiner.joinIfNotEmpty(SPACE, EMPTY, SPACE)) +
				model.getType().getName() +
				SC
			).filter(x -> {
				cg.getDependencyMgr().load(model.getType().getName());
				return true;
			});
		} else return Optional.empty();
	}
	
	private boolean shouldImport(CodeGenerator cg, Type type) {
		final List<Object> stack = cg.getRenderStack();
		if (stack.size() >= 2) {
			final Object parent = stack.get(0);
			if (parent instanceof File) {
				final Optional<String> name = fileToClassName(((File) parent).getName());
				if (name.isPresent()) {
					final Optional<String> pack = packageName(name.get());
					return !(pack.isPresent() && type.getName().startsWith(pack.get() + DOT));
				}
			} else {
				throw new UnsupportedOperationException("Import is at the wrong location in the model hierarchy.");
			}
		}
		
		return false;
	}
}