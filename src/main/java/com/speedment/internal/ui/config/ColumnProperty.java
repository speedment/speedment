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
import com.speedment.component.DocumentPropertyComponent;
import com.speedment.config.db.Column;
import static com.speedment.config.db.Column.AUTO_INCREMENT;
import static com.speedment.config.db.Column.DATABASE_TYPE;
import com.speedment.config.db.Table;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.exception.SpeedmentException;
import com.speedment.ui.config.trait.HasAliasProperty;
import com.speedment.ui.config.trait.HasEnabledProperty;
import com.speedment.ui.config.trait.HasNameProperty;
import com.speedment.ui.config.trait.HasNullableProperty;
import com.speedment.ui.config.db.BooleanPropertyItem;
import com.speedment.ui.config.db.TypeMapperPropertyItem;
import java.util.Optional;
import java.util.stream.Stream;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import org.controlsfx.control.PropertySheet;
import static com.speedment.component.DocumentPropertyComponent.concat;
import com.speedment.internal.ui.config.mutator.ColumnPropertyMutator;
import com.speedment.internal.ui.config.mutator.DocumentPropertyMutator;
import com.speedment.ui.config.trait.HasExpandedProperty;
import com.speedment.ui.config.trait.HasOrdinalPositionProperty;

/**
 *
 * @author Emil Forslund
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
    protected String[] keyPathEndingWith(String key) {
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