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
package com.speedment.code;

import com.speedment.annotation.Api;
import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import com.speedment.config.Document;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasEnabled;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Meta;
import com.speedment.internal.codegen.lang.models.ClassOrInterface;
import com.speedment.internal.codegen.lang.models.File;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A component that can translate a {@link Document} into something else. This
 * interface is implemented to generate more files from the same database
 * structure.
 *
 * @author       pemi
 * @param <DOC>  the Document type to use
 * @param <T>    the codegen type to make (Class, Interface or Enum)
 * @see          Document
 * @since        2.3
 */
@Api(version = "2.3")
public interface Translator<DOC extends Document & HasMainInterface, T extends ClassOrInterface<T>> extends Supplier<File> {

    /**
     * The document being translated.
     *
     * @return  the document
     */
    DOC getDocument();
    
    /**
     * The document being translated wrapped in a {@link HasAlias}.
     * 
     * @return  the document
     */
    default HasAlias getAliasDocument() {
        return HasAlias.of(Translator.this.getDocument());
    }

    /**
     * Return this node or any ancestral node that is a {@link Project}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the project node
     */
    default Optional<Project> project() {
        return getDocument(Project.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Dbms}. If no such
     * node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the dbms node
     */
    default Optional<Dbms> dbms() {
        return getDocument(Dbms.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Schema}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the schema node
     */
    default Optional<Schema> schema() {
        return getDocument(Schema.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Table}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the table node
     */
    default Optional<Table> table() {
        return getDocument(Table.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Column}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
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
            .orElse(Stream.empty()).filter(HasEnabled::test);
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
     * Returns this node or one of the ancestor nodes if it matches the
     * specified <code>Class</code>. If no such node exists, an
     * <code>IllegalStateException</code> is thrown.
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
    
    default Meta<File, String> generate() {
        return getCodeGenerator().metaOn(get()).findFirst().orElseThrow(() -> new SpeedmentException("Unable to generate Java code"));
    }
    
    default String toCode() {
        return generate().getResult();
    }
    
    boolean isInGeneratedPackage();
    
    Generator getCodeGenerator();
    
    default void onMake(Consumer<Builder<T>> action) {
        onMake((file, builder) -> action.accept(builder));
    }
 
    void onMake(BiConsumer<File, Builder<T>> action);
    
    Stream<BiConsumer<File, Builder<T>>> listeners();
    
    interface Builder<T extends ClassOrInterface<T>> {
        <P extends Document, DOC extends Document> Builder<T> 
        forEvery(String key, BiFunction<P, Map<String, Object>, DOC> constructor, BiConsumer<T, DOC> consumer);
        
        Builder<T> forEveryProject(BiConsumer<T, Project> consumer);
        Builder<T> forEveryDbms(BiConsumer<T, Dbms> consumer);
        Builder<T> forEverySchema(BiConsumer<T, Schema> consumer);
        Builder<T> forEveryTable(BiConsumer<T, Table> consumer);
        Builder<T> forEveryColumn(BiConsumer<T, Column> consumer);
        Builder<T> forEveryIndex(BiConsumer<T, Index> consumer);
        Builder<T> forEveryForeignKey(BiConsumer<T, ForeignKey> consumer);
        Builder<T> forEveryForeignKeyReferencingThis(BiConsumer<T, ForeignKey> consumer);
        
        T build();
    }
}