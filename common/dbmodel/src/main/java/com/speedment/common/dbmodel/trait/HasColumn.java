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
package com.speedment.common.dbmodel.trait;

import com.speedment.common.dbmodel.Column;
import com.speedment.common.dbmodel.Document;
import com.speedment.common.dbmodel.Table;
import com.speedment.common.dbmodel.util.TraitUtil.AbstractTraitView;
import static com.speedment.common.dbmodel.util.TraitUtil.viewOf;

import java.util.Map;
import java.util.Optional;

/**
 * Trait for {@link Document} implementations that reference another 
 * {@link Column} document and therefore has a {@link #findColumn()} method. If 
 * a {@code Document} implements this trait, it is also expected to implement 
 * the {@link HasName} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public interface HasColumn extends Document, HasName {

    /**
     * Locates and returns the column referenced by the {@link #getName()} 
     * method.
     * 
     * @return  the referenced column
     */
    default Optional<? extends Column> findColumn() {
        return ancestors()
            .filter(Table.class::isInstance)
            .map(Table.class::cast)
            .findFirst()
            .flatMap(table -> table
                .columns()
                .filter(col -> col.getName().equals(getName()))
                .findAny()
            );
    }
    
    /**
     * Returns a wrapper of the specified document that implements the 
     * {@link HasColumn} trait. If the specified document already implements the
     * trait, it is returned unwrapped.
     * 
     * @param document  the document to wrap
     * @return          the wrapper
     */
    static HasColumn of(Document document) {
        return viewOf(document, HasColumn.class, HasColumnView::new);
    }
}

/**
 * A wrapper class that makes sure that a given {@link Document} implements the
 * {@link HasColumn} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
class HasColumnView extends AbstractTraitView implements HasColumn {

    /**
     * Constructs a new alias view of with the specified parent and data.
     * 
     * @param parent         the parent of the wrapped document
     * @param data           the data of the wrapped document
     * @param mainInterface  the main interface of the wrapped document
     */
    HasColumnView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}