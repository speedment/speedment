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
