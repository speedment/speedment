/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.config;

import com.speedment.config.aspects.Ordinable;
import com.speedment.annotation.Api;
import com.speedment.annotation.External;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Columnable;
import com.speedment.config.aspects.Enableable;
import com.speedment.internal.core.config.ForeignKeyColumnImpl;
import com.speedment.exception.SpeedmentException;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.1")
public interface ForeignKeyColumn extends Node, Enableable, Ordinable, 
    Columnable, Child<ForeignKey> {

    /**
     * Factory holder.
     */
    enum Holder { HOLDER;
        private Supplier<ForeignKeyColumn> provider = ForeignKeyColumnImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     * 
     * @param provider  the new constructor 
     */
    static void setSupplier(Supplier<ForeignKeyColumn> provider) {
        Holder.HOLDER.provider = requireNonNull(provider);
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.

     * @return  the new instance
     */
    static ForeignKeyColumn newForeignKeyColumn() {
        return Holder.HOLDER.provider.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<ForeignKeyColumn> getInterfaceMainClass() {
        return ForeignKeyColumn.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<ForeignKey> getParentInterfaceMainClass() {
        return ForeignKey.class;
    }

    /**
     * Returns the name of the foreign column referenced by this column.
     * <p>
     * This property is editable in the GUI through reflection.
     * 
     * @return  the name of the foreign column
     */
    @External(type = String.class)
    String getForeignColumnName();

    /**
     * Sets the name of the foreign column referenced by this column.
     * <p>
     * This property is editable in the GUI through reflection.
     * 
     * @param foreignColumnName  the name of the foreign column
     */
    @External(type = String.class)
    void setForeignColumnName(String foreignColumnName);

    /**
     * Returns the name of the foreign table referenced by this column.
     * <p>
     * This property is editable in the GUI through reflection.
     * 
     * @return  the name of the foreign table
     */
    @External(type = String.class)
    String getForeignTableName();

    /**
     * Sets the name of the foreign table referenced by this column.
     * <p>
     * This property is editable in the GUI through reflection.
     * 
     * @param foreignTableName  the name of the foreign table
     */
    @External(type = String.class)
    void setForeignTableName(String foreignTableName);

    /**
     * A helper method for accessing the foreign {@link Column} referenced by
     * this key. 
     * <p>
     * The is equivalent to writing: {@code
     *     ConfigUtil.findColumnByName(
     *          getForeignTable(), 
     *          getForeignColumnName()
     *      )
     * }
     * <p>
     * If the column was not found, a {@link SpeedmentException} is thrown.
     * 
     * @return  the foreign {@link Column} referenced by this
     */
    Column getForeignColumn();

    /**
     * A helper method for accessing the foreign {@link Table} referenced by
     * this key. 
     * <p>
     * The is equivalent to writing: {@code
     *     ConfigUtil.findTableByName(
     *          ancestor(Schema.class).orElseThrow(
     *              thereIsNo(
     *                  Table.class, 
     *                  ForeignKeyColumn.class, 
     *                  getForeignTableName()
     *              )
     *          ), 
     *          getForeignTableName()
     *      );
     * }
     * <p>
     * If the table was not found, a {@link SpeedmentException} is thrown.
     * 
     * @return  the foreign {@link Table} referenced by this
     */
    Table getForeignTable();
}
