package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.trait.HasName;
import com.speedment.internal.ui.config.DocumentProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Emil Forslund
 */
public interface HasNameProperty extends DocumentProperty {

    default StringProperty nameProperty() {
        return stringPropertyOf(HasName.NAME);
    }
}