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


import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;

/**
 * An editor for editing a StringProperty via a TextArea, which has a default value. The user
 * may opt to use the default value or not, by checking or un-checking a CheckBox. The
 * property will bind to TextArea.textProperty()
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public class DefaultTextAreaItem extends AbstractTextItem {

        /**
     * Creates a new DefaultTextFieldItem. 
     * <p>
     * While the CheckBox is checked, the TextArea will be disabled, 
     * and the property will always have the default value. <br>
     * While the CheckBox is un-checked, the TextArea will be enabled, 
     * and the property will always have the TextArea's current value.
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
    public DefaultTextAreaItem(
            String label, 
            ObservableStringValue defaultValue, 
            StringProperty value, 
            String tooltip) {
        super(label, defaultValue, value, tooltip);
    }

    @Override
    protected TextInputControl getInputControl() {
        TextArea area = new TextArea();
        area.setWrapText(true);
        return area;
    }
    
}
