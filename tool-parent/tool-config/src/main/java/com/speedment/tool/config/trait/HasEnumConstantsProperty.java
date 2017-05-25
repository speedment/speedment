package com.speedment.tool.config.trait;

import com.speedment.runtime.config.trait.HasEnumConstants;
import com.speedment.tool.config.DocumentProperty;
import javafx.beans.property.StringProperty;

import java.util.Optional;

/**
 * Specialization of the {@link HasEnumConstants}-trait for implementations that
 * also implement the {@link #enumConstantsProperty()}-method.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public interface HasEnumConstantsProperty
extends DocumentProperty, HasEnumConstants {

    /**
     * Observable property for the {@link #getEnumConstants()}-fields. The value
     * may be {@code null}.
     *
     * @return  the observable property for enum constants
     */
    default StringProperty enumConstantsProperty() {
        return stringPropertyOf(ENUM_CONSTANTS,
            () -> HasEnumConstants.super.getEnumConstants().orElse(null)
        );
    }

    @Override
    default Optional<String> getEnumConstants() {
        return Optional.ofNullable(enumConstantsProperty().get());
    }
}
