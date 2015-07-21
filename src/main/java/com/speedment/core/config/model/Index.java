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
import com.speedment.core.config.model.impl.IndexImpl;
import groovy.lang.Closure;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.0")
public interface Index extends ConfigEntity, Child<Table>, Parent<IndexColumn> {

    enum Holder {

        HOLDER;
        private Supplier<Index> provider = () -> new IndexImpl();
    }

    static void setSupplier(Supplier<Index> provider) {
        Holder.HOLDER.provider = provider;
    }

    static Index newIndex() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    default Class<Index> getInterfaceMainClass() {
        return Index.class;
    }

    @Override
    default Class<Table> getParentInterfaceMainClass() {
        return Table.class;
    }

    default IndexColumn addNewIndexColumn() {
        final IndexColumn e = IndexColumn.newIndexColumn();
        add(e);
        return e;
    }

    @External(type = Boolean.class)
    Boolean isUnique();

    @External(type = Boolean.class)
    void setUnique(Boolean unique);

    default IndexColumn indexColumn(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewIndexColumn);
    }

}
