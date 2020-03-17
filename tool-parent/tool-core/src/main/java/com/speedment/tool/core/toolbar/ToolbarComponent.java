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
package com.speedment.tool.core.toolbar;

import com.speedment.common.injector.annotation.InjectKey;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Used by Tool plugins to add additional controllers to the 'toolbar' section.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
@InjectKey(ToolbarComponent.class)
public interface ToolbarComponent {

    /**
     * Installs the specified {@link ToolbarItem}. This is a shorthand for
     * {@link #install(String, ToolbarItem)} that uses the
     * {@link Button#getText()} as the key.
     *
     * @param item  the new item
     */
    void install(ToolbarItem<Button> item);

    /**
     * Installs the specified {@link ToolbarItem} with the specified key. If an
     * item with the same key already exists, then this will overwrite that item
     * and the new item will be inserted into the same position and side as the
     * old one.
     *
     * @param <T>   node type
     * @param key   the key to identify it with
     * @param item  the item to install
     */
    <T extends Node> void install(String key, ToolbarItem<T> item);

    /**
     * Appends all the items of the toolbar as children to the specified parent.
     *
     * @param parent  the parent to add tools to
     */
    void populate(Pane parent);

}
