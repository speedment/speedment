package com.speedment.tool.actions.internal.resources;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

/**
 * Enumeration of all the icons used in the project tree by default. This enum
 * might change in future versions!
 *
 * @author Emil Forslund
 * @since  3.0.17
 */
public enum ProjectTreeIcon {

    BOOK      ("/com/speedment/tool/actions/icons/book.png"),
    BOOK_OPEN ("/com/speedment/tool/actions/icons/bookOpen.png");

    private final String filename;

    ProjectTreeIcon(String filename) {
        this.filename = filename;
    }

    public ImageView view() {
        return new ImageView(image());
    }

    public Image image() {
        return new Image(inputStream());
    }

    private InputStream inputStream() {
        return getClass().getResourceAsStream(filename);
    }
}