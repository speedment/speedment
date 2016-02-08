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
package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.AbstractDocumentProperty;
import com.speedment.internal.ui.config.DocumentProperty;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Describes which implementations the {@link Document} interface to use at
 * different places in the config tree. This is purely used by the UI.
 * 
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version = "2.3")
public interface DocumentPropertyComponent extends Component {
    
    /**
     * Returns a new string array with the specified trail added to the end of
     * the specified array. If trail is null, the original array is returned.
     * 
     * @param keys   the array to concatenate
     * @param trail  the string to append
     * @return       the new array
     */
    static String[] concat(String[] keys, String trail) {
        if (trail == null) {
            return keys;
        } else {
            final String[] newArray = Arrays.copyOf(keys, keys.length + 1);
            newArray[keys.length] = trail;
            return newArray;
        }
    }
    
    final String[]
        PROJECTS            = {},
        DBMSES              = {Project.DBMSES},
        SCHEMAS             = concat(DBMSES, Dbms.SCHEMAS),
        TABLES              = concat(SCHEMAS, Schema.TABLES),
        COLUMNS             = concat(TABLES, Table.COLUMNS),
        PRIMARY_KEY_COLUMNS = concat(TABLES, Table.PRIMARY_KEY_COLUMNS),
        FOREIGN_KEYS        = concat(TABLES, Table.FOREIGN_KEYS),
        FOREIGN_KEY_COLUMNS = concat(FOREIGN_KEYS, ForeignKey.FOREIGN_KEY_COLUMNS),
        INDEXES             = concat(TABLES, Table.INDEXES),
        INDEX_COLUMNS       = concat(INDEXES, Index.INDEX_COLUMNS);
    
    /**
     * Functional interface that describes a constructor for an observable 
     * document.
     * 
     * @param <PARENT>  the parent type
     */
    @FunctionalInterface
    public interface Constructor<PARENT extends DocumentProperty> {
        AbstractDocumentProperty create(PARENT parent);
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    default Class<? extends Component> getComponentClass() {
        return DocumentPropertyComponent.class;
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
    <PARENT extends DocumentProperty> void setConstructor(Constructor<PARENT> constructor, String... keyPath);
    
    /**
     * Creates a new observable document using the installed constructor, at
     * the path specified by {@code keyPath}. To change the implementation, use 
     * {@link #setConstructor(Constructor, java.lang.String...) }.
     * 
     * @param <PARENT>  the parent type
     * @param keyPath   the path of the constructor
     * @return          the created document
     */
    <PARENT extends DocumentProperty> Constructor<PARENT> getConstructor(String... keyPath);
}