/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.util.code;

import com.speedment.Speedment;
import com.speedment.codegen.lang.models.Type;
import com.speedment.config.Document;
import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.exception.SpeedmentException;
import static com.speedment.internal.codegen.util.Formatting.shortName;
import static com.speedment.internal.codegen.util.Formatting.ucfirst;
import com.speedment.internal.util.document.DocumentUtil;
import com.speedment.util.JavaLanguageNamer;
import java.util.Optional;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class TranslatorSupport<DOC extends Document & HasName & HasMainInterface> {
    
    public final static String 
        IMPL_SUFFIX       = "Impl",
        MANAGER_SUFFIX    = "Manager",
        GENERATED_PACKAGE = "generated",
        GENERATED_PREFIX  = "Generated";
    
    private final Speedment speedment;
    private final DOC document;
    
    public TranslatorSupport(Speedment speedment, DOC document) {
        this.speedment = requireNonNull(speedment);
        this.document  = requireNonNull(document);
    }
    
    public JavaLanguageNamer namer() {
        return speedment.getCodeGenerationComponent().javaLanguageNamer();
    }
    
    protected DOC document() {
        return document;
    }
    
    public String entityName() {
        return shortName(entityType().getName());
    }
    
    public String entityImplName() {
        return shortName(entityImplType().getName());
    }
    
    public String generatedEntityName() {
        return shortName(generatedEntityType().getName());
    }
    
    public String generatedEntityImplName() {
        return shortName(generatedEntityImplType().getName());
    }
    
    public String managerName() {
        return shortName(managerType().getName());
    }
    
    public String managerImplName() {
        return shortName(managerImplType().getName());
    }
    
    public String generatedManagerName() {
        return shortName(generatedManagerType().getName());
    }
    
    public String generatedManagerImplName() {
        return shortName(generatedManagerImplType().getName());
    }
    
    public Type entityType() {
        return Type.of(fullyQualifiedTypeName());
    }
    
    public Type entityImplType() {
        return Type.of(fullyQualifiedTypeName() + IMPL_SUFFIX);
    }
    
    public Type generatedEntityType() {
        return Type.of(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX));
    }
    
    public Type generatedEntityImplType() {
        return Type.of(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + IMPL_SUFFIX);
    }
    
    public Type managerType() {
        return Type.of(fullyQualifiedTypeName() + MANAGER_SUFFIX);
    }
    
    public Type managerImplType() {
        return Type.of(fullyQualifiedTypeName() + MANAGER_SUFFIX + IMPL_SUFFIX);
    }
    
    public Type generatedManagerType() {
        return Type.of(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + MANAGER_SUFFIX);
    }
    
    public Type generatedManagerImplType() {
        return Type.of(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + MANAGER_SUFFIX + IMPL_SUFFIX);
    }

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
    public String variableName() {
        return variableName(HasAlias.of(document));
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
    public String variableName(HasAlias doc) {
        requireNonNull(doc);
        return namer().javaVariableName(doc.getJavaName());
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
    public String typeName() {
        return typeName(HasAlias.of(document));
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
    public String typeName(HasAlias doc) {
        return namer().javaTypeName(requireNonNull(doc).getJavaName());
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
    public String typeName(Project project) {
        return namer().javaTypeName(requireNonNull(project).getName());
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
    public String managerTypeName() {
        return managerTypeName(HasAlias.of(document));
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
    public String managerTypeName(HasAlias doc) {
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
    public String fullyQualifiedTypeName() {
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
    public String fullyQualifiedTypeName(String subPath) {
        return fullyQualifiedTypeName(subPath, "");
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
     * @param subPath A prefix that will be added to the "class"-part of the
     * type name.
     * @param filePrefix A sub-path to be added at the end of the 'package'-part of
     * the qualified type name. This value can be {@code null} and in that
     * case an ordinary fullyQualifiedTypeName will be returned.
     * @return the fully qualified type name of the current document
     * @see
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.5.2">Concerning
     * fully qualified type names</a>
     */
    public String fullyQualifiedTypeName(String subPath, String filePrefix) {
        requireNonNull(filePrefix);
        
        // subPath is nullable
        if (subPath == null || subPath.isEmpty()) {
            return basePackageName() + "." + ucfirst(filePrefix) + typeName(HasAlias.of(document));
        } else {
            return basePackageName() + "." + subPath + "." + ucfirst(filePrefix) + typeName(HasAlias.of(document));
        }
    }
    
    /**
     * Returns the base package name of the current node. This is everything up
     * to but not including the type name. No trailing dot is added.
     *
     * @return the base package name in lowercase.
     */
    public String basePackageName() {
        final String packName = projectOrThrow().findPackageName(namer()) + ".";
        if (document() instanceof Project) {
            return packName + namer().javaPackageName(projectOrThrow().getName());
        } else {
            return packName + DocumentUtil.relativeName(document(), Project.class, namer()::javaPackageName);
        }
    }
    
    /**
     * Returns the base directory name of the current node. It is the same as
     * returned by {@link #basePackageName()} but with dashes ('/') instead of
     * dots ('.').
     *
     * @return the base package name.
     */
    public String baseDirectoryName() {
        return basePackageName().replace(".", "/");
    }

    /**
     * Return this node or any ancestral node that is a {@link Project}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the project node
     */
    public Optional<Project> project() {
        return documentOfType(Project.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Dbms}. If no such
     * node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the dbms node
     */
    public Optional<Dbms> dbms() {
        return documentOfType(Dbms.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Schema}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the schema node
     */
    public Optional<Schema> schema() {
        return documentOfType(Schema.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Table}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the table node
     */
    public Optional<Table> table() {
        return documentOfType(Table.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Column}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the column node
     */
    public Optional<Column> column() {
        return documentOfType(Column.class);
    }
    
    public Table tableOrThrow() {
        return table().orElseThrow(() -> new SpeedmentException(
                getClass().getSimpleName() + " must have a "
                + Table.class.getSimpleName() + " document."
        ));
    }

    public Schema schemaOrThrow() {
        return schema().orElseThrow(() -> new SpeedmentException(
                getClass().getSimpleName() + " must have a "
                + Schema.class.getSimpleName() + " document."
        ));
    }

    public Dbms dbmsOrThrow() {
        return dbms().orElseThrow(() -> new SpeedmentException(
                getClass().getSimpleName() + " must have a "
                + Dbms.class.getSimpleName() + " document."
        ));
    }
    
    public Project projectOrThrow() {
        return project().orElseThrow(() -> new SpeedmentException(
                getClass().getSimpleName() + " must have a "
                + Project.class.getSimpleName() + " document."
        ));
    }

    /**
     * Returns this node or one of the ancestor nodes if it matches the
     * specified <code>Class</code>. If no such node exists, an
     * <code>IllegalStateException</code> is thrown.
     *
     * @param <E> the type of the class to match
     * @param clazz the class to match
     * @return the node found
     */
    private <E extends Document & HasMainInterface> Optional<E> documentOfType(Class<E> clazz) {
        requireNonNull(clazz);
        if (clazz.isAssignableFrom(document().mainInterface())) {
            @SuppressWarnings("unchecked")
            final E result = (E) document();
            return Optional.of(result);
        }

        return document()
            .ancestors()
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .findAny();
    }
}