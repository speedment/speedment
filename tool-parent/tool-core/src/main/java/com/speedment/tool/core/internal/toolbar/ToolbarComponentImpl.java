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
package com.speedment.tool.core.internal.toolbar;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.tool.core.toolbar.ToolbarComponent;
import com.speedment.tool.core.toolbar.ToolbarItem;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static com.speedment.common.injector.State.INITIALIZED;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link ToolbarComponent}-interface.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public final class ToolbarComponentImpl implements ToolbarComponent {

    private final Map<String, ToolbarItem<?>> byKey;
    private final List<ToolbarItem<?>> leftSide;
    private final List<ToolbarItem<?>> rightSide;
    private Injector injector;

    public ToolbarComponentImpl() {
        this.byKey     = new HashMap<>();
        this.leftSide  = new ArrayList<>();
        this.rightSide = new ArrayList<>();
    }

    @ExecuteBefore(INITIALIZED)
    public void setInjector(Injector injector) {
        this.injector = requireNonNull(injector);
    }

    @Override
    public void install(ToolbarItem<Button> item) {
        install(injector.inject(item).createNode().getText(), item);
    }

    @Override
    public <T extends Node> void install(String key, ToolbarItem<T> item) {
        injector.inject(item);
        final ToolbarItem<?> oldItem = byKey.put(key, item);
        if (oldItem == null) {
            switch (item.getSide()) {
                case LEFT:  leftSide.add(item); break;
                case RIGHT: rightSide.add(item); break;
                default: throw new UnsupportedOperationException(format(
                    "Unknown enum constant '%s'.", item.getSide()));
            }
        } else {
            switch (oldItem.getSide()) {
                case LEFT:  leftSide.set(leftSide.indexOf(oldItem), item); break;
                case RIGHT: rightSide.set(rightSide.indexOf(oldItem), item); break;
                default: throw new UnsupportedOperationException(format(
                    "Unknown enum constant '%s'.", item.getSide()));
            }
        }
    }

    @Override
    public void populate(Pane parent) {
        final ObservableList<Node> children = parent.getChildren();
        children.clear();

        leftSide.stream()
            .map(ToolbarItem::createNode)
            .forEachOrdered(children::add);

        final Pane separator = new Pane();
        separator.setPrefHeight(1);
        HBox.setHgrow(separator, Priority.ALWAYS);
        children.add(separator);

        final ListIterator<ToolbarItem<?>> it = rightSide.listIterator(rightSide.size());
        while (it.hasPrevious()) {
            children.add(it.previous().createNode());
        }
    }
}
