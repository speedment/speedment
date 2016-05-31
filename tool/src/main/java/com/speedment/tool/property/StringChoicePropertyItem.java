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

import static com.speedment.internal.ui.property.AbstractPropertyItem.defaultDecorator;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @author Emil Forslund
 */
public final class StringChoicePropertyItem extends AbstractPropertyItem<String, StringProperty, PropertyEditor<String>> {
    
    private final ObservableList<String> alternatives;
    
    public StringChoicePropertyItem(ObservableList<String> alternatives, StringProperty property, String name, String description) {
        super(property, name, description, defaultDecorator());
        this.alternatives = requireNonNull(alternatives);
    }

    public StringChoicePropertyItem(ObservableList<String> alternatives, StringProperty property, String name, String description, Consumer<PropertyEditor<String>> decorator) {
        super(property, name, description, decorator);
        this.alternatives = requireNonNull(alternatives);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
    
    @Override
    protected PropertyEditor<String> createUndecoratedEditor() {
        return createChoiceEditor(this, alternatives);
    }
    
    private static <T> PropertyEditor<T> createChoiceEditor(AbstractPropertyItem<String, StringProperty, PropertyEditor<String>> property, ObservableList<T> choices) {
        return new AbstractPropertyEditor<T, ComboBox<T>>(property, new ComboBox<>(choices)) {
            
            @Override 
            protected ObservableValue<T> getObservableValue() {
                return getEditor().getSelectionModel().selectedItemProperty();
            }

            @Override
            public void setValue(T value) {
                getEditor().getSelectionModel().select(value);
            }
        };
    }
}