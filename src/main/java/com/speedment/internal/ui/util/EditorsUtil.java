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
package com.speedment.internal.ui.util;

import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.List;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.PropertyEditor;
import javafx.scene.control.TextArea;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Utility methods for creating custom {@link PropertyEditor editors} for a 
 * ControlsFX {@link PropertySheet}.
 * 
 * @author Emil Forslund
 */
public final class EditorsUtil {
    
    /**
     * Creates a {@link PropertyEditor} that uses a {@code PasswordField} as
     * editor componenet.
     * 
     * @param item  the property sheet item to represent
     * @return      the created property editor
     */
    public static PropertyEditor<String> createPasswordEditor(PropertySheet.Item item) {
        return new AbstractPropertyEditor<String, PasswordField>(item, new PasswordField()) {
            
            @Override
            protected ObservableValue<String> getObservableValue() {
                return getEditor().textProperty();
            }

            @Override
            public void setValue(String t) {
                getEditor().textProperty().setValue(t);
            }
        };
    }
    
    /**
     * Creates a {@link PropertyEditor} that uses a {@code TextArea} as editor 
     * componenet.
     * 
     * @param item  the property sheet item to represent
     * @return      the created property editor
     */
    public static PropertyEditor<String> createTextAreaEditor(PropertySheet.Item item) {
        return new AbstractPropertyEditor<String, TextArea>(item, new TextArea()) {
            
            @Override
            protected ObservableValue<String> getObservableValue() {
                return getEditor().textProperty();
            }

            @Override
            public void setValue(String t) {
                getEditor().textProperty().setValue(t);
            }
        };
    }
    
    /**
     * Creates a {@link PropertyEditor} that uses a {@code ChoiceBox} with a
     * {@code Function<T, String>} as converter.
     * componenet.
     * 
     * @param <T>           the property type
     * @param item          the property sheet item to represent
     * @param alternatives  the available alternatives
     * @param converter     converter to use for the alternative labels
     * @return              the created property editor
     */
    public static <T> PropertyEditor<T> createChoiceEditorWithConverter(PropertySheet.Item item, List<T> alternatives, Function<T, String> converter) {
        final ObservableList<String> labels = observableArrayList(alternatives.stream().map(converter).collect(toList()));
        final ObservableList<T> observable = observableArrayList(alternatives);
        
        final ChoiceBox<String> choice = new ChoiceBox<>(labels);
        @SuppressWarnings("unchecked")
        final T itemValue = (T)item.getValue();
        choice.getSelectionModel().select(converter.apply(itemValue));
        
        return new AbstractPropertyEditor<T, ChoiceBox<String>>(item, choice) {
            @Override
            protected ObservableValue<T> getObservableValue() {
                return Bindings.valueAt(observable, getEditor().getSelectionModel().selectedIndexProperty());
            }

            @Override
            public void setValue(T t) {
                getEditor().getSelectionModel().select(observable.indexOf(t));
            }
        };
    }
    
    /**
     * This should never be called.
     */
    private EditorsUtil() {
        instanceNotAllowed(getClass());
    }
}