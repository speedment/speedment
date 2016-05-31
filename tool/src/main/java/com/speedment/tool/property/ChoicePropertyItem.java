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

import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @author Emil Forslund
 */
public final class ChoicePropertyItem<T> extends AbstractPropertyItem<T, Property<T>, PropertyEditor<T>> {
    
    private final ObservableList<T> alternatives;
    private final StringConverter<T> converter;
    private final Class<T> type;
    
    public ChoicePropertyItem(ObservableList<T> alternatives, Property<T> property, StringConverter<T> converter, Class<T> type, String name, String description) {
        super(property, name, description, defaultDecorator());
        this.alternatives = requireNonNull(alternatives);
        this.converter    = requireNonNull(converter);
        this.type         = requireNonNull(type);
    }

    public ChoicePropertyItem(ObservableList<T> alternatives, Property<T> property, StringConverter<T> converter, Class<T> type, String name, String description, Consumer<PropertyEditor<T>> decorator) {
        super(property, name, description, decorator);
        this.alternatives = requireNonNull(alternatives);
        this.converter    = requireNonNull(converter);
        this.type         = requireNonNull(type);
    }

    @Override
    public Class<T> getType() {
        return type;
    }
    
    @Override
    protected PropertyEditor<T> createUndecoratedEditor() {
        return createChoiceEditor(this, alternatives, converter);
    }
    
    private static <T> PropertyEditor<T> createChoiceEditor(AbstractPropertyItem<T, Property<T>, PropertyEditor<T>> item, ObservableList<T> alternatives, StringConverter<T> converter) {
        final ComboBox<T> comboBox = new ComboBox<>(alternatives);
        comboBox.setConverter(converter);
        
        final T selected = item.getValue();
        if (selected == null) {
            comboBox.getSelectionModel().clearSelection();
        } else {
            comboBox.getSelectionModel().select(item.getValue());
        }
        
        return new AbstractPropertyEditor<T, ComboBox<T>>(item, comboBox) {
            
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