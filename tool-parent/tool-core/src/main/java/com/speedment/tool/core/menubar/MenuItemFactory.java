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

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Factory for a a node that can be added as a child to a {@link Menu}.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
@FunctionalInterface
public interface MenuItemFactory {

    /**
     * Creates and returns a new item in the menu.
     *
     * @return  the menu item
     */
    MenuItem createMenuItem();

}
