package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import javafx.beans.property.IntegerProperty;

/**
 *
 * @author Emil Forslund
 */
public interface HasOrdinalPositionProperty extends DocumentProperty {
    
    default IntegerProperty ordinalPositionProperty() {
        return integerPropertyOf(HasOrdinalPosition.ORDINAL_POSITION);
    }
}