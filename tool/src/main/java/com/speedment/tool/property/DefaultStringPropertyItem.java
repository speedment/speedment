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

import com.speedment.tool.property.DefaultStringPropertyItem.DefaultStringPropertyEditor;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class DefaultStringPropertyItem extends AbstractPropertyItem<String, StringProperty, DefaultStringPropertyEditor> {

    private final StringProperty textProperty;
    private final ObservableStringValue defaultValue;

    public DefaultStringPropertyItem(
            StringProperty value,
            ObservableStringValue defaultValue,
            String name,
            String description) {

        super(value, name, description, defaultDecorator());
        this.textProperty = value;
        this.defaultValue = defaultValue;
    }

    public DefaultStringPropertyItem(
            StringProperty value,
            ObservableStringValue defaultValue,
            String name,
            String description,
            Consumer<DefaultStringPropertyEditor> decorator) {

        super(value, name, description, decorator);
        this.textProperty = value;
        this.defaultValue = defaultValue;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    protected DefaultStringPropertyEditor createUndecoratedEditor() {
        return new DefaultStringPropertyEditor(this);
    }

    public final static class DefaultStringPropertyEditor extends AbstractPropertyEditor<String, DefaultStringNode> {

        private DefaultStringPropertyEditor(DefaultStringPropertyItem item) {
            super(item, new DefaultStringNode(item.textProperty, item.defaultValue));
        }

        @Override
        protected ObservableValue<String> getObservableValue() {
            return getEditor().text.textProperty();
        }

        @Override
        public void setValue(String value) {
            getEditor().setValue(value);
        }
    }

    private final static class DefaultStringNode extends HBox {

        private final StringProperty textProperty;
        private final ObservableStringValue defaultValue;
        private final StringProperty enteredValue;
        private final CheckBox auto;
        private final TextField text;

        private final static double SPACING = 8.0d;

        private DefaultStringNode(final StringProperty textProperty, final ObservableStringValue defaultValue) {
            this.textProperty = requireNonNull(textProperty); // Avoid Garbage Collection
            this.defaultValue = requireNonNull(defaultValue); // Avoid Garbage Collection
            this.auto         = new CheckBox("Auto");
            this.text         = new TextField();
            this.enteredValue = new SimpleStringProperty();
            init();
        }

        private void init() {
            final boolean sameAsDefault = isSameAsDefaultValue(textProperty);
            auto.selectedProperty().setValue(sameAsDefault);

            if (sameAsDefault) {
                enteredValue.setValue(defaultValue.get());
                text.textProperty().bind(defaultValue);
            } else {
                enteredValue.setValue(textProperty.get());
                text.textProperty().setValue(textProperty.get());
            }

            text.disableProperty().bind(auto.selectedProperty());

            auto.selectedProperty().addListener((ob, o, isAuto) -> {
                text.textProperty().unbind();

                if (isAuto) {
                    enteredValue.setValue(text.textProperty().getValue());
                    text.textProperty().bind(defaultValue);
                } else if (isSameAsDefaultValue(text.textProperty())) {
                    text.textProperty().setValue(enteredValue.get());
                }
            });

            getChildren().addAll(auto, text);

            setAlignment(Pos.CENTER_LEFT);
            auto.setAlignment(Pos.CENTER_LEFT);
            text.setAlignment(Pos.CENTER_LEFT);

            setHgrow(text, Priority.ALWAYS);
            setHgrow(auto, Priority.SOMETIMES);
            setSpacing(SPACING);
        }

        private void setValue(String value) {
            if (auto.isSelected()) {
                if (value == null || value.isEmpty()) {
                    enteredValue.setValue(defaultValue.get());
                } else {
                    enteredValue.setValue(value);
                }
            } else {
                text.textProperty().unbind();
                text.textProperty().setValue(value);
            }
        }

        private boolean isSameAsDefaultValue(StringProperty stringProperty) {
            return stringProperty.isEmpty().get() || Objects.equals(stringProperty.get(), defaultValue.get());
        }
    }
}