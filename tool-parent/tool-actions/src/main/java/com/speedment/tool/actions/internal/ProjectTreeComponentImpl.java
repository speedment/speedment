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
package com.speedment.tool.actions.internal;

import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.tool.actions.ProjectTreeComponent;
import com.speedment.tool.config.DocumentProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeCell;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.stream.Collectors.toList;

/**
 * Default implementation of the {@link ProjectTreeComponent}-interface. This
 * implementation is concurrent.
 *
 * @author Emil Forslund
 * @since  3.0.17
 */
public final class ProjectTreeComponentImpl implements ProjectTreeComponent {

    private final Map<Class<?>, List<ContextMenuBuilder<?>>> contextMenuBuilders;

    /**
     * This class is intended to be dependency injected. This constructor should
     * therefore not be invoked directly.
     */
    public ProjectTreeComponentImpl() {
        contextMenuBuilders = new ConcurrentHashMap<>();
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface>
    void installContextMenu(Class<? extends DOC> nodeType,
                            ContextMenuBuilder<DOC> menuBuilder) {

        contextMenuBuilders.computeIfAbsent(nodeType,
            k -> new CopyOnWriteArrayList<>()
        ).add(menuBuilder);
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface>
    Optional<ContextMenu> createContextMenu(TreeCell<DocumentProperty> treeCell,
                                            DOC doc) {
        requireNonNulls(treeCell, doc);

        @SuppressWarnings("unchecked")
        final List<ContextMenuBuilder<DOC>> builders =
            MapStream.of(contextMenuBuilders)
                .filterKey(clazz -> clazz.isAssignableFrom(doc.getClass()))
                .values()
                .flatMap(List::stream)
                .map(builder -> (ContextMenuBuilder<DOC>) builder)
                .collect(toList());

        final ContextMenu menu = new ContextMenu();
        for (int i = 0; i < builders.size(); i++) {
            final List<MenuItem> items = builders.get(i)
                .build(treeCell, doc)
                .collect(toList());

            if (i > 0 && !items.isEmpty()) {
                menu.getItems().add(new SeparatorMenuItem());
            }

            items.forEach(menu.getItems()::add);
        }

        if (menu.getItems().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(menu);
        }
    }
}
