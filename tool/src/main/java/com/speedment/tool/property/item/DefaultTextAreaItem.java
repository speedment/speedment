package com.speedment.tool.property.item;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author Simon
 */
public class DefaultTextAreaItem extends AbstractDefaultStringItem {

    public DefaultTextAreaItem(String label, ObservableStringValue defaultValue, StringProperty value, String tooltip) {
        super(label, defaultValue, value, tooltip);
    }

    @Override
    protected TextInputControl getInputControl() {
        return new TextArea();
    }
    
}
