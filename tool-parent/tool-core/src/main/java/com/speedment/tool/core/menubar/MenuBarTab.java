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
package com.speedment.tool.core.menubar;

/**
 * Enumeration of all the tabs visible in the Speedment tool menu bar.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public enum MenuBarTab {

    FILE("_File"),
    EDIT("_Edit"),
    WINDOW("_Window"),
    HELP("_Help");

    private final String text;

    MenuBarTab(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}