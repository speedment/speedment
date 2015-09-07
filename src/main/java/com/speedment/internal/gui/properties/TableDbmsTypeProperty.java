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
import com.speedment.config.parameters.DbmsType;
import com.speedment.internal.core.platform.component.DbmsHandlerComponent;
import static java.util.Objects.requireNonNull;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class TableDbmsTypeProperty extends TableProperty<DbmsType> {
	
	private final ChoiceBox<DbmsType> choice;

	public TableDbmsTypeProperty(Speedment speedment, String name, DbmsType value) {
		super (requireNonNull(speedment), requireNonNull(name));
        // value nullable

		choice = new ChoiceBox<>(
			supportedDbmsTypes()
				.collect(Collectors.toCollection(
					FXCollections::observableArrayList
				))
		);
		
		choice.setValue(value);
		
		choice.setConverter(new StringConverter<DbmsType>() {
			@Override
			public String toString(DbmsType object) {
				final String str = object.getName();
				if (str.length() < 2) {
					return str.toUpperCase();
				} else {
					return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
				}
			}

			@Override
			public DbmsType fromString(String string) {
				return supportedDbmsTypes()
					.filter(e -> e.getName().equalsIgnoreCase(string))
					.findAny().orElse(null);
			}
		});
	}

	@Override
	public Property<DbmsType> valueProperty() {
		return choice.valueProperty();
	}

	@Override
	public Node getValueGraphic() {
		return choice;
	}
    
    private Stream<DbmsType> supportedDbmsTypes() {
        return getSpeedment().get(DbmsHandlerComponent.class).supportedDbmsTypes();
    }
}