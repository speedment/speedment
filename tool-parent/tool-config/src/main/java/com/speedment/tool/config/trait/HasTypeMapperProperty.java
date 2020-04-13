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
package com.speedment.tool.config.trait;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.trait.HasTypeMapper;
import static com.speedment.runtime.config.trait.HasTypeMapperUtil.DATABASE_TYPE;
import static com.speedment.runtime.config.trait.HasTypeMapperUtil.TYPE_MAPPER;
import com.speedment.tool.config.DocumentProperty;
import java.util.Optional;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.StringProperty;

/**
 * A trait for documents that have a {@code TypeMapper} specified. This 
 * corresponds to the {@link HasTypeMapper} trait but for observable documents.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface HasTypeMapperProperty extends DocumentProperty, HasTypeMapper {

    default StringProperty typeMapperProperty() {
        return stringPropertyOf(TYPE_MAPPER, 
            () -> HasTypeMapper.super.getTypeMapper().orElse(null)
        );
    }

    @Override
    default Optional<String> getTypeMapper() {
        return Optional.ofNullable(typeMapperProperty().get());
    }
    
    @Override
    default String getDatabaseType() {
        return databaseTypeProperty().get();
    }
    
    @Override
    default Class<?> findDatabaseType() {
        return databaseTypeObjectProperty().get();
    }

    default StringProperty databaseTypeProperty() {
        return stringPropertyOf(
            DATABASE_TYPE, 
            HasTypeMapper.super::getDatabaseType
        );
    }

    default ObjectBinding<Class<?>> databaseTypeObjectProperty() {
        return createObjectBinding(
            HasTypeMapper.super::findDatabaseType, 
            databaseTypeProperty()
        );
    }

    Column getMappedColumn();
}