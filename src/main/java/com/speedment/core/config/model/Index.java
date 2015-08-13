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
package com.speedment.core.config.model;

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.aspects.Parent;
import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.core.config.model.impl.IndexImpl;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.0")
public interface Index extends Node, Enableable, Child<Table>, Parent<IndexColumn> {

    /**
     * Factory holder.
     */
    enum Holder { HOLDER;
        private Supplier<Index> provider = IndexImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     * 
     * @param provider  the new constructor 
     */
    static void setSupplier(Supplier<Index> provider) {
        Holder.HOLDER.provider = provider;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.

     * @return  the new instance
     */
    static Index newIndex() {
        return Holder.HOLDER.provider.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Index> getInterfaceMainClass() {
        return Index.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Table> getParentInterfaceMainClass() {
        return Table.class;
    }

    /**
     * Creates and adds a new {@link IndexColumn} as a child to this node in the 
     * configuration tree.
     * 
     * @return  the newly added child
     */
    default IndexColumn addNewIndexColumn() {
        final IndexColumn e = IndexColumn.newIndexColumn();
        add(e);
        return e;
    }

    /**
     * Returns whether or not this index is an {@code UNIQUE} index.
     * <p>
     * This property is editable in the GUI through reflection.
     * 
     * @return  {@code true} if this index is {@code UNIQUE}
     */
    @External(type = Boolean.class)
    Boolean isUnique();

    /**
     * Sets whether or not this index is an {@code UNIQUE} index.
     * <p>
     * This property is editable in the GUI through reflection.
     * 
     * @param unique  {@code true} if this index should be {@code UNIQUE}
     */
    @External(type = Boolean.class)
    void setUnique(Boolean unique);
}