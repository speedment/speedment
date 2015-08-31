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
import com.speedment.internal.core.runtime.typemapping.StandardJavaTypeMapping;
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
public final class TableClassProperty extends TableProperty<Class> {
	
	private final ComboBox<Class> combo;

	public TableClassProperty(Speedment speedment, String name, Class value) {
		super (requireNonNull(speedment), requireNonNull(name));
        // value nullable
		combo = new ComboBox<>(
			StandardJavaTypeMapping.stream()
				.map(v -> v.getJavaClass())
				.collect(Collectors.toCollection(
					FXCollections::observableArrayList
				))
		);
		
		combo.setEditable(true);
		
		if (value != null) {
			combo.setValue(value);
		}
		
		combo.setConverter(new StringConverter<Class>() {
			@Override
			public String toString(Class clazz) {
				if (clazz == null) {
					return "";
				} else {
					return clazz.getName();
				}
			}

			@Override
			public Class fromString(String string) {
				try {
					return Class.forName(string);
				} catch (ClassNotFoundException ex) {
					return null;
				}
			}
		});
	}

	@Override
	public Property<Class> valueProperty() {
		return combo.valueProperty();
	}

	@Override
	public Node getValueGraphic() {
		return combo;
	}
}