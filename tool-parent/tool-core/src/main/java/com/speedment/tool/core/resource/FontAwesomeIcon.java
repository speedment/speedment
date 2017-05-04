package com.speedment.tool.core.resource;

import com.speedment.tool.core.exception.SpeedmentToolException;
import javafx.scene.image.Image;

import java.io.InputStream;

/**
 * A subset of the Font Awesome Icon library usable in JavaFX applications.
 *
 * @author Emil Forslund
 * @since  3.0.8
 */
public enum FontAwesomeIcon {

    BAN("ban"),
    CLOCK("clock-o"),
    DATABASE("database"),
    DOWNLOAD("download"),
    EXCLAMATION_CIRCLE("exclamation-circle.png"),
    EXCLAMATION_TRIANGLE("exclamation-triangle.png"),
    FOLDER_OPEN("folder-open"),
    LOCK("lock"),
    PLAY_CIRCLE("play-circle"),
    QUESTION_CIRCLE("question-circle"),
    REFRESH("refresh"),
    SIGN_IN("sign-in"),
    SPINNER("spinner"),
    STAR("star"),
    TRASH("trash"),
    WRENCH("wrench");

    private final String filename;

    FontAwesomeIcon(String icon) {
        filename = "/icons/" + icon + ".png";
    }

    public Image load() {
        return new Image(getFileInputStream());
    }

    InputStream getFileInputStream() {
        final InputStream stream = getClass().getResourceAsStream(filename);

        if (stream == null) {
            throw new SpeedmentToolException(
                "Could not find icon: '" + filename + "'."
            );
        }

        return stream;
    }
}