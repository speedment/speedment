package com.speedment.tool.property.item;

import com.speedment.runtime.annotation.Api;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

/**
 * A simple editor which will let the user edit a BooleanProperty via a
 * CheckBox. 
 * <p>
 * The CheckBox will default to the current value of the property. If no such value
 * exists, it will default to false. The property will bind to CheckBox.selectedProperty()
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
public class SimpleCheckBoxItem extends BaseLabelTooltipItem{

    private final BooleanProperty property;
    
    /**
     * Creates a new SimpleCheckBoxItem. The property will bind to the
     * editor node's CheckBox.selectedProperty()
     * 
     * @param label     the label text
     * @param property  the property to edit
     * @param tooltip   the tooltip text
     */ 
    public SimpleCheckBoxItem(String label, BooleanProperty property, String tooltip){
        super(label, tooltip);
        this.property = property;
    }

    @Override
    public Node getEditor() {
        final CheckBox box = new CheckBox();
        box.setSelected( property.get() );        
        property.bindBidirectional(box.selectedProperty() );
        return box;
    }    
}
