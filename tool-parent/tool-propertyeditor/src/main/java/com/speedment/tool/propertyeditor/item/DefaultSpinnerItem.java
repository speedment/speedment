/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.propertyeditor.item;

import com.speedment.runtime.core.exception.SpeedmentException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.function.UnaryOperator;

import static java.util.Objects.requireNonNull;

/**
 * An editor for editing a StringProperty via an IntegerSpinner, which has a default value. The user
 * may opt to use the default value or not, by checking or un-checking a CheckBox. The property
 * will bind to Spinner.valueProperty()
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public class DefaultSpinnerItem extends AbstractLabelTooltipItem {

    private final ObservableIntegerValue defaultValue;
    private final ObjectProperty<Integer> value;        //Output value
    private final ObjectProperty<Integer> customValue;  
    private final int min;
    private final int max;

    /**
     * Creates a new DefaultSpinnerItem. 
     * <p>
     * While the CheckBox is checked, the Spinner will be disabled, 
     * and the property will always have the default value. <br>
     * While the CheckBox is un-checked, the Spinner will be enabled, 
     * and the property will always have the Spinner's current value.
     * <p>
     * Upon construction, the editor decides whether to check the default box
     * by comparing the property value to the default value. If they match, or
     * the property value is {@code null}, the CheckBox will be checked.
     * 
     * @param label         the label text
     * @param defaultValue  the default value 
     * @param value         the property to be edited
     * @param tooltip       the tooltip
     */
    public DefaultSpinnerItem(
        final String label,
        final ObservableIntegerValue defaultValue,
        final IntegerProperty value,
        final String tooltip
    ) {
        this(label, defaultValue, value, tooltip, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    /**
     * Creates a new DefaultSpinnerItem. 
     * <p>
     * While the CheckBox is checked, the Spinner will be disabled, 
     * and the property will always have the default value. <br>
     * While the CheckBox is un-checked, the Spinner will be enabled, 
     * and the property will always have the Spinner's current value.
     * <p>
     * Upon construction, the editor decides whether to check the default box
     * by comparing the property value to the default value. If they match, or
     * the property value is {@code null}, the CheckBox will be checked.
     * 
     * @param label         the label text
     * @param defaultValue  the default value 
     * @param value         the property to be edited
     * @param tooltip       the tooltip
     * @param decorator     the editor decorator
     */
    public DefaultSpinnerItem(
        final String label,
        final ObservableIntegerValue defaultValue,
        final IntegerProperty value,
        final String tooltip,
        final UnaryOperator<Node> decorator
    ) {
        this(label, defaultValue, value, tooltip, Integer.MIN_VALUE, Integer.MAX_VALUE, decorator);
    }

    /**
     * Creates a new DefaultSpinnerItem. 
     * <p>
     * While the CheckBox is checked, the Spinner will be disabled, 
     * and the property will always have the default value. <br>
     * While the CheckBox is un-checked, the Spinner will be enabled, 
     * and the property will always have the Spinner's current value.
     * <p>
     * Upon construction, the editor decides whether to check the default box
     * by comparing the property value to the default value. If they match, or
     * the property value is {@code null}, the CheckBox will be checked.
     * 
     * @param label         the label text
     * @param defaultValue  the default value 
     * @param value         the property to be edited
     * @param tooltip       the tooltip
     * @param min           the minimum spinner value
     * @param max           the maximum spinner value
     */
    public DefaultSpinnerItem(
        final String label,
        final ObservableIntegerValue defaultValue,
        final IntegerProperty value,
        final String tooltip,
        final int min,
        final int max
    ) {
        this(label, defaultValue, value, tooltip, min, max, NO_DECORATOR);
    }
    
    /**
     * Creates a new DefaultSpinnerItem. 
     * <p>
     * While the CheckBox is checked, the Spinner will be disabled, 
     * and the property will always have the default value. <br>
     * While the CheckBox is un-checked, the Spinner will be enabled, 
     * and the property will always have the Spinner's current value.
     * <p>
     * Upon construction, the editor decides whether to check the default box
     * by comparing the property value to the default value. If they match, or
     * the property value is {@code null}, the CheckBox will be checked.
     * 
     * @param label         the label text
     * @param defaultValue  the default value 
     * @param value         the property to be edited
     * @param tooltip       the tooltip
     * @param min           the minimum spinner value
     * @param max           the maximum spinner value
     * @param decorator     the editor decorator
     */
    public DefaultSpinnerItem(
        final String label,
        final ObservableIntegerValue defaultValue,
        final IntegerProperty value,
        final String tooltip,
        final int min,
        final int max,
        final UnaryOperator<Node> decorator
    ) {
        super(label, tooltip, decorator);

        this.defaultValue = requireNonNull(defaultValue);
        this.value = requireNonNull(value).asObject();
        this.customValue = new SimpleIntegerProperty().asObject();
        this.min = min;
        this.max = max;
    }

    @Override
    protected Node createUndecoratedEditor() {
        final boolean usesDefaultValue = value.getValue() == null || value.getValue().equals(defaultValue.getValue());

        final HBox container = new HBox();
        final CheckBox auto = new CheckBox("Auto");
        final Spinner<Integer> spinner = new Spinner<>();

        final IntegerSpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
        spinner.setValueFactory(svf);
        spinner.disableProperty().bind(auto.selectedProperty());
        spinner.setEditable(true);

        auto.setSelected(usesDefaultValue);
        attachListener(auto.selectedProperty(), (ov, o, isAuto)
            -> setSpinnerBehaviour(svf, isAuto, defaultValue, customValue)
        );

        customValue.setValue(usesDefaultValue ? defaultValue.get() : value.get());
        setSpinnerBehaviour(svf, usesDefaultValue, defaultValue, customValue);

        final TextField editor = spinner.getEditor();
        attachListener(editor.textProperty(), (ov, oldVal, newVal) -> {
            try {
                Integer.parseInt(newVal);
            } catch (final NumberFormatException ex) {
                editor.setText(oldVal);
            }
        });

        attachListener(editor.focusedProperty(), (ov, wasFocused, isFocused) -> {
            if (wasFocused) {
                tryConstrainValueIn(editor);
            }
        });

        attachListener(svf.valueProperty(), (ov, o, n) -> {
            if (n == null || n == defaultValue.get()) {
                value.setValue(null);
            } else {
                value.setValue(n);
            }
        });

        container.getChildren().addAll(auto, spinner);
        return container;
    }

    private void tryConstrainValueIn(TextField editor) {
        requireNonNull(editor);

        try {
            final int editorValue = Integer.parseInt(editor.getText());
            if (editorValue > max) {
                editor.setText(String.valueOf(max));
            } else if (editorValue < min) {
                editor.setText(String.valueOf(min));
            }
        } catch (final NumberFormatException ex) {
            throw new SpeedmentException("Unable to parse an integer from editor field", ex);
        }
    }

    private static void setSpinnerBehaviour(
        final IntegerSpinnerValueFactory svf,
        final boolean useDefaultValue,
        final ObservableIntegerValue defaultValue,
        final ObjectProperty<Integer> customValue
    ) {
        if (useDefaultValue) {
            svf.valueProperty().unbindBidirectional(customValue);
            svf.setValue(defaultValue.get());
        } else {
            svf.setValue(customValue.getValue());
            svf.valueProperty().bindBidirectional(customValue);
        }
    }
}
