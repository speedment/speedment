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
    implements AbstractToolAction, ToggleTablesEnabledAction {

    static final String ENABLE_ALL_TABLES  = "Enable All Tables";
    static final String DISABLE_ALL_TABLES = "Disable All Tables";

    @Override
    public void installMenuItems(ProjectTreeComponent projectTree) {
        projectTree.installContextMenu(SchemaProperty.class, (treeCell, node) -> {
            final MenuItem enableTables  = new MenuItem(ENABLE_ALL_TABLES);
            final MenuItem disableTables = new MenuItem(DISABLE_ALL_TABLES);

            enableTables.setOnAction(ev ->
                DocumentUtil.traverseOver(node)
                    .filter(TableProperty.class::isInstance)
                    .forEach(doc -> ((TableProperty) doc).enabledProperty().setValue(true))
            );

            disableTables.setOnAction(ev ->
                DocumentUtil.traverseOver(node)
                    .filter(TableProperty.class::isInstance)
                    .forEach(doc -> ((TableProperty) doc).enabledProperty().setValue(false))
            );

            return Stream.of(enableTables, disableTables);
        });
    }
}
