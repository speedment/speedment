package com.speedment.tool.core.internal.menubar;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.tool.core.menubar.MenuBarComponent;
import com.speedment.tool.core.menubar.MenuBarTab;
import com.speedment.tool.core.menubar.MenuBarTabHandler;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import java.util.EnumMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link MenuBarComponent}.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public final class MenuBarComponentImpl implements MenuBarComponent {

    private final Map<MenuBarTab, MenuBarTabHandler> handlers;

    MenuBarComponentImpl() {
        handlers = new EnumMap<>(MenuBarTab.class);
    }

    @ExecuteBefore(State.INITIALIZED)
    void populateHandlers(Injector injector) {
        for (final MenuBarTab tab : MenuBarTab.values()) {
            handlers.put(tab, new MenuBarTabHandlerImpl(injector));
        }
    }

    @Override
    public MenuBarTabHandler forTab(MenuBarTab tab) {
        return requireNonNull(handlers.get(tab));
    }

    @Override
    public void populate(MenuBar menuBar) {
        final ObservableList<Menu> menus = menuBar.getMenus();
        menus.clear();

        for (final MenuBarTab tab : handlers.keySet()) {
            final Menu menu = new Menu(tab.getText());
            handlers.get(tab).populate(menu);
            if (!menu.getItems().isEmpty()) {
                menus.add(menu);
            }
        }
    }
}
