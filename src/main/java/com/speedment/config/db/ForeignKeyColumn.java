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
package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasColumn;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasOrdinalPosition;
import com.speedment.config.db.trait.HasParent;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.ForeignKeyColumnMutator;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface ForeignKeyColumn extends 
        Document,
        HasParent<ForeignKey>,
        HasName,
        HasOrdinalPosition,
        HasColumn,
        HasMainInterface,
        HasMutator<ForeignKeyColumnMutator> {

    final String 
        FOREIGN_TABLE_NAME = "foreignTableName",
        FOREIGN_COLUMN_NAME = "foreignColumnName";

    /**
     * Returns the name of the foreign column referenced by this column.
     *
     * @return the name of the foreign column
     */
    default String getForeignTableName() {
        return (String) get(FOREIGN_TABLE_NAME).get();
    }

    /**
     * Returns the name of the foreign table referenced by this column.
     *
     * @return the name of the foreign table
     */
    default String getForeignColumnName() {
        return (String) get(FOREIGN_COLUMN_NAME).get();
    }

    /**
     * A helper method for accessing the foreign {@link Table} referenced by
     * this key.
     * <p>
     * If the table was not found, a {@link SpeedmentException} is thrown.
     *
     * @return the foreign {@link Table} referenced by this
     */
    default Table findForeignTable() throws SpeedmentException {
        final Schema schema = (Schema) ancestors()
                .filter(doc -> Schema.class.isAssignableFrom(doc.getClass()))
                .findFirst()
                .orElseThrow(() -> new SpeedmentException(
                        "A foreign key in the config tree references a table that "
                        + "is not located in the same schema"
                ));

        return schema.tables()
                .filter(tab -> tab.getName().equals(getForeignTableName()))
                .findAny()
                .orElseThrow(() -> new SpeedmentException(
                        "A non-existing table '" + getForeignTableName() + "' was referenced."
                ));
    }

    /**
     * A helper method for accessing the foreign {@link Column} referenced by
     * this key.
     * <p>
     * If the column was not found, a {@link SpeedmentException} is thrown.
     *
     * @return the foreign {@link Column} referenced by this
     */
    default Column findForeignColumn() throws SpeedmentException {
        return findForeignTable()
                .columns()
                .filter(col -> col.getName().equals(getForeignColumnName()))
                .findAny()
                .orElseThrow(() -> new SpeedmentException(
                        "A non-existing column '" + getForeignColumnName()
                        + "' in table '" + getForeignTableName() + "' was referenced."
                ));
    }

    @Override
    default Class<ForeignKeyColumn> mainInterface() {
        return ForeignKeyColumn.class;
    }

    @Override
    default ForeignKeyColumnMutator mutator() {
        return DocumentMutator.of(this);
    }

}
