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
package com.speedment.tool.config;


import com.speedment.common.dbmodel.Index;
import com.speedment.common.dbmodel.IndexColumn;
import com.speedment.tool.component.DocumentPropertyComponent;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.IndexColumnPropertyMutator;
import com.speedment.tool.config.trait.HasColumnProperty;
import com.speedment.tool.config.trait.HasExpandedProperty;
import com.speedment.tool.config.trait.HasNameProperty;
import com.speedment.tool.config.trait.HasOrderTypeProperty;
import com.speedment.tool.config.trait.HasOrdinalPositionProperty;

import java.util.List;

import static com.speedment.runtime.internal.util.ImmutableListUtil.concat;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */

public final class IndexColumnProperty extends AbstractChildDocumentProperty<Index, IndexColumnProperty>
    implements IndexColumn, HasExpandedProperty, HasNameProperty, HasOrdinalPositionProperty,
    HasOrderTypeProperty, HasColumnProperty {

    public IndexColumnProperty(Index parent) {
        super(parent);
    }

    @Override
    public IndexColumnPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

//    @Override
//    public Stream<PropertySheet.Item> getUiVisibleProperties(Injector injector) {
//        return Stream.concat(
//            HasColumnProperty.super.getUiVisibleProperties(injector),
//            HasOrderTypeProperty.super.getUiVisibleProperties(injector)
//        );
//    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.INDEX_COLUMNS, key);
    }
}
