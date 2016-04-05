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
package com.speedment.internal.codegen.java;

import com.speedment.codegen.TransformFactory;
import com.speedment.internal.codegen.DefaultDependencyManager;
import com.speedment.internal.codegen.DefaultGenerator;
import static com.speedment.util.NullUtil.requireNonNullElements;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A hook to the generator that can be passed to various stages in the pipeline.
 * Contains multiple methods for generating model-to-model or model-to-text.
 * <p>
 * The <code>JavaGenerator</code> comes with all the basic types
 * of the java language and the 'java.lang'-package ignored in imports and 
 * has views of all the basic language concepts preinstalled.
 * 
 * @author Emil Forslund
 */
public class JavaGenerator extends DefaultGenerator {
    
	private final static Pattern[] IGNORED = compileAll(
        "^void$",
		"^byte$",
        "^short$",
        "^char$",
        "^int$",
        "^long$",
        "^boolean$",
        "^float$",
        "^double$",
        "^java\\.lang\\."
    );
    
    /**
     * Instantiates the JavaGenerator.
     */
    public JavaGenerator() {
        this(new JavaTransformFactory());
    }
	
    /**
     * Instantiates the JavaGenerator using an array of custom 
     * {@link TransformFactory}.
     * <p>
     * Warning! If you use this constructor, no transforms will be installed
     * by default!
     * 
     * @param factories  an array of the factories to use
     */
	public JavaGenerator(TransformFactory... factories) {
		super(new DefaultDependencyManager(IGNORED), requireNonNullElements(factories));
	}

    private static Pattern[] compileAll(String... regexp) {
        final Set<Pattern> patterns = Stream.of(regexp)
            .map(Pattern::compile)
            .collect(Collectors.toSet());

        return patterns.toArray(new Pattern[patterns.size()]);
    }
}
