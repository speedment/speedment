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

import com.speedment.annotation.Api;
import com.speedment.config.aspects.Parent;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Enableable;
import com.speedment.internal.core.config.ForeignKeyImpl;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.1")
public interface ForeignKey extends Node, Enableable, Child<Table>, Parent<ForeignKeyColumn> {

    /**
     * Factory holder.
     */
    enum Holder {
        HOLDER;
        private Supplier<ForeignKey> provider = ForeignKeyImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param provider the new constructor
     */
    static void setSupplier(Supplier<ForeignKey> provider) {
        Holder.HOLDER.provider = requireNonNull(provider);
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     *
     * @return the new instance
     */
    static ForeignKey newForeignKey() {
        return Holder.HOLDER.provider.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<ForeignKey> getInterfaceMainClass() {
        return ForeignKey.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Table> getParentInterfaceMainClass() {
        return Table.class;
    }

    /**
     * Creates and adds a new {@link ForeignKeyColumn} as a child to this node
     * in the configuration tree.
     *
     * @return the newly added child
     */
    ForeignKeyColumn addNewForeignKeyColumn();

    /**
     * Creates and returns a new ForeignKeyColumn.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new ForeignKeyColumn
     */
    public ForeignKeyColumn foreignKeyColumn(Closure<?> c);
}