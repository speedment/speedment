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

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.mutator.ColumnPropertyMutator;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.trait.*;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.Optional;

import static com.speedment.runtime.core.internal.util.ImmutableListUtil.concat;
import static javafx.beans.binding.Bindings.createObjectBinding;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class ColumnProperty extends AbstractChildDocumentProperty<Table, ColumnProperty> implements
    Column,
    HasEnabledProperty,
    HasExpandedProperty,
    HasNameProperty,
    HasAliasProperty,
    HasNullableProperty,
    HasOrdinalPositionProperty {

    public ColumnProperty(Table parent) {
        super(parent);
    }

    @Override
    public StringProperty nameProperty() {
        return HasNameProperty.super.nameProperty();
    }

    public BooleanProperty autoIncrementProperty() {
        return booleanPropertyOf(AUTO_INCREMENT, Column.super::isAutoIncrement);
    }

    @Override
    public boolean isAutoIncrement() {
        return autoIncrementProperty().get();
    }

    public StringProperty typeMapperProperty() {
        return stringPropertyOf(TYPE_MAPPER, () -> Column.super.getTypeMapper().orElse(null));
    }

    @Override
    public Optional<String> getTypeMapper() {
        return Optional.ofNullable(typeMapperProperty().get());
    }
    
    public StringProperty enumConstantsProperty() {
        return stringPropertyOf(ENUM_CONSTANTS, () -> Column.super.getEnumConstants().orElse(null));
    }

    @Override
    public Optional<String> getEnumConstants() {
        return Optional.ofNullable(enumConstantsProperty().get());
    }
    @Override
    public String getDatabaseType() {
        return databaseTypeProperty().get();
    }

    public StringProperty databaseTypeProperty() {
        return stringPropertyOf(DATABASE_TYPE, Column.super::getDatabaseType);
    }

    public ObjectBinding<Class<?>> databaseTypeObjectProperty() {
        return createObjectBinding(Column.super::findDatabaseType, databaseTypeProperty());
    }

    @Override
    public Class<?> findDatabaseType() {
        return databaseTypeObjectProperty().get();
    }

    @Override
    public ColumnPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.COLUMNS, key);
    }
}