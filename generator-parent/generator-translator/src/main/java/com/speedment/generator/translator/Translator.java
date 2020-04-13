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

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Something that can translate a {@link Document} into something else. This
 * interface is implemented to generate more files from the same database
 * structure.
 *
 * @author       Per Minborg
 * @param <DOC>  the Document type to use
 * @param <T>    the codegen type to make (Class, Interface or Enum)
 * @see          Document
 * @since        2.3.0
 */
public interface Translator<DOC extends Document & HasMainInterface, T> extends Supplier<File> {

    /**
     * Return this node or any ancestral node that is a {@link Project}. If no
     * such node exists, an {@code Optional.empty()} is returned.
     *
     * @return the project node
     */
    default Optional<Project> project() {
        return getDocument(Project.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Dbms}. If no
     * such node exists, an {@code Optional.empty()} is returned.
     *
     * @return the dbms node
     */
    default Optional<Dbms> dbms() {
        return getDocument(Dbms.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Schema}. If no
     * such node exists, an {@code Optional.empty()} is returned.
     *
     * @return the schema node
     */
    default Optional<Schema> schema() {
        return getDocument(Schema.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Table}. If no
     * such node exists, an {@code Optional.empty()} is returned.
     *
     * @return the table node
     */
    default Optional<Table> table() {
        return getDocument(Table.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Column}. If no
     * such node exists, an {@code Optional.empty()} is returned.
     *
     * @return the column node
     */
    default Optional<Column> column() {
        return getDocument(Column.class);
    }

    /**
     * Returns a stream over all enabled columns in the node tree. Disabled
     * nodes will be ignored.
     *
     * @return the enabled columns
     * @see Column
     * @see HasEnabled#isEnabled()
     */
    default Stream<? extends Column> columns() {
        return table()
            .map(Table::columns)
            .orElse(Stream.empty())
            .filter(HasEnabled::test);
    }

    /**
     * Returns a stream over all enabled indexes in the node tree. Disabled
     * nodes will be ignored.
     *
     * @return the enabled indexes
     * @see Index
     * @see HasEnabled#isEnabled()
     */
    default Stream<? extends Index> indexes() {
        return table()
            .map(Table::indexes)
            .orElse(Stream.empty())
            .filter(HasEnabled::test);
    }

    /**
     * Returns a stream over all enabled foreign keys in the node tree. Disabled
     * nodes will be ignored.
     *
     * @return the enabled foreign keys
     * @see ForeignKey
     * @see HasEnabled#isEnabled()
     */
    default Stream<? extends ForeignKey> foreignKeys() {
        return table()
            .map(Table::foreignKeys)
            .orElse(Stream.empty())
            .filter(HasEnabled::test);
    }

    /**
     * Returns a stream over all enabled primary key columns in the node tree.
     * Disabled nodes will be ignored.
     *
     * @return the enabled primary key columns
     * @see PrimaryKeyColumn
     * @see HasEnabled#isEnabled()
     */
    default Stream<? extends PrimaryKeyColumn> primaryKeyColumns() {
        return table()
            .map(Table::primaryKeyColumns)
            .orElse(Stream.empty())
            .filter(HasEnabled::test);
    }
    
    /**
     * The document being translated.
     *
     * @return  the document
     */
    DOC getDocument();

    /**
     * Returns this node or one of the ancestor nodes if it matches the
     * specified {@code Class}. If no
     * such node exists, an {@code Optional.empty()} is returned.
     *
     * @param <E> the type of the class to match
     * @param clazz the class to match
     * @return the node found
     */
    default <E extends Document> Optional<E> getDocument(Class<E> clazz) {
        requireNonNull(clazz);
        if (clazz.isAssignableFrom(Translator.this.getDocument().mainInterface())) {
            @SuppressWarnings("unchecked")
            final E result = (E) Translator.this.getDocument();
            return Optional.of(result);
        }

        return Translator.this.getDocument()
            .ancestors()
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .findAny();
    }
    
    /**
     * The document being translated wrapped in a {@link HasAlias}.
     * 
     * @return  the document
     */
    default HasAlias getAliasDocument() {
        return HasAlias.of(Translator.this.getDocument());
    }
    
    /**
     * Generates code for the {@link Document} contained in this 
     * {@code Translator}.
     * 
     * @return  the generated meta info
     */
    default Meta<File, String> generate() {
        return getCodeGenerator().metaOn(
            get()).findFirst().orElseThrow(
                () -> new SpeedmentException("Unable to generate Java code")
            );
    }
    
    /**
     * Generates and returns the code translated from the contained 
     * {@link Document} by this {@code Translator}.
     * 
     * @return  the generated code
     */
    default String toCode() {
        return generate().getResult();
    }
    
    /**
     * Returns {@code true} if the file generated by this {@code Translator}
     * should be located in a {@code .generated.} package. Files located in
     * such a package will be overwritten each time the generator runs.
     * 
     * @return  {@code true} if located in {@code .generated.}, else {@code false}
     */
    boolean isInGeneratedPackage();
    
    /**
     * Returns the CodeGen {@link Generator} used by this {@code Translator}.
     * This can be used to add dependencies to the runtime or to install new
     * views.
     * 
     * @return  the {@link Generator} used 
     */
    Generator getCodeGenerator();
    
    /**
     * Append an additional action that should be executed as part of the make
     * process of this translator. This method is useful for adding additional
     * methods, fields or internal classes based on the configuration model.
     * <p>
     * If the action requires additional {@link Import} statements to
     * be added to the generated file, the {@link #onMake(BiConsumer)} method
     * should be used instead.
     * 
     * @param action  the action to perform
     * @see #onMake(BiConsumer)
     */
    default void onMake(Consumer<Builder<T>> action) {
        onMake((file, builder) -> action.accept(builder));
    }
 
    /**
     * Append an additional action that should be executed as part of the make
     * process of this translator. This method is useful for adding additional
     * methods, fields or internal classes based on the configuration model.
     * <p>
     * This method allows the action to affect both the {@link Builder} and the
     * file where the class or interface will be located. This should be used
     * if the specified action require additional {@link Import} statements to
     * be added to the file.
     * 
     * @param action  the action to perform
     * @see #onMake(BiConsumer)
     */
    void onMake(BiConsumer<File, Builder<T>> action);
    
    /**
     * Returns a {@code Stream} of the listeners currently part of the make 
     * process of this {@code Translator}.
     * 
     * @return  {@code Stream} of all listeners
     */
    Stream<BiConsumer<File, Builder<T>>> listeners();
    
    /**
     * The make process is divided into three phases; {@link #PRE_MAKE},
     * {@link #MAKE} and {@link #POST_MAKE}, as specified by this enum.
     * 
     * @author  Per Minborg
     * @since   2.3
     */
    enum Phase { PRE_MAKE, MAKE, POST_MAKE }
    
    /**
     * A general interface for all builder implementations used by this 
     * {@link Translator}. A builder is finalized using the {@link #build()} 
     * method.
     * 
     * @param <T>  {@link com.speedment.common.codegen.model.Class Class}, 
     *             {@link com.speedment.common.codegen.model.Interface Interface} or 
     *             {@link com.speedment.common.codegen.model.Enum Enum} to build.
     * 
     * @author  Emil Forslund
     * @since   2.3
     */
    interface Builder<T> {
        
        /**
         * Executes the specified code for every document of the specified type 
         * that is found during this build. This method could for an example be 
         * called to add additional methods or fields to the 
         * {@link ClassOrInterface} being built.
         * <p>
         * A key and constructor is specified so that the document of the 
         * unknown type can be constructed when the tree is traversed.
         * 
         * @param <P>          the type of the parent document
         * @param <DOC>        the type of the document
         * @param key          the key to react on
         * @param constructor  constructor for the document to react on
         * @param consumer     the code to apply
         * @return             a reference to this {@code Builder}
         */
        default <P extends Document, DOC extends Document> Builder<T> 
        forEvery(String key, BiFunction<P, Map<String, Object>, DOC> constructor, BiConsumer<T, DOC> consumer) {
            return forEvery(Phase.MAKE, key, constructor, consumer);
        }
        
        /**
         * Executes the specified code for every document of the specified type 
         * that is found during this build. This method could for an example be 
         * called to add additional methods or fields to the 
         * {@link ClassOrInterface} being built.
         * <p>
         * A key and constructor is specified so that the document of the 
         * unknown type can be constructed when the tree is traversed.
         * 
         * @param <P>          the type of the parent document
         * @param <DOC>        the type of the document
         * @param phase        the {@link Phase} to execute in
         * @param key          the key to react on
         * @param constructor  constructor for the document to react on
         * @param consumer     the code to apply
         * @return             a reference to this {@code Builder}
         */
        <P extends Document, DOC extends Document> Builder<T> 
        forEvery(Phase phase, String key, BiFunction<P, Map<String, Object>, DOC> constructor, BiConsumer<T, DOC> consumer);
        
        /**
         * Executes the specified code for the {@link Project} document of this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        default Builder<T> forEveryProject(BiConsumer<T, Project> consumer) {return forEveryProject(Phase.MAKE, consumer);}
        
        /**
         * Executes the specified code for the {@link Project} document of this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param phase     the {@link Phase} to execute in
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        Builder<T> forEveryProject(Phase phase, BiConsumer<T, Project> consumer);
        
        /**
         * Executes the specified code for every {@link Dbms} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        default Builder<T> forEveryDbms(BiConsumer<T, Dbms> consumer) {
            return forEveryDbms(Phase.MAKE, consumer);
        }
        
        /**
         * Executes the specified code for every {@link Dbms} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param phase     the {@link Phase} to execute in
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        Builder<T> forEveryDbms(Phase phase, BiConsumer<T, Dbms> consumer);
        
        /**
         * Executes the specified code for every {@link Schema} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        default Builder<T> forEverySchema(BiConsumer<T, Schema> consumer) {
            return forEverySchema(Phase.MAKE, consumer);
        }
        
        /**
         * Executes the specified code for every {@link Schema} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param phase     the {@link Phase} to execute in
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        Builder<T> forEverySchema(Phase phase, BiConsumer<T, Schema> consumer);
        
        /**
         * Executes the specified code for every {@link Table} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        default Builder<T> forEveryTable(BiConsumer<T, Table> consumer) {
            return forEveryTable(Phase.MAKE, consumer);
        }
        
        /**
         * Executes the specified code for every {@link Table} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param phase     the {@link Phase} to execute in
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        Builder<T> forEveryTable(Phase phase, BiConsumer<T, Table> consumer);
        
        /**
         * Executes the specified code for every {@link Column} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        default Builder<T> forEveryColumn(BiConsumer<T, Column> consumer) {
            return forEveryColumn(Phase.MAKE, consumer);
        }
        
        /**
         * Executes the specified code for every {@link Column} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param phase     the {@link Phase} to execute in
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        Builder<T> forEveryColumn(Phase phase, BiConsumer<T, Column> consumer);
        
        /**
         * Executes the specified code for every {@link Index} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        default Builder<T> forEveryIndex(BiConsumer<T, Index> consumer) {
            return forEveryIndex(Phase.MAKE, consumer);
        }
        
        /**
         * Executes the specified code for every {@link Index} document in this
         * build. This method could for an example be called to add additional
         * methods or fields to the {@link ClassOrInterface} being built.
         * 
         * @param phase     the {@link Phase} to execute in
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        Builder<T> forEveryIndex(Phase phase, BiConsumer<T, Index> consumer);
        
        /**
         * Executes the specified code for every {@link ForeignKey} document in 
         * this build. This method could for an example be called to add 
         * additional methods or fields to the {@link ClassOrInterface} being 
         * built.
         * 
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        default Builder<T> forEveryForeignKey(BiConsumer<T, ForeignKey> consumer) {
            return forEveryForeignKey(Phase.MAKE, consumer);
        }
        
        /**
         * Executes the specified code for every {@link ForeignKey} document in 
         * this build. This method could for an example be called to add 
         * additional methods or fields to the {@link ClassOrInterface} being 
         * built.
         * 
         * @param phase     the {@link Phase} to execute in
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        Builder<T> forEveryForeignKey(Phase phase, BiConsumer<T, ForeignKey> consumer);
        
        /**
         * Executes the specified code for every external {@link ForeignKey} 
         * document that is referencing this document during this build. This 
         * method could for an example be called to add additional methods or 
         * fields to the {@link ClassOrInterface} being built.
         * 
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        default Builder<T> forEveryForeignKeyReferencingThis(BiConsumer<T, ForeignKey> consumer) {
            return forEveryForeignKeyReferencingThis(Phase.MAKE, consumer);
        }

        /**
         * Executes the specified code for every external {@link ForeignKey} 
         * document that is referencing this document during this build. This 
         * method could for an example be called to add additional methods or 
         * fields to the {@link ClassOrInterface} being built.
         * 
         * @param phase     the {@link Phase} to execute in
         * @param consumer  the code to apply
         * @return          a reference to this {@code Builder}
         */
        Builder<T> forEveryForeignKeyReferencingThis(Phase phase, BiConsumer<T, ForeignKey> consumer);
        
        /**
         * Builds the {@link com.speedment.common.codegen.model.Class Class}, 
         * {@link com.speedment.common.codegen.model.Interface Interface} or 
         * {@link com.speedment.common.codegen.model.Enum Enum} that has been prepared
         * by this {@code Builder}.
         * 
         * @return  the built instance
         */
        T build();
    }
}