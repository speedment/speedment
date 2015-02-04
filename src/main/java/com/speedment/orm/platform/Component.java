package com.speedment.orm.platform;

/**
 *
 * @author pemi
 */
public interface Component {

    // Lifecycle operations for plugins
    default void added() {
    }

    default void removed() {
    }

}
