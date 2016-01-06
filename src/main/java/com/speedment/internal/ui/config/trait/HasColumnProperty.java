package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.trait.*;
import com.speedment.config.db.Column;
import com.speedment.internal.ui.config.DocumentProperty;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;

/**
 *
 * @author Emil Forslund
 */
public interface HasColumnProperty extends DocumentProperty, HasColumn, HasNameProperty {
    
    default ObjectBinding<Column> columnProperty() {
        return createObjectBinding(this::findColumn, nameProperty());
    }
}