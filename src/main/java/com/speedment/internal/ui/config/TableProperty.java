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
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.PreviewPropertyItem;
import com.speedment.internal.util.JavaLanguage;
import static com.speedment.internal.util.JavaLanguage.javaTypeName;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javax.annotation.Generated;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class TableProperty extends AbstractChildDocumentProperty<Schema> 
    implements Table, HasEnabledProperty, HasNameProperty, HasAliasProperty {

    public TableProperty(Schema parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            HasAliasProperty.super.getUiVisibleProperties(speedment),
            Stream.of(new PreviewPropertyItem(
                Bindings.createStringBinding(
                    () -> 
                        isEnabled() ? 
                            "@" + Generated.class.getSimpleName() + "(\"speedment\")\n" +
                            "public interface " + javaTypeName(getJavaName()) + " extends Entity<" + javaTypeName(getJavaName()) + "> {" +
                            "    ...\n" +
                            "}" :
                            "(No code is generated for disabled nodes.)",
                    enabledProperty(), 
                    aliasProperty(),
                    nameProperty()
                ), 
                "Preview", 
                "This is a simple preview to give you a hint of how the parameters will affect the generated code."
            ))
        ).flatMap(s -> s);
    }
    
    public ObservableList<ColumnProperty> columnsProperty() {
        return observableListOf(COLUMNS, ColumnProperty::new);
    }
    
    public ObservableList<IndexProperty> indexesProperty() {
        return observableListOf(INDEXES, IndexProperty::new);
    }
    
    public ObservableList<ForeignKeyProperty> foreignKeysProperty() {
        return observableListOf(FOREIGN_KEYS, ForeignKeyProperty::new);
    }
    
    public ObservableList<PrimaryKeyColumnProperty> primaryKeyColumnsProperty() {
        return observableListOf(PRIMARY_KEY_COLUMNS, PrimaryKeyColumnProperty::new);
    }
    
    @Override
    public StringProperty nameProperty() {
        return HasNameProperty.super.nameProperty();
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ColumnProperty> columnConstructor() {
        return ColumnProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, IndexProperty> indexConstructor() {
        return IndexProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ForeignKeyProperty> foreignKeyConstructor() {
        return ForeignKeyProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, PrimaryKeyColumnProperty> primaryKeyColumnConstructor() {
        return PrimaryKeyColumnProperty::new;
    }

    @Override
    protected final DocumentProperty createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case COLUMNS             : return new ColumnProperty(this, data);
            case INDEXES             : return new IndexProperty(this, data);
            case FOREIGN_KEYS        : return new ForeignKeyProperty(this, data);
            case PRIMARY_KEY_COLUMNS : return new PrimaryKeyColumnProperty(this, data);
            default                  : return super.createDocument(key, data);
        }
    }
    
    @Override
    public Stream<? extends ColumnProperty> columns() {
        return columnsProperty().stream();
    }

    @Override
    public Stream<? extends IndexProperty> indexes() {
        return indexesProperty().stream();
    }

    @Override
    public Stream<? extends ForeignKeyProperty> foreignKeys() {
        return foreignKeysProperty().stream();
    }

    @Override
    public Stream<? extends PrimaryKeyColumnProperty> primaryKeyColumns() {
        return primaryKeyColumnsProperty().stream();
    }

    @Override
    public ColumnProperty addNewColumn() {
        final ColumnProperty created = new ColumnProperty(this, new ConcurrentHashMap<>());
        columnsProperty().add(created);
        return created;
    }

    @Override
    public IndexProperty addNewIndex() {
        final IndexProperty created = new IndexProperty(this, new ConcurrentHashMap<>());
        indexesProperty().add(created);
        return created;
    }

    @Override
    public ForeignKeyProperty addNewForeignKey() {
        final ForeignKeyProperty created = new ForeignKeyProperty(this, new ConcurrentHashMap<>());
        foreignKeysProperty().add(created);
        return created;
    }

    @Override
    public PrimaryKeyColumnProperty addNewPrimaryKeyColumn() {
        final PrimaryKeyColumnProperty created = new PrimaryKeyColumnProperty(this, new ConcurrentHashMap<>());
        primaryKeyColumnsProperty().add(created);
        return created;
    }
}