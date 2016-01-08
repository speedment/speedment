package com.speedment.internal.ui.config.trait;

import com.speedment.internal.ui.config.DocumentProperty;
import javafx.beans.property.BooleanProperty;

/**
 *
 * @author Emil
 */
public interface HasExpandedProperty extends DocumentProperty {
    
    final String EXPANDED = "expanded";
    
    default BooleanProperty expandedProperty() {
        return booleanPropertyOf(EXPANDED);
    }
}