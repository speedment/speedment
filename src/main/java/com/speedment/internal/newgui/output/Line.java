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
    
//    private final static Font FONT = Font.font("Monospaced", 12);

    private Line(String text, String... classes) {
        super(text);
        getStyleClass().addAll(classes);
//
//        setFont(FONT);
//        setStyle(
//            "-fx-background-color: " + rgb(background) + ";" +
//            "-fx-text-fill: " + rgb(foreground) + ";"
//        );
    }
    
//    private static String rgb(Color color) {
//        return new StringBuilder("rgba(")
//            .append(color.getRed() * 255).append(", ")
//            .append(color.getGreen() * 255).append(", ")
//            .append(color.getBlue() * 255).append(", ")
//            .append(color.getOpacity()).append(")")
//            .toString();
//    }
    
    public static Line info(String message) {
        final Line line = new Line(message, "msg", "info");
//        line.getStyleClass().addAll("msg", "info");
        return line;
    }
    
    public static Line warning(String message) {
        final Line line = new Line(message, "msg", "warning");
//        line.getStyleClass().addAll("msg", "warning");
        return line;
    }
    
    public static Line error(String message) {
        final Line line = new Line(message, "msg", "error");
//        line.getStyleClass().addAll("msg", "error");
        return line;
    }
    
    public static Line success(String message) {
        final Line line = new Line(message, "msg", "success");
//        line.getStyleClass().addAll("msg", "success");
        return line;
    }
    
//    private final static class Info extends Line {
//        private Info(String text) {
//            super(text, Color.BLACK, Color.TRANSPARENT);
//        }
//    }
//    
//    private final static class Warning extends Line {
//        private Warning(String text) {
//            super(text, Color.BLACK, Color.LIGHTGOLDENRODYELLOW);
//            getStyleClass().add("warning");
//        }
//    }
//    
//    private final static class Error extends Line {
//        private Error(String text) {
//            super(text, Color.BLACK, Color.LIGHTPINK);
//        }
//    }
//    
//    private final static class Success extends Line {
//        private Success(String text) {
//            super(text, Color.BLACK, Color.LIGHTGREEN);
//        }
//    }
}
