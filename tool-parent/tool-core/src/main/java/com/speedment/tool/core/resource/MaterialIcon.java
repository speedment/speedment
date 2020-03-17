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
 * A subset of the Google Material Design icon library usable in JavaFX
 * applications.
 *
 * @author Emil Forslund
 * @since  3.1.18
 */
public enum MaterialIcon implements Icon {

    WRAP_TEXT('\ue25b'),
    SCROLL_TEXT('\ue258');

    private final char character;

    MaterialIcon(char character) {
        this.character = character;
    }

    @Override
    public Node view() {
        final Label label = new Label(String.valueOf(character));
        label.getStyleClass().add("material-icon");
        return label;
    }
}