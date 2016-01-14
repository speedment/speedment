/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.ui.property;

import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @author Emil Forslund
 */
public final class PreviewPropertyItem extends AbstractPropertyItem<String, StringProperty> {
    
    private final ObservableStringValue preview;
    
    public PreviewPropertyItem(ObservableStringValue preview, String name, String description) {
        super(wrap(preview), name, description, AbstractPropertyItem.DEFAULT_DECORATOR);
        this.preview = preview;
    }
    
    public PreviewPropertyItem(ObservableStringValue preview, String name, String description, Consumer<PropertyEditor<?>> decorator) {
        super(wrap(preview), name, description, decorator);
        this.preview = preview;
    }
    
    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public void setValue(Object obj) {
        // Do nothing. A preview can't change value.
    }

    @Override
    protected PropertyEditor<?> createUndecoratedEditor() {
        return new PreviewPropertyEditor(this);
    }
    
    private static StringProperty wrap(ObservableStringValue value) {
        final StringProperty property = new SimpleStringProperty();
        property.bind(value);
        return property;
    }
    
    private final static class PreviewPropertyEditor extends AbstractPropertyEditor<String, PreviewNode> {

        private PreviewPropertyEditor(PreviewPropertyItem item) {
            super(item, new PreviewNode(item.preview));
        }
        
        @Override
        protected ObservableValue<String> getObservableValue() {
            return super.getEditor().textProperty();
        }

        @Override
        public void setValue(String value) {
            // Do nothing. A preview can't change value.
        }
    }
    
    private final static class PreviewNode extends TextArea {
        
        public PreviewNode(ObservableStringValue preview) {
            setFont(Font.font("Monospaced"));
            textProperty().bind(preview);
            setEditable(false);
        }
        
    }
}