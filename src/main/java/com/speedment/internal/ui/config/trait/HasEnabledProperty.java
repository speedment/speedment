package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil
 */
public interface HasEnabledProperty extends DocumentProperty {
    
    default BooleanProperty enabledProperty() {
        return booleanPropertyOf(HasEnabled.ENABLED);
    }
    
    @Override
    default Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            new BooleanPropertyItem(
                enabledProperty(), 
                "Enabled", 
                "True if this node should be included in the code generation."
            )
        );
    }
}