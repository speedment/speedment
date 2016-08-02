package com.speedment.tool.property.item;

import com.speedment.runtime.annotation.Api;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 * An editor for editing a StringProperty via a TextField, which has a default value. The user
 * may opt to use the default value or not, by checking or un-checking a CheckBox. The
 * property will bind to TextField.textProperty()
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
public class DefaultTextFieldItem  extends DefaultStringItem {

    /**
     * Creates a new DefaultTextFieldItem. 
     * <p>
     * While the CheckBox is checked, the TextField will be disabled, 
     * and the property will always have the default value. <br>
     * While the CheckBox is un-checked, the TextField will be enabled, 
     * and the property will always have the TextField's current value.
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
    public DefaultTextFieldItem(String label, ObservableStringValue defaultValue, StringProperty value, String tooltip) {
        super(label, defaultValue, value, tooltip);
    }

    @Override
    protected TextInputControl getInputControl() {
        return new TextField();
    }

}
