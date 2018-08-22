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
