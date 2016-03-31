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
import com.speedment.config.db.mutator.DocumentMutator;
import com.speedment.config.db.mutator.ForeignKeyColumnMutator;
import static com.speedment.internal.util.document.DocumentUtil.newNoSuchElementExceptionFor;
import java.util.Optional;

/**
 * A typed {@link Document} that represents the column referenced by a foreign
 * key instance in the database. A {@code ForeignKeyColumn} is located inside a
 * {@link ForeignKey}.
 *
 * @author Emil Forslund
 * @since 2.0
 */
@Api(version = "2.3")
public interface ForeignKeyColumn extends
    Document,
    HasParent<ForeignKey>,
    HasName,
    HasOrdinalPosition,
    HasColumn,
    HasMainInterface,
    HasMutator<ForeignKeyColumnMutator<? extends ForeignKeyColumn>> {

    final String FOREIGN_TABLE_NAME = "foreignTableName",
        FOREIGN_COLUMN_NAME = "foreignColumnName";

    /**
     * Returns the name of the foreign column referenced by this column.
     *
     * @return the name of the foreign column
     */
    default String getForeignTableName() {
        return getAsString(FOREIGN_TABLE_NAME)
            .orElseThrow(newNoSuchElementExceptionFor(this, FOREIGN_TABLE_NAME));
    }

    /**
     * Returns the name of the foreign table referenced by this column.
     *
     * @return the name of the foreign table
     */
    default String getForeignColumnName() {
        return getAsString(FOREIGN_COLUMN_NAME)
            .orElseThrow(newNoSuchElementExceptionFor(this, FOREIGN_COLUMN_NAME));
    }

    /**
     * A helper method for accessing the foreign {@link Table} referenced by
     * this key.
     * <p>
     * If the table was not found, a {@link SpeedmentException} is thrown.
     *
     * @return the foreign {@link Table} referenced by this
     */
    default Optional<? extends Table> findForeignTable() {
        final Optional<Schema> schema = ancestors()
            .filter(Schema.class::isInstance)
            .map(Schema.class::cast)
            .findFirst();

        return schema.flatMap(s -> s.tables()
            .filter(tab -> tab.getName().equals(getForeignTableName()))
            .findAny()
        );
    }

    /**
     * A helper method for accessing the foreign {@link Column} referenced by
     * this key.
     * <p>
     * If the column was not found, a {@link SpeedmentException} is thrown.
     *
     * @return the foreign {@link Column} referenced by this
     */
    default Optional<? extends Column> findForeignColumn() {
        return findForeignTable()
            .flatMap(table -> table.columns()
                .filter(col -> col.getName().equals(getForeignColumnName()))
                .findAny()
            );
    }

    @Override
    default Class<ForeignKeyColumn> mainInterface() {
        return ForeignKeyColumn.class;
    }

    @Override
    default ForeignKeyColumnMutator<? extends ForeignKeyColumn> mutator() {
        return DocumentMutator.of(this);
    }
}
