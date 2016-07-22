package com.speedment.plugins.enums.internal.ui;

import com.speedment.tool.property.AbstractPropertyItem;
import java.util.function.Consumer;
import javafx.beans.property.StringProperty;

public class CommaSeparatedStringPropertyItem extends AbstractPropertyItem<String, StringProperty, CommaSeparatedStringEditor>{
  
    private final StringProperty valueProperty;
    private final String defaultValue;

    public CommaSeparatedStringPropertyItem(StringProperty value, String defaultValue, String name, String description) {
        super(value, name, description, defaultDecorator());
        this.valueProperty = value;
        this.defaultValue = defaultValue;
    }

    public CommaSeparatedStringPropertyItem(StringProperty value, String defaultValue, String name, String description, Consumer<CommaSeparatedStringEditor> decorator) {
        super(value, name, description, decorator);
        this.valueProperty = value;
        this.defaultValue = defaultValue;
    }

    @Override
    protected CommaSeparatedStringEditor createUndecoratedEditor() {
        CommaSeparatedStringEditor editor = new CommaSeparatedStringEditor(defaultValue);
        valueProperty.bind( editor.binding() );
        
        return editor;
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }
    
}
