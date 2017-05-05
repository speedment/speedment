package com.speedment.tool.core.resource;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A subset of the Font Awesome Icon library usable in JavaFX applications.
 *
 * @author Emil Forslund
 * @since  3.0.8
 */
public enum FontAwesome implements Icon {

    BAN('\uf05e'),
    CHECK('\uf00c'),
    CLOCK('\uf017'),
    DATABASE('\uf1c0'),
    DOWNLOAD('\uf019'),
    EXCLAMATION_CIRCLE('\uf06a'),
    EXCLAMATION_TRIANGLE('\uf071'),
    FOLDER_OPEN('\uf07c'),
    LOCK('\uf023'),
    PLAY_CIRCLE('\uf144'),
    PLUS('\uf067'),
    QUESTION_CIRCLE('\uf059'),
    REFRESH('\uf021'),
    SIGN_IN('\uf090'),
    SPINNER('\uf110'),
    STAR('\uf005'),
    TIMES('\uf00d'),
    TRASH('\uf014'),
    WRENCH('\uf0ad');

    private final char character;

    FontAwesome(char character) {
        this.character = character;
    }

    @Override
    public Node view() {
        final Label label = new Label(String.valueOf(character));
        label.getStyleClass().add("glyph-icon");
        return label;
    }
}