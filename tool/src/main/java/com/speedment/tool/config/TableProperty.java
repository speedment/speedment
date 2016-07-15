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

import com.speedment.generator.TranslatorSupport;
import com.speedment.generator.util.JavaLanguageNamer;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.tool.component.DocumentPropertyComponent;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.TablePropertyMutator;
import com.speedment.tool.config.trait.HasAliasProperty;
import com.speedment.tool.config.trait.HasEnabledProperty;
import com.speedment.tool.config.trait.HasExpandedProperty;
import com.speedment.tool.config.trait.HasNameProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

import java.util.List;
import java.util.stream.Stream;

import static com.speedment.runtime.internal.util.ImmutableListUtil.concat;
import com.speedment.tool.property.DefaultStringPropertyItem;
import java.util.Optional;
import static javafx.beans.binding.Bindings.createStringBinding;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
@Api(version = "3.0")
public final class TableProperty
    extends AbstractChildDocumentProperty<Schema, TableProperty>
    implements
    Table,
    HasEnabledProperty,
    HasExpandedProperty,
    HasNameProperty,
    HasAliasProperty {

    public TableProperty(Schema parent) {
        super(parent);
    }

    public ObservableList<ColumnProperty> columnsProperty() {
        return observableListOf(COLUMNS);
    }

    public ObservableList<IndexProperty> indexesProperty() {
        return observableListOf(INDEXES);
    }

    public ObservableList<ForeignKeyProperty> foreignKeysProperty() {
        return observableListOf(FOREIGN_KEYS);
    }

    public ObservableList<PrimaryKeyColumnProperty> primaryKeyColumnsProperty() {
        return observableListOf(PRIMARY_KEY_COLUMNS);
    }

    @Override
    public StringProperty nameProperty() {
        return HasNameProperty.super.nameProperty();
    }
   
    public StringProperty packageNameProperty() {
        return stringPropertyOf(Table.PACKAGE_NAME, () -> null);
    }
    
    @Override
    public Optional<String> getPackageName() {
        return Optional.ofNullable(packageNameProperty().get());
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
    public TablePropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        final TranslatorSupport<Table> support = new TranslatorSupport<>(speedment.getOrThrow(JavaLanguageNamer.class), this);
        
        return Stream.of(
            HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            HasAliasProperty.super.getUiVisibleProperties(speedment),
            Stream.of(new DefaultStringPropertyItem(
                packageNameProperty(),
                createStringBinding(support::basePackageName, aliasProperty()),
                "Package Name", 
                "The package where generated classes will be located."
            ))
        ).flatMap(s -> s);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.TABLES, key);
    }
}
