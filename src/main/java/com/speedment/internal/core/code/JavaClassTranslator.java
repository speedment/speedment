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
package com.speedment.internal.core.code;

import com.speedment.code.Translator;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.config.db.Project;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.internal.util.JavaLanguageNamer;
import static com.speedment.internal.util.document.DocumentUtil.relativeName;
import static java.util.Objects.requireNonNull;
import static com.speedment.internal.util.document.DocumentUtil.relativeName;
import static java.util.Objects.requireNonNull;

/**
 * A more specific {@link Translator} that results in a CodeGen {@link File}.
 * The class contains many helper-functions to make the generation process
 * easier.
 *
 * @author pemi
 * @param <T> The Node type
 */
public interface JavaClassTranslator<T extends HasName & HasMainInterface> extends Translator<T, File> {

    
    /**
     * Returns the alias of the current document formatted as a java variable.
     * <p>
     * Example:
     * <ul>
     * <li><code>employeesSchema</code>
     * <li><code>userTable</code>
     * <li><code>firstname</code>
     * </ul>
     *
     * @return the document name as a variable
     * @see #getDocument()
     */
    default String variableName() {
        return variableName(getAliasDocument());
    }

    /**
     * Returns the alias of the specified document formatted as a java variable.
     * <p>
     * Example:
     * <ul>
     * <li><code>employeesSchema</code>
     * <li><code>userTable</code>
     * <li><code>firstname</code>
     * </ul>
     *
     * @param doc the document to retrieve the name from.
     * @return the node name as a variable
     */
    default String variableName(HasAlias doc) {
        requireNonNull(doc);
        return javaLanguageNamer().javaVariableName(doc.getJavaName());
    }

    /**
     * Returns the alias of the current document formatted as a java type.
     * <p>
     * Example:
     * <ul>
     * <li><code>EmployeesSchema</code>
     * <li><code>UserTable</code>
     * <li><code>Firstname</code>
     * </ul>
     *
     * @return the document alias as a type
     * @see #getDocument()
     */
    default String typeName() {
        return typeName(getAliasDocument());
    }

    /**
     * Returns the alias of the specified document formatted as a java type.
     * <p>
     * Example:
     * <ul>
     * <li><code>EmployeesSchema</code>
     * <li><code>UserTable</code>
     * <li><code>Firstname</code>
     * </ul>
     *
     * @param doc  the document to retrieve the alias from
     * @return     the document alias as a type
     */
    default String typeName(HasAlias doc) {
        return javaLanguageNamer().javaTypeName(requireNonNull(doc).getJavaName());
    }
    
    /**
     * Returns the name of the specified project formatted as a java type.
     * <p>
     * Example:
     * <ul>
     * <li><code>EmployeesSchema</code>
     * <li><code>UserTable</code>
     * <li><code>Firstname</code>
     * </ul>
     *
     * @param project  the document to retrieve the name from
     * @return         the project name as a type
     */
    default String typeName(Project project) {
        return javaLanguageNamer().javaTypeName(requireNonNull(project).getName());
    }

    /**
     * Returns the alias of the current document as a java type but with the 
     * keyword 'Manager' appended to it.
     * <p>
     * Example:
     * <ul>
     * <li><code>EmployeesSchemaManager</code>
     * <li><code>UserTableManager</code>
     * <li><code>FirstnameManager</code>
     * </ul>
     *
     * @return the document alias as a manager type
     * @see #getDocument()
     */
    default String managerTypeName() {
        return managerTypeName(getAliasDocument());
    }

    /**
     * Returns the alias of the specified document as a java type but with the
     * keyword 'Manager' appended to it.
     * <p>
     * Example:
     * <ul>
     * <li><code>EmployeesSchemaManager</code>
     * <li><code>UserTableManager</code>
     * <li><code>FirstnameManager</code>
     * </ul>
     *
     * @param doc  the document to retrieve the alias from
     * @return     the document alias as a manager type
     */
    default String managerTypeName(HasAlias doc) {
        return typeName(doc) + "Manager";
    }

    /**
     * Returns the fully qualified type name of the current document.
     * <p>
     * Example:
     * <ul>
     * <li><code>com.speedment.example.employeesschema.EmployeesSchema</code>
     * <li><code>com.speedment.example.usertable.UserTable</code>
     * <li><code>com.speedment.example.usertable.firstname.Firstname</code>
     * </ul>
     * <p>
     * Note that this method is only meant to work with documents at
     * {@code Table} or higher level in the hierarchy. It will return a
     * result for all documents located in a valid hierarchy, but the result 
     * might not be as intended.
     *
     * @return the fully qualified type name of the current document
     * @see
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.5.2">Concerning
     * fully qualified type names</a>
     */
    default String fullyQualifiedTypeName() {
        return fullyQualifiedTypeName(null);
    }

    /**
     * Returns the fully qualified type name of the current document. The specified
     * sub-path will be added after the base package name and before the type
     * name of the node. The sub-path should not contain either leading nor
     * trailing dots.
     * <p>
     * Example:
     * <ul>
     * <li><code>com.speedment.example.employeesschema.EmployeesSchema</code>
     * <li><code>com.speedment.example.usertable.UserTable</code>
     * <li><code>com.speedment.example.usertable.firstname.Firstname</code>
     * </ul>
     * <p>
     * Note that this method is only meant to work with nodes at
     * {@code Table} or higher level in the hierarchy. It will return a
     * result for all documents located in a valid hierarchy, but the result might
     * not be as intended.
     *
     * @param subPath A sub-path to be added at the end of the 'package'-part of
     * the qualified type name. This value can be {@code null} and in that
     * case an ordinary fullyQualifiedTypeName will be returned.
     * @return the fully qualified type name of the current document
     * @see
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.5.2">Concerning
     * fully qualified type names</a>
     */
    default String fullyQualifiedTypeName(String subPath) {
        // subPath is nullable
        if (subPath == null || subPath.isEmpty()) {
            return basePackageName() + "." + typeName(getAliasDocument());
        } else {
            return basePackageName() + "." + subPath + "." + typeName(getAliasDocument());
        }
    }

    /**
     * Returns the base package name of the current node. This is everything up
     * to but not including the type name. No trailing dot is added.
     *
     * @return the base package name in lowercase.
     */
    default String basePackageName() {
        final String packName = project().getPackageName().toLowerCase() + ".";
        if (getDocument() instanceof Project) {
            return packName + javaLanguageNamer().javaPackageName(project().getName());
        } else {
            return packName + relativeName(getDocument(), Project.class, javaLanguageNamer()::javaPackageName);
        }
    }

    /**
     * Returns the base directory name of the current node. It is the same as
     * returned by {@link #basePackageName()} but with dashes ('/') instead of
     * dots ('.').
     *
     * @return the base package name.
     */
    default String baseDirectoryName() {
        return basePackageName().replace(".", "/");
    }
    
    JavaLanguageNamer javaLanguageNamer();
    
}
