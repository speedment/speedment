package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Emil Forslund
 */
public interface HasAliasProperty extends DocumentProperty {

    default StringProperty aliasProperty() {
        return stringPropertyOf(HasAlias.ALIAS);
    }
}