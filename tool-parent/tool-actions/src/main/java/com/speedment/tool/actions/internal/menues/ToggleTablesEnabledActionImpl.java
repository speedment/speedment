/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
import com.speedment.tool.actions.menues.ToggleTablesEnabledAction;
import com.speedment.tool.config.SchemaProperty;
import com.speedment.tool.config.TableProperty;
import javafx.scene.control.MenuItem;

import java.util.stream.Stream;

/**
 * Default implementation of the {@link ToggleTablesEnabledAction}-interface.
 *
 * @author Emil Forslund
 * @since  3.0.17
 */
public final class ToggleTablesEnabledActionImpl
extends AbstractToolAction
implements ToggleTablesEnabledAction {

    @Override
    void installMenuItems(ProjectTreeComponent projectTree) {
        projectTree.installContextMenu(SchemaProperty.class, (treeCell, node) -> {
            final MenuItem enableTables  = new MenuItem("Enable All Tables");
            final MenuItem disableTables = new MenuItem("Disable All Tables");

            enableTables.setOnAction(ev ->
                DocumentUtil.traverseOver(node)
                    .filter(TableProperty.class::isInstance)
                    .forEach(doc -> {
                        TableProperty tableProperty = (TableProperty) doc;

                        tableProperty.enabledProperty().setValue(true);
                        setChildrenState(tableProperty, true);
                    })
            );

            disableTables.setOnAction(ev ->
                DocumentUtil.traverseOver(node)
                    .filter(TableProperty.class::isInstance)
                    .forEach(doc -> {
                        TableProperty tableProperty = (TableProperty) doc;

                        tableProperty.enabledProperty().setValue(false);
                        setChildrenState(tableProperty, false);
                    })
            );

            return Stream.of(enableTables, disableTables);
        });
    }

    private void setChildrenState(final TableProperty tableProperty, boolean state) {
        tableProperty.columnsProperty().forEach(column -> column.enabledProperty().setValue(state));
        tableProperty.primaryKeyColumnsProperty().forEach(pk -> pk.enabledProperty().setValue(state));
        tableProperty.indexesProperty().forEach(index -> index.enabledProperty().setValue(state));
    }
}
