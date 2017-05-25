package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;

import java.util.Optional;

/**
 * A trait for {@link Document documents} that implement the
 * {@link #getEnumConstants()} method.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public interface HasEnumConstants extends Document {

    /**
     * The attribute for the 'enumConstants' field in the JSON Configuration
     * file.
     */
    String ENUM_CONSTANTS = "enumConstants";

    /**
     * Returns a comma separated string of the possible values that this column
     * may have. If the list of potential values are not constrained, an empty
     * optional is returned.
     *
     * @return  list of constant values separated by commas or empty
     */
    default Optional<String> getEnumConstants() {
        return getAsString(ENUM_CONSTANTS);
    }

}
