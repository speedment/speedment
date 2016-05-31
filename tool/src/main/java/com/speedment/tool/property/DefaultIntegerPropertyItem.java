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
package com.speedment.tool.property;

import com.speedment.tool.property.DefaultIntegerPropertyItem.DefaultIntegerPropertyEditor;
import com.speedment.tool.util.SimpleNumericProperty;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import org.controlsfx.property.editor.AbstractPropertyEditor;

/**
 *
 * @author Emil Forslund
 */
public final class DefaultIntegerPropertyItem extends AbstractPropertyItem<Number, IntegerProperty, DefaultIntegerPropertyEditor> {

    private final IntegerProperty valueProperty;
    private final IntegerBinding defaultValue;

    public DefaultIntegerPropertyItem(IntegerProperty value, IntegerBinding defaultValue, String name, String description) {
        super(value, name, description, defaultDecorator());
        this.valueProperty = value;
        this.defaultValue = defaultValue;
    }

    public DefaultIntegerPropertyItem(IntegerProperty value, IntegerBinding defaultValue, String name, String description, Consumer<DefaultIntegerPropertyEditor> decorator) {
        super(value, name, description, decorator);
        this.valueProperty = value;
        this.defaultValue = defaultValue;
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    protected DefaultIntegerPropertyEditor createUndecoratedEditor() {
        return new DefaultIntegerPropertyEditor(this);
    }

    public final static class DefaultIntegerPropertyEditor extends AbstractPropertyEditor<Integer, DefaultIntegerNode> {

        private DefaultIntegerPropertyEditor(DefaultIntegerPropertyItem item) {
            super(item, new DefaultIntegerNode(item.valueProperty, item.defaultValue));
        }

        @Override
        protected ObservableValue<Integer> getObservableValue() {
            return super.getEditor().spinner.getValueFactory().valueProperty();
        }

        @Override
        public void setValue(Integer value) {
            super.getEditor().setValue(value);
        }

        public void setMin(int min) {
            ((IntegerSpinnerValueFactory) super.getEditor().spinner.getValueFactory()).setMin(min);
        }

        public void setMax(int max) {
            ((IntegerSpinnerValueFactory) super.getEditor().spinner.getValueFactory()).setMax(max);
        }

    }

    public final static class DefaultIntegerNode extends HBox {

        private final Property<Number> spinnerValue;
        private final IntegerProperty enteredValue;
        private final CheckBox auto;
        private final Spinner<Integer> spinner;

        private final static double SPACING = 8.0d;

        private DefaultIntegerNode(IntegerProperty valueProperty, IntegerBinding defaultValue) {
            this.auto = new CheckBox("Auto");
            this.spinner = new Spinner<>(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1);

            final boolean isAutoByDefault = valueProperty.get() == defaultValue.get();

            this.enteredValue = new SimpleIntegerProperty(valueProperty.get());
            this.auto.selectedProperty().setValue(isAutoByDefault);

            this.spinner.disableProperty().bind(auto.selectedProperty());
            this.spinner.setEditable(true);

            final StringConverter<Integer> sci = spinner.getValueFactory().getConverter();
            final StringConverter<Integer> sci2 = new StringConverter<Integer>() {
                @Override
                public Integer fromString(String value) {
                    try {
                        return sci.fromString(value);
                    } catch (final NumberFormatException nfe) {
                        return 0;
                    }
                }

                @Override
                public String toString(Integer value) {
                    return sci.toString(value);
                }
            };
            
            final StringConverter<Number> sci3 = new StringConverter<Number>() {
                @Override
                public Number fromString(String value) {
                    try {
                        return sci.fromString(value);
                    } catch (final NumberFormatException nfe) {
                        return 0;
                    }
                }

                @Override
                public String toString(Number value) {
                    return value == null ? null : sci.toString(value.intValue());
                }
            };
            
            this.spinner.getValueFactory().setConverter(sci2);
            
            this.spinnerValue = new SimpleNumericProperty(
                isAutoByDefault 
                    ? defaultValue.get() 
                    : valueProperty.get()
            );
            Bindings.bindBidirectional(
                this.spinner.getEditor().textProperty(), 
                spinnerValue, 
                sci3
            );

            
            this.spinnerValue.addListener((ob, o, v) -> {
                System.out.println("New value: " + v);
            });

            this.auto.selectedProperty().addListener((ob, o, isAuto) -> {
                if (isAuto) {
                    enteredValue.setValue(spinnerValue.getValue());
                    spinnerValue.setValue(defaultValue.getValue());
                    //ObservableUtil.bind(spinner.getValueFactory().valueProperty(), defaultValue);
                } else if (Objects.equals(
                    defaultValue.getValue(),
                    spinnerValue.getValue()
                )) {
                    spinnerValue.setValue(enteredValue.get());
                }
            });
            
            defaultValue.addListener((ob, o, n) -> {
                if (auto.isSelected()) {
                    spinnerValue.setValue(n);
                }
            });

            super.getChildren().addAll(auto, spinner);

            setAlignment(Pos.CENTER_LEFT);
            auto.setAlignment(Pos.CENTER_LEFT);

            setHgrow(spinner, Priority.ALWAYS);
            setHgrow(auto, Priority.SOMETIMES);
            setSpacing(SPACING);
            
            valueProperty.bindBidirectional(spinnerValue);
        }

        private void setValue(Integer value) {
            spinnerValue.setValue(value);
        }
    }
}
