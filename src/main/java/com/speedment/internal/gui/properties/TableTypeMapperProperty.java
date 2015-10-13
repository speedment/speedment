/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.gui.properties;

import com.speedment.Speedment;
import com.speedment.component.TypeMapperComponent;
import com.speedment.config.mapper.TypeMapper;
import com.speedment.exception.SpeedmentException;
import static java.util.Objects.requireNonNull;
import java.util.stream.Collectors;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public final class TableTypeMapperProperty extends TableProperty<TypeMapper> {
	
	private final ComboBox<TypeMapper> combo;

	public TableTypeMapperProperty(Speedment speedment, String name, TypeMapper initialValue) {
		super (requireNonNull(speedment), requireNonNull(name));
        
        combo = new ComboBox<>(
            speedment.get(TypeMapperComponent.class).stream()
                .filter(tm -> tm.getDatabaseType().equals(initialValue.getDatabaseType()))
                .collect(Collectors.toCollection(
                    FXCollections::observableArrayList
                ))
        );
        
        combo.setEditable(true);

        if (initialValue != null) {
            combo.setValue(initialValue);
        }

        combo.setConverter(new StringConverter<TypeMapper>() {
            @Override
            public String toString(TypeMapper clazz) {
                if (clazz == null) {
                    return "";
                } else {
                    return clazz.getClass().getName();
                }
            }

            @Override
            public TypeMapper fromString(String string) {
                return speedment.get(TypeMapperComponent.class).get(string).orElseThrow(
                    () -> new SpeedmentException("Could not find type mapper '" + string + "'.")
                );
            }
        });
	}

	@Override
	public Property<TypeMapper> valueProperty() {
		return combo.valueProperty();
	}

	@Override
	public Node getValueGraphic() {
		return combo;
	}
}