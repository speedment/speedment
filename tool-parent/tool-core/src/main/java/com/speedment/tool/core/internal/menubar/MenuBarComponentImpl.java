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

    public MenuBarComponentImpl() {
        handlers = new EnumMap<>(MenuBarTab.class);
    }

    @ExecuteBefore(State.INITIALIZED)
    public void populateHandlers(Injector injector) {
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

        for (final Map.Entry<MenuBarTab, MenuBarTabHandler> e : handlers.entrySet()) {
            final Menu menu = new Menu(e.getKey().getText());
            e.getValue().populate(menu);
            if (!menu.getItems().isEmpty()) {
                menus.add(menu);
            }
        }
    }
}
