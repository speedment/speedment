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
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 *
 * @author Emil Forslund
 */
public final class TableIntegerProperty extends TableProperty<Number> {

    private final TextField textfield;
    private final IntegerProperty property;

    public TableIntegerProperty(Speedment speedment, String name, Number value) {
        super(requireNonNull(speedment), requireNonNull(name));
        // value nullable
        textfield = new TextField();

        if (value == null) {
            textfield.setText("...");
        } else {
            textfield.setText(value.toString());
        }

        textfield.textProperty().addListener((ob, o, n) -> {
            try {
                Integer.parseInt(n);
            } catch (NumberFormatException e) {
                textfield.setText("0");
            }
        });

        property = new SimpleIntegerProperty();
        property.bind(new IntegerBinding() {
            @Override
            protected int computeValue() {
                try {
                    return Integer.parseInt(textfield.getText());
                } catch (NumberFormatException ex) {
                    return 0;
                }
            }
        });
    }

    @Override
    public Property<Number> valueProperty() {
        return property;
    }

    @Override
    public Node getValueGraphic() {
        return textfield;
    }
}
