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

import com.speedment.common.injector.annotation.InjectKey;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Used by Tool plugins to add additional controllers to the 'menubar' section.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
@InjectKey(MenuBarComponent.class)
public interface MenuBarComponent {

    /**
     * Returns the handler for the specified tab.
     *
     * @param tab  the tab to find the handler for
     * @return  the handler
     */
    MenuBarTabHandler forTab(MenuBarTab tab);

    /**
     * Clears and populates the specified {@link MenuBar} by creating new
     * instances of {@link MenuItem} using the installed factories.
     *
     * @param menuBar  the menu bar to populate
     */
    void populate(MenuBar menuBar);

}
