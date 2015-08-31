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
import com.speedment.internal.codegen.base.DependencyManager;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.java.views.interfaces.HasClassesView;
import com.speedment.internal.codegen.java.views.interfaces.HasJavadocView;
import com.speedment.internal.codegen.java.views.interfaces.HasImportsView;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.util.Formatting;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * Transforms from a {@link File} to java code.
 * 
 * @author Emil Forslund
 */
public final class FileView implements Transform<File, String>, HasJavadocView<File>, 
    HasClassesView<File>, HasImportsView<File> {
    
	private final static String PACKAGE_STRING = "package ";

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, File model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		final DependencyManager mgr = gen.getDependencyMgr();
        mgr.clearDependencies();
        
        final String pack = fileToClassName(model.getName())
                                .flatMap(Formatting::packageName)
                                .orElse(EMPTY);
        
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
				return PACKAGE_STRING + pack.get() + scdnl();
			}
		}
		
		return EMPTY;
	}
}