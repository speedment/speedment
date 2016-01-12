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
package com.speedment.internal.ui.property;

import com.speedment.Speedment;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.util.EditorsUtil;
import java.util.List;
import java.util.function.Consumer;
import static java.util.stream.Collectors.toList;
import javafx.beans.property.Property;
import org.controlsfx.property.editor.PropertyEditor;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class TypeMapperPropertyItem extends AbstractPropertyItem<TypeMapper<?, ?>, Property<TypeMapper<?, ?>>> {
    
    private final Speedment speedment;
    private final Class<?> type;
    
    public TypeMapperPropertyItem(Speedment speedment, Class<?> type, Property<TypeMapper<?, ?>> property, String name, String description) {
        this(speedment, type, property, name, description, AbstractPropertyItem.DEFAULT_DECORATOR);
    }

    public TypeMapperPropertyItem(Speedment speedment, Class<?> type, Property<TypeMapper<?, ?>> property, String name, String description, Consumer<PropertyEditor<?>> decorator) {
        super(property, name, description, decorator);
        this.speedment = requireNonNull(speedment);
        this.type      = requireNonNull(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<TypeMapper<?, ?>> getType() {
        return (Class<TypeMapper<?, ?>>) (Class<?>) TypeMapper.class;
    }

    @Override
    protected PropertyEditor<?> createUndecoratedEditor() {
        final List<TypeMapper<?, ?>> mappers = speedment.getTypeMapperComponent().stream()
            .filter(mapper -> type.isAssignableFrom(mapper.getDatabaseType()))
            .sorted(comparing(TypeMapper::getLabel))
            .collect(toList());
        
        if (mappers.isEmpty()) {
            throw new SpeedmentException(
                "Created TypeMapperPropertyItem to illustrate type '" + 
                type.getSimpleName() + 
                "' but no mappers was found."
            );
        }
        
        return EditorsUtil.createChoiceEditorWithConverter(
            this, mappers, TypeMapper::getLabel
        );
    }
}