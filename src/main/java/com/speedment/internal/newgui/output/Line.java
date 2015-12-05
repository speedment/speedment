/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.newgui.output;

import javafx.scene.control.Label;

/**
 *
 * @author Emil Forslund
 */
public final class Line extends Label {
    
    private Line(String text, String... classes) {
        super(text);
        getStyleClass().addAll(classes);
    }

    public static Line info(String message) {
        final Line line = new Line(message, "msg", "info");
        return line;
    }
    
    public static Line warning(String message) {
        final Line line = new Line(message, "msg", "warning");
        return line;
    }
    
    public static Line error(String message) {
        final Line line = new Line(message, "msg", "error");
        return line;
    }
    
    public static Line success(String message) {
        final Line line = new Line(message, "msg", "success");
        return line;
    }
}