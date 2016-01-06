package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import javafx.beans.property.BooleanProperty;

/**
 *
 * @author Emil
 */
public interface HasEnabledProperty extends DocumentProperty {
    
    default BooleanProperty enabledProperty() {
        return booleanPropertyOf(HasEnabled.ENABLED);
    }
}