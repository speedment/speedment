/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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