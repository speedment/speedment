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
package com.speedment.internal.ui.config;

import com.speedment.Speedment;
import com.speedment.config.db.Index;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class IndexProperty extends AbstractChildDocumentProperty<Table> 
    implements Index, HasEnabledProperty, HasNameProperty {

    public IndexProperty(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            Stream.of(
                new BooleanPropertyItem(
                    uniqueProperty(),       
                    "Is Unique",
                    "True if elements in this index are unique."
                )
            )
        ).flatMap(s -> s);
    }
    
    public final BooleanProperty uniqueProperty() {
        return booleanPropertyOf(UNIQUE);
    }

    @Override
    public BiFunction<Index, Map<String, Object>, IndexColumnProperty> indexColumnConstructor() {
        return IndexColumnProperty::new;
    }

    @Override
    protected final DocumentProperty createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case INDEX_COLUMNS : return new IndexColumnProperty(this, data);
            default            : return super.createDocument(key, data);
        }
    }
    
    @Override
    public Stream<IndexColumnProperty> indexColumns() {
        return indexColumnsProperty().stream();
    }
    
    public ObservableList<IndexColumnProperty> indexColumnsProperty() {
        return observableListOf(INDEX_COLUMNS, IndexColumnProperty.class);
    }

    @Override
    public IndexColumnProperty addNewIndexColumn() {
        final IndexColumnProperty created = new IndexColumnProperty(this, new ConcurrentHashMap<>());
        indexColumnsProperty().add(created);
        return created;
    }
}