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


import com.speedment.tool.propertyeditor.PropertyEditor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import static java.util.Objects.requireNonNull;

/**
 * Base for most PropertyEditor.Item used in Speedment
 * <p>
 * This basic implementation will assign each editor item a text label and with
 * a tooltip, which can be read if the user hovers over the label. Child classes
 * are responsible for supplying the actual editor node
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public abstract class AbstractLabelTooltipItem implements PropertyEditor.Item {
    
    protected static final UnaryOperator<Node> NO_DECORATOR = n -> n;
    
    private final String label;
    private final String tooltip;
    private final Map<ObservableValue<Object>, ChangeListener<Object>> listeners;
    private final UnaryOperator<Node> editorDecorator;
    
    /**
     * Creates an instance of this class. This constructor lets the caller 
     * supply a decorator that will be applied to the created editor.
     * 
     * @param label           the description label text
     * @param tooltip         the tooltip text
     * @param editorDecorator the editor decorator
     */
    protected AbstractLabelTooltipItem(String label, String tooltip, UnaryOperator<Node> editorDecorator){
        requireNonNull(label,           "A label must be assigned.");
        requireNonNull(tooltip,         "A tooltip must be assigned");
        requireNonNull(editorDecorator, "An editor decorator must be assigned");
        
        this.label           = label;
        this.tooltip         = tooltip;
        this.editorDecorator = editorDecorator;
        
        this.listeners = new HashMap<>();
    }
    
    @Override
    public Node createLabel() {
        final Label l = new Label(label);
        l.setTooltip( new Tooltip(tooltip) );
        return l;
    }

    @Override
    public final Node createEditor() {
        final Node editor = createUndecoratedEditor();
        return editorDecorator.apply(editor);
    }
    
    @Override
    public final void onRemove(){
        listeners.forEach(ObservableValue::removeListener);
    }
    
    /**
     * Creates an editor without applying any decorators. This is called 
     * internally by the base class to produce a decorated editor.
     * 
     * @return  the created editor
     */
    protected abstract Node createUndecoratedEditor();
    
    /**
     * Attaches a ChangeListener to the ObservableValue, and also stores their relationship.
     * <p>
     * Knowledge of their relationship is used when this PropertyEditor.Item is
     * removed from the PropertySheet, to detach the listeners again. This will avoid
     * memory leaks, as listeners otherwise prevent the observable from being garbage 
     * collected.
     * 
     * @param <T>  type of the ObservableValue and the ChangeListener
     * @param observable  the ObservableValue
     * @param listener    the ChangeListener 
     */
    protected final <T> void attachListener(ObservableValue<T> observable, ChangeListener<T> listener) {
        @SuppressWarnings("unchecked")
        final ObservableValue<Object> key = (ObservableValue<Object>) observable;
        
        @SuppressWarnings("unchecked")
        final ChangeListener<Object> value = (ChangeListener<Object>) listener;
        
        listeners.put(key, value);
        observable.addListener(listener);
    }
}
