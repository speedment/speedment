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