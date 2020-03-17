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
package com.speedment.tool.actions.internal.menues;

import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.actions.ProjectTreeComponent;
import com.speedment.tool.actions.internal.resources.ProjectTreeIcon;
import com.speedment.tool.actions.menues.ToggleExpandedAction;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.SchemaProperty;
import com.speedment.tool.config.TableProperty;
import com.speedment.tool.config.trait.HasExpandedProperty;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

import java.util.stream.Stream;

/**
 * Default implementation of {@link ToggleExpandedAction}.
 *
 * @author Emil Forslund
 * @since  3.0.17
 */
public final class ToggleExpandedActionImpl
    implements AbstractToolAction, ToggleExpandedAction {

    static final String EXPAND_ALL   = "Expand All";
    static final String  COLLAPSE_ALL = "Collapse All";

    @Override
    public void installMenuItems(ProjectTreeComponent projectTree) {
        projectTree.installContextMenu(DbmsProperty.class, this::installForDocumentType);
        projectTree.installContextMenu(SchemaProperty.class, this::installForDocumentType);
        projectTree.installContextMenu(TableProperty.class, this::installForDocumentType);
    }

    private <DOC extends DocumentProperty> Stream<MenuItem>
    installForDocumentType(TreeCell<DocumentProperty> treeCell, DOC node) {
        final MenuItem expandAll   = new MenuItem(EXPAND_ALL, ProjectTreeIcon.BOOK_OPEN.view());
        final MenuItem collapseAll = new MenuItem(COLLAPSE_ALL, ProjectTreeIcon.BOOK.view());

        expandAll.setOnAction(ev ->
            DocumentUtil.traverseOver(node)
                .filter(HasExpandedProperty.class::isInstance)
                .forEach(doc -> ((HasExpandedProperty) doc).expandedProperty().setValue(true))
        );

        collapseAll.setOnAction(ev ->
            DocumentUtil.traverseOver(node)
                .filter(HasExpandedProperty.class::isInstance)
                .forEach(doc -> ((HasExpandedProperty) doc).expandedProperty().setValue(false))
        );

        return Stream.of(expandAll, collapseAll);
    }
}