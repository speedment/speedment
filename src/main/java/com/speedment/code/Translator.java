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

import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import com.speedment.config.Document;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.config.db.trait.HasEnabled;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.Node;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Meta;
import static java.util.Objects.requireNonNull;

/**
 * A component that can translate a {@link Node} into something else. This
 * interface is implemented to generate more files from the same database
 * structure.
 *
 * @author pemi
 * @param <T> the ConfigEntity type to use
 * @param <R> the type to translate into
 * @see Node
 */
public interface Translator<T extends Document & HasMainInterface, R> extends Supplier<R> {

    /**
     * The node being translated.
     *
     * @return the node
     */
    T getNode();

    /**
     * Return this node or any ancestral node that is a {@link Project}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the project node
     */
    default Project project() {
        return getGenericConfigEntity(Project.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Dbms}. If no such
     * node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the dbms node
     */
    default Dbms dbms() {
        return getGenericConfigEntity(Dbms.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Schema}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the schema node
     */
    default Schema schema() {
        return getGenericConfigEntity(Schema.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Table}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the table node
     */
    default Table table() {
        return getGenericConfigEntity(Table.class);
    }

    /**
     * Return this node or any ancestral node that is a {@link Column}. If no
     * such node exists, an <code>IllegalStateException</code> is thrown.
     *
     * @return the column node
     */
    default Column column() {
        return getGenericConfigEntity(Column.class);
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
        return table().columns().filter(HasEnabled::test);
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
        return table().indexes().filter(HasEnabled::test);
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
        return table().foreignKeys().filter(HasEnabled::test);
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
        return table().primaryKeyColumns().filter(HasEnabled::test);
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
    default <E extends Document> E getGenericConfigEntity(Class<E> clazz) {
        requireNonNull(clazz);
        if (clazz.isAssignableFrom(getNode().mainInterface())) {
            @SuppressWarnings("unchecked")
            final E result = (E) getNode();
            return result;
        }

        return getNode()
                //.ancestor(clazz)
                .ancestors()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(
                        getNode() + " is not a " + clazz.getSimpleName()
                        + " and does not have a parent that is a " + clazz.getSimpleName()
                ));
    }
    
    default Meta<R, String> generate() {
        return getCodeGenerator().metaOn(get()).findFirst().orElseThrow(() -> new SpeedmentException("Unable to generate Java code"));
    }
    
    default String toCode() {
        return generate().getResult();
    }
    
    Generator getCodeGenerator();
}
