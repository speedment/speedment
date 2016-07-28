/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.item;

import com.speedment.tool.property.PropertyEditor;
import static java.util.Objects.requireNonNull;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Simon
 */
public abstract class AbstractLabelAndTooltipItem implements PropertyEditor.Item{
    
    private final String label;
    private final String tooltip;
    
    public AbstractLabelAndTooltipItem(String label, String tooltip){
        requireNonNull(label, "A label must be assigned.");
        requireNonNull(tooltip, "A tooltip must be assigned");
        this.label = label;
        this.tooltip = tooltip;
    }
    
    @Override
    public final Node getLabel() {
        final Label l = new Label(label);
        l.setTooltip( new Tooltip(tooltip) );
        l.getStyleClass().add("property-labels");
        return l;
    }       
}
