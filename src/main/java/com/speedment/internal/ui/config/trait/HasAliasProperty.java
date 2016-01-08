package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.property.StringPropertyItem;
import java.util.stream.Stream;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public interface HasAliasProperty extends DocumentProperty {

    default StringProperty aliasProperty() {
        return stringPropertyOf(HasAlias.ALIAS);
    }

    @Override
    default Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            new StringPropertyItem(
                aliasProperty(), 
                "Alias", 
                "The name that will be used for this in generated code."
            )
        );
    }
}