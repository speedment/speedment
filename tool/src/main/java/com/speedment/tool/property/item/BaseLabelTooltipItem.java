package com.speedment.tool.property.item;

import com.speedment.tool.property.PropertyEditor;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import static java.util.Objects.requireNonNull;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Simon
 */
public abstract class BaseLabelTooltipItem implements PropertyEditor.Item{
    
    private final String label;
    private final String tooltip;
    private final Map<ObservableValue<Object>, ChangeListener<Object>> listeners;
    
    public BaseLabelTooltipItem(String label, String tooltip){
        requireNonNull(label, "A label must be assigned.");
        requireNonNull(tooltip, "A tooltip must be assigned");
        this.label = label;
        this.tooltip = tooltip;
        this.listeners = new HashMap<>();
    }
    
    @Override
    public final Node getLabel() {
        final Label l = new Label(label);
        l.setTooltip( new Tooltip(tooltip) );
        l.getStyleClass().add("property-labels");
        return l;
    }  

    @Override
    public PropertyEditor.Editor getEditor() {
       return new PropertyEditor.Editor() {
           @Override
           public Node getNode() {
               return getEditorNode();
           }

           @Override
           public void onRemove() {
               detachAllListeners();
           }
       };
    }
        
    protected final <T> void attachListener(ObservableValue<T> observable, ChangeListener<T> listener) {
        @SuppressWarnings("unchecked")
        final ObservableValue<Object> key = (ObservableValue<Object>) observable;
        
        @SuppressWarnings("unchecked")
        final ChangeListener<Object> value = (ChangeListener<Object>) listener;
        
        listeners.put(key, value);
        observable.addListener(listener);
    }
    
    protected final void detachAllListeners(){
        listeners.forEach(ObservableValue::removeListener);
    }
    
    protected abstract Node getEditorNode();
}
