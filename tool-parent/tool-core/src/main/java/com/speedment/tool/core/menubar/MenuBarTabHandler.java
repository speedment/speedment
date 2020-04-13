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

import com.speedment.tool.core.resource.SpeedmentIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Manages factories for {@link MenuItem MenuItems} for a particular tab.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public interface MenuBarTabHandler {

    /**
     * Adds a new {@link SeparatorMenuItem} factory to the end of this handler.
     *
     * @return  this instance
     */
    default MenuBarTabHandler addSeparator() {
        return add(SeparatorMenuItem::new);
    }

    /**
     * Adds a new {@link MenuItem} factory to the end of this handler.
     *
     * @param text    the text to show
     * @param action  the action to perform when the button is pressed
     * @return        this instance
     */
    default MenuBarTabHandler addMenuItem(String text, EventHandler<ActionEvent> action) {
        return set(text.replace("_", "").replace(" ", "-").toLowerCase(), () -> {
            final MenuItem item = new MenuItem(text);
            item.setOnAction(action);
            return item;
        });
    }

    /**
     * Adds a new {@link MenuItem} factory to the end of this handler.
     *
     * @param text    the text to show
     * @param icon    the icon to show
     * @param action  the action to perform when the button is pressed
     * @return        this instance
     */
    default MenuBarTabHandler addMenuItem(String text, SpeedmentIcon icon, EventHandler<ActionEvent> action) {
        return set(text.replace("_", "").replace(" ", "-").toLowerCase(), () -> {
            final MenuItem item = new MenuItem(text);
            item.setGraphic(icon.view());
            item.setOnAction(action);
            return item;
        });
    }

    /**
     * Adds the specified factory last in the list of factories for this tab.
     * The new factory will not be given any key so it can only be accessed
     * using its index.
     *
     * @param factory  the factory to add
     * @return  this instance
     */
    MenuBarTabHandler add(MenuItemFactory factory);

    /**
     * Set which factory is responsible for the specified key in the handler.
     * This will replace any existing factory mapped to that key. The order in
     * which the factories are applied will not change, the new factory will
     * take the index of the previous one.
     *
     * @param key      the key to set
     * @param factory  the factory for that key
     * @return  this instance
     */
    MenuBarTabHandler set(String key, MenuItemFactory factory);

    /**
     * Inserts the specified factory at the specified index, incrementing the
     * index of any factory already there, as well as any following factories.
     *
     * @param index    the index to insert it at
     * @param factory  the factory to insert
     * @return  this instance
     * @throws IndexOutOfBoundsException  if the specified index is {@code < 0}
     *   or {@code >} the number of factories installed
     */
    MenuBarTabHandler insert(int index, MenuItemFactory factory);

    /**
     * Removes the factory with the specified key and returns {@code true}. If
     * no factory with that key existed, then {@code false} is returned.
     *
     * @param key  the factory key to remove
     * @return if the factory was removed
     */
    boolean remove(String key);

    /**
     * Returns the index of the factory with the specified key, or {@code -1} if
     * no factory with the specified key exists in the handler.
     *
     * @param key  the key to look for
     * @return     the index of the factory with that key
     */
    int indexOf(String key);

    /**
     * Clears all children in the specified {@link Menu}, then creates a new
     * instance for each factory installed in this handler and adds them to the
     * menu bar in the order they were installed.
     *
     * @param menu  the menu to populate
     */
    void populate(Menu menu);

}
