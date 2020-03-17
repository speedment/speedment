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
package com.speedment.tool.core.util;


import javafx.scene.control.Label;

/**
 *
 * @author Emil Forslund
 */
public final class OutputUtil {

    private OutputUtil() {}

    public static Label info(String message) {
        return log(message, "info");
    }
    
    public static Label warning(String message) {
        return log(message, "warning");
    }
    
    public static Label error(String message) {
        return log(message, "error");
    }
    
    public static Label success(String message) {
        return log(message, "success");
    }
    
    private static Label log(String message, String type) {
        final Label label = new Label(message);
        label.setWrapText(true);
        label.getStyleClass().addAll("msg", type);
        return label;
    }
    
}