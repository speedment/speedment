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
