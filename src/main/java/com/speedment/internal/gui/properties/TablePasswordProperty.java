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
import static java.util.Objects.requireNonNull;
import javafx.beans.property.Property;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;

/**
 *
 * @author Emil Forslund
 */
public final class TablePasswordProperty extends TableProperty<String> {
	
	private final PasswordField textfield;

	public TablePasswordProperty(Speedment speedment, String name, String value) {
		super (requireNonNull(speedment), requireNonNull(name));
        // value nullable
		textfield = new PasswordField();
		
		if (value == null) {
			textfield.setText("...");
		} else {
			textfield.setText(value);
		}
	}

	@Override
	public Property<String> valueProperty() {
		return textfield.textProperty();
	}

	@Override
	public Node getValueGraphic() {
		return textfield;
	}
}