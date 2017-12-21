package com.speedment.tool.config.trait;

import com.speedment.runtime.config.trait.HasName;
import javafx.beans.property.BooleanProperty;

/**
 * Trait that defines if the {@link HasName#getName()}-field is protected from
 * edits in the tool or not. This should have no effect on the generated code.
 *
 * @author Emil Forslund
 * @since  3.0.20
 */
public interface HasNameProtectedProperty extends HasNameProperty {

    String NAME_PROTECTED = "nameProtected";

    default BooleanProperty nameProtectedProperty() {
        return booleanPropertyOf(NAME_PROTECTED, this::isNameProtectedByDefault);
    }

    default boolean isNameProtected() {
        return nameProtectedProperty().get();
    }

    default boolean isNameProtectedByDefault() {
        return true;
    }

}
