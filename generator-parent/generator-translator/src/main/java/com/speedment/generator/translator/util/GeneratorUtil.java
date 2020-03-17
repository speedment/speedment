/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.generator.translator.util;

import com.speedment.common.codegen.util.Formatting;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasAlias;

/**
 * Utility class used when generating code based on a Speedment configuration
 * model.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public final class GeneratorUtil {

    /**
     * Returns the name that will be given a Java type generated from the
     * specified document.
     *
     * @param doc  the document (like a {@link Table} or a {@link Column})
     * @return     the Java type-name
     */
    public static String typeName(HasAlias doc) {
        return Formatting.ucfirst(javaName(doc));
    }

    /**
     * Returns the name that will be given a Java variable generated from the
     * specified document.
     *
     * @param doc  the document (like a {@link Table} or a {@link Column})
     * @return     the Java variable-name
     */
    public static String varName(HasAlias doc) {
        return Formatting.lcfirst(javaName(doc));
    }

    /**
     * Returns the Java name of the specified document formatted using
     * camel-case. The case of the first letter is unspecified.
     *
     * @param doc  the document
     * @return     the Java name
     */
    private static String javaName(HasAlias doc) {
        return Formatting.javaNameFromExternal(doc.getJavaName());
    }

    /**
     * Utility classes should not be instantiated.
     */
    private GeneratorUtil() {}
}
