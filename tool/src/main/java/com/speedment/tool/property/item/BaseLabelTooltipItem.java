package com.speedment.tool.property.item;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.property.PropertyEditor;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
@Api(version="3.0")
public abstract class BaseLabelTooltipItem implements PropertyEditor.Item{
    
    private final String label;
    private final String tooltip;
    private final Map<ObservableValue<Object>, ChangeListener<Object>> listeners;
    
    /**
     * Creates an instance of this class
     * 
     * @param label    the description label text
     * @param tooltip  the tooltip text
     */
    protected BaseLabelTooltipItem(String label, String tooltip){
        requireNonNull(label, "A label must be assigned.");
        requireNonNull(tooltip, "A tooltip must be assigned");
        this.label = label;
        this.tooltip = tooltip;
        this.listeners = new HashMap<>();
    }
    
    @Override
    public Node getLabel() {
        final Label l = new Label(label);
        l.setTooltip( new Tooltip(tooltip) );
        return l;
    }
    
    @Override
    public final void onRemove(){
        listeners.forEach(ObservableValue::removeListener);
    }
    
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
