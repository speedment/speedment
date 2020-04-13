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
import com.speedment.tool.core.menubar.MenuBarTabHandler;
import com.speedment.tool.core.menubar.MenuItemFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link MenuBarTabHandler}.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public class MenuBarTabHandlerImpl implements MenuBarTabHandler {

    private final Map<String, MenuItemFactory> byKey;
    private final List<MenuItemFactory> factories;
    private final Injector injector;

    MenuBarTabHandlerImpl(Injector injector) {
        this.injector  = requireNonNull(injector);
        this.byKey     = new HashMap<>();
        this.factories = new ArrayList<>();
    }

    @Override
    public MenuBarTabHandler add(MenuItemFactory factory) {
        factories.add(injector.inject(factory));
        return this;
    }

    @Override
    public MenuBarTabHandler set(String key, MenuItemFactory factory) {
        injector.inject(factory);
        final MenuItemFactory oldFactory = byKey.put(key, factory);
        if (oldFactory == null) {
            factories.add(factory);
        } else {
            factories.set(factories.indexOf(oldFactory), factory);
        }
        return this;
    }

    @Override
    public MenuBarTabHandler insert(int index, MenuItemFactory factory) {
        factories.add(index, injector.inject(factory));
        return this;
    }

    @Override
    public boolean remove(String key) {
        final MenuItemFactory oldFactory = byKey.remove(key);
        if (oldFactory == null) {
            return false;
        } else {
            factories.remove(oldFactory);
            return true;
        }
    }

    @Override
    public int indexOf(String key) {
        final MenuItemFactory factory = byKey.get(key);
        if (factory == null) return -1;
        else return factories.indexOf(factory);
    }

    @Override
    public void populate(Menu menu) {
        final ObservableList<MenuItem> items = menu.getItems();
        items.clear();
        for (final MenuItemFactory factory : factories) {
            items.add(factory.createMenuItem());
        }
    }
}
