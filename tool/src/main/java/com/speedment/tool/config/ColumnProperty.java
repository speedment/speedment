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

import com.speedment.runtime.Speedment;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.tool.component.DocumentPropertyComponent;
import com.speedment.tool.config.mutator.ColumnPropertyMutator;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.trait.*;
import com.speedment.tool.property.BooleanPropertyItem;
import com.speedment.tool.property.TypeMapperPropertyItem;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import org.controlsfx.control.PropertySheet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.speedment.runtime.internal.util.ImmutableListUtil.concat;
import static javafx.beans.binding.Bindings.createObjectBinding;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
@Api(version = "3.0")
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
        return stringPropertyOf(TYPE_MAPPER, Column.super::getTypeMapper);
    }

    @Override
    public String getTypeMapper() {
        return typeMapperProperty().get();
    }

    public Property<TypeMapper<?, ?>> typeMapperObjectProperty() {
        final Property<TypeMapper<?, ?>> pathProperty = new SimpleObjectProperty<>(
            TYPE_MAPPER_CONVERTER.fromString(typeMapperProperty().get())
        );

        typeMapperProperty().bindBidirectional(pathProperty, TYPE_MAPPER_CONVERTER);

        return pathProperty;
    }

    @Override
    public TypeMapper<?, ?> findTypeMapper() {
        return typeMapperObjectProperty().getValue();
    }

    public StringProperty databaseTypeProperty() {
        return stringPropertyOf(DATABASE_TYPE, Column.super::getDatabaseType);
    }

    @Override
    public String getDatabaseType() {
        return databaseTypeProperty().get();
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
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            HasAliasProperty.super.getUiVisibleProperties(speedment),
            HasNullableProperty.super.getUiVisibleProperties(speedment),
            Stream.of(
                new TypeMapperPropertyItem(
                    speedment,
                    Optional.ofNullable(findTypeMapper())
                    .map(tm -> (Class) tm.getDatabaseType())
                    .orElse(findDatabaseType()),
                    typeMapperProperty(),
                    "JDBC Type to Java",
                    "The class that will be used to map types between the database and the generated code."
                ), new BooleanPropertyItem(
                    autoIncrementProperty(),
                    "Is Auto Incrementing",
                    "If this column will increment automatically for each new entity."
                )
            )
        ).flatMap(s -> s);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.COLUMNS, key);
    }

    private final static StringConverter<TypeMapper<?, ?>> TYPE_MAPPER_CONVERTER = new StringConverter<TypeMapper<?, ?>>() {
        @Override
        public String toString(TypeMapper<?, ?> typeMapper) {
            if (typeMapper == null) {
                return null;
            } else {
                return typeMapper.getClass().getName();
            }
        }

        @Override
        public TypeMapper<?, ?> fromString(String className) {
            if (className == null) {
                return null;
            } else {
                try {
                    @SuppressWarnings("unchecked")
                    final TypeMapper<?, ?> typeMapper = (TypeMapper<?, ?>) Class.forName(className).newInstance();
                    return typeMapper;
                } catch (final ClassNotFoundException ex) {
                    throw new SpeedmentException("Could not find type-mapper class: '" + className + "'.", ex);
                } catch (InstantiationException ex) {
                    throw new SpeedmentException("Could not instantiate type-mapper class: '" + className + "'.", ex);
                } catch (IllegalAccessException ex) {
                    throw new SpeedmentException("Could not access type-mapper class: '" + className + "'.", ex);
                }
            }
        }
    };
}
