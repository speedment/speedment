/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.config.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.*;
import static com.speedment.runtime.core.internal.util.ImmutableListUtil.concat;
import static com.speedment.runtime.core.internal.util.ImmutableListUtil.of;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import com.speedment.tool.config.DocumentProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Describes which implementations the {@link Document} interface to use at
 * different places in the config tree. This is purely used by the UI.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
@InjectKey(DocumentPropertyComponent.class)
public interface DocumentPropertyComponent {

    List<String>
        PROJECTS            = emptyList(),
        DBMSES              = singletonList(Project.DBMSES),
        SCHEMAS             = Stream.concat(DBMSES.stream(), Stream.of(Dbms.SCHEMAS)).collect(collectingAndThen(toList(), Collections::unmodifiableList)),
        TABLES              = Stream.concat(SCHEMAS.stream(), Stream.of(Schema.TABLES)).collect(collectingAndThen(toList(), Collections::unmodifiableList)),
        COLUMNS             = Stream.concat(TABLES.stream(), Stream.of(Table.COLUMNS)).collect(collectingAndThen(toList(), Collections::unmodifiableList)),
        PRIMARY_KEY_COLUMNS = Stream.concat(TABLES.stream(), Stream.of(Table.PRIMARY_KEY_COLUMNS)).collect(collectingAndThen(toList(), Collections::unmodifiableList)),
        FOREIGN_KEYS        = Stream.concat(TABLES.stream(), Stream.of(Table.FOREIGN_KEYS)).collect(collectingAndThen(toList(), Collections::unmodifiableList)),
        FOREIGN_KEY_COLUMNS = Stream.concat(FOREIGN_KEYS.stream(), Stream.of(ForeignKey.FOREIGN_KEY_COLUMNS)).collect(collectingAndThen(toList(), Collections::unmodifiableList)),
        INDEXES             = Stream.concat(TABLES.stream(), Stream.of(Table.INDEXES)).collect(collectingAndThen(toList(), Collections::unmodifiableList)),
        INDEX_COLUMNS       = Stream.concat(INDEXES.stream(), Stream.of(Index.INDEX_COLUMNS)).collect(collectingAndThen(toList(), Collections::unmodifiableList));

    /**
     * Functional interface that describes a constructor for an observable 
     * document.
     * 
     * @param <PARENT>  the parent type
     */
    @FunctionalInterface
    interface Constructor<PARENT extends DocumentProperty> {
        DocumentProperty create(PARENT parent);
    }

    /**
     * Sets the method used to produce an observable view of a branch in the 
     * tree. The specified {@code keyPath} describes where in the tree this
     * method should be used. The last string in the array is the key for the
     * collection that is added to.
     * <p>
     * <b>Example:</b>
     * <ul>
     *     <li><pre>setConstructor(ProjectProperty::new);</pre>
     *     <li><pre>setConstructor(DbmsProperty::new, "dbmses")</pre>
     *     <li><pre>setConstructor(SchemaProperty::new, "dbmses", "schemas");</pre>
     *     <li><pre>setConstructor(TableProperty::new, "dbmses", "schemas", "tables");</pre>
     * </ul>
     * 
     * @param <PARENT>     the type of the parent
     * @param constructor  the new constructor to use
     * @param keyPath      the path to the collection where to use it
     */
    <PARENT extends DocumentProperty> void setConstructor(Constructor<PARENT> constructor, List<String> keyPath);
    
    /**
     * Creates a new observable document using the installed constructor, at
     * the path specified by {@code keyPath}. To change the implementation, use 
     * {@link #setConstructor(Constructor, java.util.List) }.
     * 
     * @param <PARENT>  the parent type
     * @param keyPath   the path of the constructor
     * @return          the created document
     */
    <PARENT extends DocumentProperty> Constructor<PARENT> getConstructor(List<String> keyPath);
}