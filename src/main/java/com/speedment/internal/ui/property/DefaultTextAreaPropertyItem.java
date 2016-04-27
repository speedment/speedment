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

import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.HBox.setHgrow;
import javafx.scene.layout.Priority;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @author Emil Forslund
 */
public final class DefaultTextAreaPropertyItem extends AbstractPropertyItem<String, StringProperty> {
    
    private final StringProperty textProperty;
    private final ObservableStringValue defaultValue;
    
    public DefaultTextAreaPropertyItem(
            StringProperty value, 
            ObservableStringValue defaultValue, 
            String name, 
            String description) {
        
        super(value, name, description, AbstractPropertyItem.DEFAULT_DECORATOR);
        this.textProperty = value;
        this.defaultValue = defaultValue;
    }
    
    public DefaultTextAreaPropertyItem(
            StringProperty value, 
            ObservableStringValue defaultValue, 
            String name, 
            String description, 
            Consumer<PropertyEditor<?>> decorator) {
        
        super(value, name, description, decorator);
        this.textProperty = value;
        this.defaultValue = defaultValue;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    protected PropertyEditor<?> createUndecoratedEditor() {
        return new DefaultTextAreaPropertyEditor(this);
    }
    
    private final static class DefaultTextAreaPropertyEditor extends AbstractPropertyEditor<String, DefaultTextAreaNode> {

        private DefaultTextAreaPropertyEditor(DefaultTextAreaPropertyItem item) {
            super(item, new DefaultTextAreaNode(item.textProperty, item.defaultValue));
        }
        
        @Override
        protected ObservableValue<String> getObservableValue() {
            return super.getEditor().text.textProperty();
        }

        @Override
        public void setValue(String value) {
            super.getEditor().setValue(value);
        }
    }
    
    private final static class DefaultTextAreaNode extends HBox {
        
        private final ObservableStringValue defaultValue;
        private final StringProperty enteredValue;
        private final CheckBox auto;
        private final TextArea text;
        
        private final static double SPACING = 8.0d;
        
        private DefaultTextAreaNode(StringProperty textProperty, ObservableStringValue defaultValue) {
            this.defaultValue = requireNonNull(defaultValue); // Avoid Garbage Collection
            this.auto         = new CheckBox("Auto");
            this.text         = new TextArea();
            
            final boolean isAutoByDefault = 
                textProperty.isEmpty().get() ||
                textProperty.get().equals(defaultValue.get());
            
            this.enteredValue = new SimpleStringProperty();
            this.auto.selectedProperty().setValue(isAutoByDefault);
            
            if (isAutoByDefault) {
                enteredValue.setValue(defaultValue.get());
                text.textProperty().bind(defaultValue);
            } else {
                enteredValue.setValue(textProperty.get());
                text.textProperty().setValue(textProperty.get());
            }
            
            this.text.disableProperty().bind(auto.selectedProperty());

            this.auto.selectedProperty().addListener((ob, o, isAuto) -> {
                text.textProperty().unbind();
                
                if (isAuto) {
                    enteredValue.setValue(text.textProperty().getValue());
                    text.textProperty().bind(this.defaultValue);
                } else {
                    if (text.textProperty().isEmpty().get()
                    ||  Objects.equals(
                            this.defaultValue.get(), 
                            this.text.textProperty().get()
                    )) {
                        text.textProperty().setValue(enteredValue.get());
                    }
                }
            });
            
            super.getChildren().addAll(auto, text);
            
            setAlignment(Pos.CENTER_LEFT);
            auto.setAlignment(Pos.CENTER_LEFT);
            text.setWrapText(true);

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
    }
}
