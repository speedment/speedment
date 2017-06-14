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
import com.speedment.common.codegen.internal.java.view.trait.HasClassesView;
import com.speedment.common.codegen.internal.java.view.trait.HasImportsView;
import com.speedment.common.codegen.internal.java.view.trait.HasJavadocView;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.util.Formatting;

import java.util.Optional;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.common.codegen.util.Formatting.*;

/**
 * Transforms from a {@link File} to java code.
 * 
 * @author Emil Forslund
 */
public final class FileView implements Transform<File, String>, 
        HasImportsView<File>,    
        HasJavadocView<File>, 
        HasClassesView<File> {

	@Override
	public Optional<String> transform(Generator gen, File model) {
        requireNonNulls(gen, model);
        
		final DependencyManager mgr = gen.getDependencyMgr();
        mgr.clearDependencies();
        
        final String pack = fileToClassName(model.getName())
            .flatMap(Formatting::packageName)
            .orElse("");
        
        mgr.setCurrentPackage(pack);

		final Optional<String> view = Optional.of(
			renderJavadoc(gen, model) +
			renderPackage(model) +
            renderImports(gen, model) +
            renderClasses(gen, model)
		);
		
		mgr.unsetCurrentPackage(pack);
		
		return view;
	}
    
    /**
     * Renders the 'package'-part of the file. In java, the package should only
     * be present if the file is located in a directory in the sources folder.
     * If the file is in the global package, an empty string will be returned.
     * <p>
     * The package part will be suffixed by two new line characters if a package
     * was outputted.
     * <p>
     * Example: <pre>"package com.speedment.example;\n\n"</pre>
     * 
     * @param file  the file
     * @return      the package part or an empty string
     */
    private String renderPackage(File file) {
		final Optional<String> name = fileToClassName(file.getName());
		if (name.isPresent()) {
			final Optional<String> pack = packageName(name.get());
			if (pack.isPresent()) {
				return "package " + pack.get() + ";" + dnl();
			}
		}
		
		return "";
	}
}