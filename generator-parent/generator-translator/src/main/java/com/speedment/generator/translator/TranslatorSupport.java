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
package com.speedment.generator.translator;

import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.injector.Injector;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasPackageName;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Supplier;

import static com.speedment.common.codegen.util.Formatting.shortName;
import static com.speedment.common.codegen.util.Formatting.ucfirst;
import static com.speedment.runtime.config.ProjectUtil.DEFAULT_PACKAGE_NAME;
import static com.speedment.runtime.config.util.DocumentUtil.Name.JAVA_NAME;
import static java.util.Objects.requireNonNull;

/**
 * A support class for the {@link Translator} interface that holds various
 * naming methods used in the translator implementations.
 * <p>
 * This class might be refactored later on to separate methods that require the
 * document and those that do not in separate files.
 * 
 * @param <DOC>  the document type
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class TranslatorSupport<DOC extends Document & HasName & HasMainInterface> {
    
    public static final String IMPL_SUFFIX       = "Impl";
    public static final String MANAGER_SUFFIX    = "Manager";
    public static final String GENERATED_PACKAGE = "generated";
    public static final String GENERATED_PREFIX  = "Generated";
    public static final String SQL_ADAPTER_SUFFIX= "SqlAdapter";

    private  static final String MUST_HAVE_A = " must have a ";
    private static final String DOCUMENT_TEXT = " document.";

    private final DOC document;
    private final Injector injector;
    
    public TranslatorSupport(Injector injector, DOC document) {
        this.document = requireNonNull(document);
        this.injector = requireNonNull(injector);
    }
    
    public JavaLanguageNamer namer() {
        return injector.getOrThrow(JavaLanguageNamer.class);
    }
    
    public Type typeOf(Column column) {
        return injector.getOrThrow(TypeMapperComponent.class)
            .get(column).getJavaType(column);
    }
    
    protected DOC document() {
        return document;
    }
    
    public String entityName() {
        return shortName(entityType().getTypeName());
    }
    
    public String entityImplName() {
        return shortName(entityImplType().getTypeName());
    }
    
    public String generatedEntityName() {
        return shortName(generatedEntityType().getTypeName());
    }
    
    public String generatedEntityImplName() {
        return shortName(generatedEntityImplType().getTypeName());
    }
    
    public String managerName() {
        return shortName(managerType().getTypeName());
    }
    
    public String managerImplName() {
        return shortName(managerImplType().getTypeName());
    }
    
    public String generatedManagerName() {
        return shortName(generatedManagerType().getTypeName());
    }
    
    public String generatedManagerImplName() {
        return shortName(generatedManagerImplType().getTypeName());
    }
    
    public String sqlAdapterName() {
        return shortName(sqlAdapterType().getTypeName());
    }
    
    public String generatedSqlAdapterName() {
        return shortName(generatedSqlAdapterType().getTypeName());
    }
    
    public Type entityType() {
        return SimpleType.create(fullyQualifiedTypeName());
    }
    
    public Type entityImplType() {
        return SimpleType.create(fullyQualifiedTypeName() + IMPL_SUFFIX);
    }
    
    public Type generatedEntityType() {
        return SimpleType.create(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX));
    }
    
    public Type generatedEntityImplType() {
        return SimpleType.create(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + IMPL_SUFFIX);
    }
    
    public Type managerType() {
        return SimpleType.create(fullyQualifiedTypeName() + MANAGER_SUFFIX);
    }
    
    public Type managerImplType() {
        return SimpleType.create(fullyQualifiedTypeName() + MANAGER_SUFFIX + IMPL_SUFFIX);
    }
    
    public Type generatedManagerType() {
        return SimpleType.create(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + MANAGER_SUFFIX);
    }
    
    public Type generatedManagerImplType() {
        return SimpleType.create(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + MANAGER_SUFFIX + IMPL_SUFFIX);
    }
    
    public Type sqlAdapterType() {
        return SimpleType.create(fullyQualifiedTypeName() + SQL_ADAPTER_SUFFIX);
    }
    
    public Type generatedSqlAdapterType() {
        return SimpleType.create(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + SQL_ADAPTER_SUFFIX);
    }

    /**
     * Returns the alias of the current document formatted as a java variable.
     * <p>
     * Example:
     * <ul>
     *      <li>{@code employeesSchema}
     *      <li>{@code userTable}
     *      <li>{@code firstname}
     * </ul>
     *
     * @return  the document name as a variable
     * 
     * @see #document()
     */
    public String variableName() {
        return variableName(HasAlias.of(document));
    }

    /**
     * Returns the alias of the specified document formatted as a java variable.
     * <p>
     * Example:
     * <ul>
     *      <li>{@code employeesSchema}
     *      <li>{@code userTable}
     *      <li>{@code firstname}
     * </ul>
     *
     * @param doc  the document to retrieve the name from.
     * @return     the node name as a variable
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
     *      <li>{@code EmployeesSchema}
     *      <li>{@code UserTable}
     *      <li>{@code Firstname}
     * </ul>
     *
     * @return  the document alias as a type
     * @see #document()
     */
    public String typeName() {
        return typeName(HasAlias.of(document));
    }

    /**
     * Returns the alias of the specified document formatted as a java type.
     * <p>
     * Example:
     * <ul>
     *      <li>{@code EmployeesSchema}
     *      <li>{@code UserTable}
     *      <li>{@code Firstname}
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
     *      <li>{@code EmployeesSchema}
     *      <li>{@code UserTable}
     *      <li>{@code Firstname}
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
     *      <li>{@code EmployeesSchemaManager}
     *      <li>{@code UserTableManager}
     *      <li>{@code FirstnameManager}
     * </ul>
     *
     * @return  the document alias as a manager type
     * @see #document()
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
     *      <li>{@code EmployeesSchemaManager}
     *      <li>{@code UserTableManager}
     *      <li>{@code FirstnameManager}
     * </ul>
     *
     * @param doc  the document to retrieve the alias from
     * @return     the document alias as a manager type
     */
    public String managerTypeName(HasAlias doc) {
        return typeName(doc) + MANAGER_SUFFIX;
    }
    
    /**
     * Returns the fully qualified type name of the current document.
     * <p>
     * Example:
     * <ul>
     *      <li>{@code com.speedment.example.employeesschema.EmployeesSchema}
     *      <li>{@code com.speedment.example.usertable.UserTable}
     *      <li>{@code com.speedment.example.usertable.firstname.Firstname}
     * </ul>
     * <p>
     * Note that this method is only meant to work with documents at
     * {@link Table} or higher level in the hierarchy. It will return a
     * result for all documents located in a valid hierarchy, but the result 
     * might not be as intended.
     *
     * @return  the fully qualified type name of the current document
     * @see
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.5.2">
     *      Concerning fully qualified type names
     * </a>
     */
    public String fullyQualifiedTypeName() {
        return fullyQualifiedTypeName(null);
    }

    /**
     * Returns the fully qualified type name of the current document. The 
     * specified sub-path will be added after the base package name and before 
     * the type name of the node. The sub-path should not contain either 
     * leading nor trailing dots.
     * <p>
     * Example:
     * <ul>
     *      <li>{@code com.speedment.example.employeesschema.EmployeesSchema}
     *      <li>{@code com.speedment.example.usertable.UserTable}
     *      <li>{@code com.speedment.example.usertable.firstname.Firstname}
     * </ul>
     * <p>
     * Note that this method is only meant to work with nodes at
     * {@code Table} or higher level in the hierarchy. It will return a
     * result for all documents located in a valid hierarchy, but the result 
     * might not be as intended.
     *
     * @param subPath  A sub-path to be added at the end of the 'package'-part 
     *                 of the qualified type name. This value can be 
     *                 {@code null} and in that case an ordinary 
     *                 {@code fullyQualifiedTypeName} will be returned.
     * @return         the fully qualified type name of the current document
     * @see
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.5.2">
     *      Concerning fully qualified type names
     * </a>
     */
    public String fullyQualifiedTypeName(String subPath) {
        return fullyQualifiedTypeName(subPath, "");
    }
    
    /**
     * Returns the fully qualified type name of the current document. The 
     * specified sub-path will be added after the base package name and before 
     * the type name of the node. The sub-path should not contain either leading 
     * nor trailing dots.
     * <p>
     * Example:
     * <ul>
     *      <li>{@code com.speedment.example.employeesschema.EmployeesSchema}
     *      <li>{@code com.speedment.example.usertable.UserTable}
     *      <li>{@code com.speedment.example.usertable.firstname.Firstname}
     * </ul>
     * <p>
     * Note that this method is only meant to work with nodes at {@code Table} 
     * or higher level in the hierarchy. It will return a result for all 
     * documents located in a valid hierarchy, but the result might not be as 
     * intended.
     *
     * @param subPath     a prefix that will be added to the "class"-part of the
     *                    type name
     * @param filePrefix  a sub-path to be added at the end of the 
     *                    'package'-part of the qualified type name. This value 
     *                    can be {@code null} and in that case an ordinary 
     *                    {@code fullyQualifiedTypeName} will be returned.
     * @return            the fully qualified type name of the current document
     * @see
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.5.2">
     *      Concerning fully qualified type names
     * </a>
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
     * <p>
     * If the user has specified a custom package in the config file, that will
     * be returned. Otherwise a package name will ge generated using the default
     * settings.
     *
     * @return  the base package name in lowercase
     */
    public String basePackageName() {
        try {
            return Optional.of(document())
                .map(HasPackageName.class::cast)
                .flatMap(HasPackageName::getPackageName)
                .orElseGet(this::defaultPackageName);
        } catch (final ClassCastException ex) {
            throw new SpeedmentException(
                "The method basePackageName() may only be called on " +
                "instances of TranslatorSupport that have a document that " +
                "implements HasPackageName.",
                ex
            );
        }
    }
    
    /**
     * Returns the default package name where the document would be located if
     * the user has not specified a custom package in the config file.
     * 
     * @return  the default package name
     */
    public String defaultPackageName() {
        final Supplier<String> defaultProjectPackage = () ->
            DEFAULT_PACKAGE_NAME + 
            namer().javaPackageName(projectOrThrow().getCompanyName()) + "." +
            namer().javaPackageName(projectOrThrow().getName());
        
        if (document() instanceof Project) {
            return defaultProjectPackage.get();
        } else {
            return projectOrThrow().getPackageName()
                .orElseGet(defaultProjectPackage) + "." +
                    DocumentUtil.relativeName(
                        document(), 
                        Dbms.class, 
                        JAVA_NAME, 
                        namer()::javaPackageName
                    );
        }
    }
    
    /**
     * Returns the base directory name of the current node. It is the same as
     * returned by {@link #basePackageName()} but with dashes ('/') instead of
     * dots ('.').
     *
     * @return  the base package name.
     */
    public String baseDirectoryName() {
        return basePackageName().replace(".", "/");
    }

    /**
     * Return this node or any ancestral node that is a {@link Project}. If no
     * such node exists, an empty {@code Optional} is returned.
     *
     * @return  the project node
     */
    public Optional<Project> project() {
        return documentOfType(Project.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Dbms}. If no such
     * node exists, an empty {@code Optional} is returned.
     *
     * @return  the dbms node
     */
    public Optional<Dbms> dbms() {
        return documentOfType(Dbms.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Schema}. If no
     * such node exists, an empty {@code Optional} is returned.
     *
     * @return  the schema node
     */
    public Optional<Schema> schema() {
        return documentOfType(Schema.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Table}. If no
     * such node exists, an empty {@code Optional} is returned.
     *
     * @return  the table node
     */
    public Optional<Table> table() {
        return documentOfType(Table.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Column}. If no
     * such node exists, an empty {@code Optional} is returned.
     *
     * @return  the column node
     */
    public Optional<Column> column() {
        return documentOfType(Column.class);
    }
    
    /**
     * Return this node or any ancestral node that is a {@link Project}. If no
     * such node exists, an {@code IllegalStateException} is thrown.
     * 
     * @return  the project node
     * @throws IllegalStateException  if there was no project
     */
    public Project projectOrThrow() {
        return project().orElseThrow(() -> new IllegalStateException(
                getClass().getSimpleName() + MUST_HAVE_A
                + Project.class.getSimpleName() + DOCUMENT_TEXT
        ));
    }
    
    /**
     * Return this node or any ancestral node that is a {@link Dbms}. If no
     * such node exists, an {@code IllegalStateException} is thrown.
     * 
     * @return  the dbms node
     * @throws IllegalStateException  if there was no dbms
     */
    public Dbms dbmsOrThrow() {
        return dbms().orElseThrow(() -> new IllegalStateException(
                getClass().getSimpleName() + MUST_HAVE_A
                + Dbms.class.getSimpleName() + DOCUMENT_TEXT
        ));
    }
    
    /**
     * Return this node or any ancestral node that is a {@link Schema}. If no
     * such node exists, an {@code IllegalStateException} is thrown.
     * 
     * @return  the schema node
     * @throws IllegalStateException  if there was no schema
     */
    public Schema schemaOrThrow() {
        return schema().orElseThrow(() -> new IllegalStateException(
                getClass().getSimpleName() + MUST_HAVE_A
                + Schema.class.getSimpleName() + DOCUMENT_TEXT
        ));
    }
    
    /**
     * Return this node or any ancestral node that is a {@link Table}. If no
     * such node exists, an {@code IllegalStateException} is thrown.
     * 
     * @return  the table node
     * @throws IllegalStateException  if there was no table
     */
    public Table tableOrThrow() {
        return table().orElseThrow(() -> new IllegalStateException(
                getClass().getSimpleName() + MUST_HAVE_A
                + Table.class.getSimpleName() + DOCUMENT_TEXT
        ));
    }

    /**
     * Return this node or any ancestral node that is a {@link Column}. If no
     * such node exists, an {@code IllegalStateException} is thrown.
     * 
     * @return  the column node
     * @throws IllegalStateException  if there was no column
     */
    public Column columnOrThrow() {
        return column().orElseThrow(() -> new IllegalStateException(
                getClass().getSimpleName() + MUST_HAVE_A
                + Column.class.getSimpleName() + DOCUMENT_TEXT
        ));
    }

    /**
     * Returns this node or one of the ancestor nodes if it matches the
     * specified {@code Class}. If no such node exists, an
     * {@code IllegalStateException} is thrown.
     *
     * @param <E>    the type of the class to match
     * @param clazz  the class to match
     * @return       the node found
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