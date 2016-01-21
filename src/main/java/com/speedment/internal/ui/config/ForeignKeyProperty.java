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
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyProperty extends AbstractChildDocumentProperty<Table> 
    implements ForeignKey, HasEnabledProperty, HasNameProperty {

    public ForeignKeyProperty(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    public ObservableList<ForeignKeyColumnProperty> foreignKeyColumnsProperty() {
        return observableListOf(FOREIGN_KEY_COLUMNS, ForeignKeyColumnProperty::new);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.concat(
            HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment)
        );
    }

    @Override
    public BiFunction<ForeignKey, Map<String, Object>, ForeignKeyColumnProperty> foreignKeyColumnConstructor() {
        return ForeignKeyColumnProperty::new;
    }

    @Override
    protected final DocumentProperty createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case FOREIGN_KEY_COLUMNS : return new ForeignKeyColumnProperty(this, data);
            default                  : return super.createDocument(key, data);
        }
    }
    
    @Override
    public Stream<ForeignKeyColumnProperty> foreignKeyColumns() {
        return foreignKeyColumnsProperty().stream();
    }
    
    @Override
    public ForeignKeyColumnProperty addNewForeignKeyColumn() {
        final Map<String, Object> child = new ConcurrentHashMap<>();
        child.put(ForeignKeyColumn.FOREIGN_COLUMN_NAME, "");
        child.put(ForeignKeyColumn.FOREIGN_TABLE_NAME, "");
        
        final ForeignKeyColumnProperty created = new ForeignKeyColumnProperty(this, child);
        foreignKeyColumnsProperty().add(created);
        
        return created;
    }

    @Override
    public boolean isExpandedByDefault() {
        return false;
    }
    
    
    @Override
    public void prepare() {
        foreignKeyColumns().forEach(DocumentProperty::prepare);
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    }     
}