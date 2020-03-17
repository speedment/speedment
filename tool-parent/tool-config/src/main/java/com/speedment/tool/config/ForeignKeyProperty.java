/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.ForeignKeyUtil;
import com.speedment.runtime.config.Table;
import com.speedment.tool.config.component.DocumentPropertyComponentUtil;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.ForeignKeyPropertyMutator;
import com.speedment.tool.config.trait.*;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Stream;

import static com.speedment.tool.config.internal.util.ImmutableListUtil.concat;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class ForeignKeyProperty extends AbstractChildDocumentProperty<Table, ForeignKeyProperty> 
implements ForeignKey,
           HasEnabledProperty,
           HasExpandedProperty,
           HasIdProperty,
           HasNameProperty,
           HasNameProtectedProperty {

    public ForeignKeyProperty(Table parent) {
        super(parent);
    }
    
    public ObservableList<ForeignKeyColumnProperty> foreignKeyColumnsProperty() {
        return observableListOf(ForeignKeyUtil.FOREIGN_KEY_COLUMNS);
    }
    
    @Override
    public Stream<ForeignKeyColumn> foreignKeyColumns() {
        return foreignKeyColumnsProperty().stream().map(ForeignKeyColumn.class::cast);
    }

    @Override
    public boolean isExpandedByDefault() {
        return false;
    }
    
    @Override
    public ForeignKeyPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }
    
    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponentUtil.FOREIGN_KEYS, key);
    }
}