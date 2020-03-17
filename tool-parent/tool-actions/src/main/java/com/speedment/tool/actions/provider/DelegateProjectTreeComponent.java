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
package com.speedment.tool.actions.provider;

import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.tool.actions.ProjectTreeComponent;
import com.speedment.tool.actions.internal.ProjectTreeComponentImpl;
import com.speedment.tool.config.DocumentProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;

import java.util.Optional;

/**
 * Delegated implementation of the {@link ProjectTreeComponent}-interface. This
 * implementation is concurrent.
 *
 * @author Per
 * @since  3.2.0
 */
public final class DelegateProjectTreeComponent implements ProjectTreeComponent {

    private final ProjectTreeComponentImpl inner;

    public DelegateProjectTreeComponent() {
        this.inner = new ProjectTreeComponentImpl();
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface> void installContextMenu(Class<? extends DOC> nodeType, ContextMenuBuilder<DOC> menuBuilder) {
        inner.installContextMenu(nodeType, menuBuilder);
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface> Optional<ContextMenu> createContextMenu(TreeCell<DocumentProperty> treeCell, DOC doc) {
        return inner.createContextMenu(treeCell, doc);
    }
}
