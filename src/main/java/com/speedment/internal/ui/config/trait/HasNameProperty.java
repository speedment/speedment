package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.trait.HasName;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.property.StringPropertyItem;
import java.util.stream.Stream;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public interface HasNameProperty extends DocumentProperty, HasUiVisibleProperties {

    default StringProperty nameProperty() {
        return stringPropertyOf(HasName.NAME);
    }

    @Override
    default Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            new StringPropertyItem(
                nameProperty(), 
                "Name", 
                "The name of the persisted entity represented by this node."
            )
        );
    }
}