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

import com.speedment.runtime.config.trait.HasEnabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.speedment.tool.actions.internal.menues.ToggleTablesEnabledActionImpl.DISABLE_ALL_TABLES;
import static com.speedment.tool.actions.internal.menues.ToggleTablesEnabledActionImpl.ENABLE_ALL_TABLES;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
class ToggleTablesEnabledActionImplTest extends AbstractToolActionTest {

    @Override
    AbstractToolAction newToolAction() {
        return new ToggleTablesEnabledActionImpl();
    }

    @Test
    @DisplayName("Table context menu not empty")
    void testTableContextMenuNotEmpty() {
        assertAnyMenuExistsFor(schema);
    }

    @Test
    @DisplayName("'Enable/Disable tables' shows up in context menu")
    void testMenuItemShowsUp() {
        assertMenuExistsFor(schema, ENABLE_ALL_TABLES);
        assertMenuExistsFor(schema, DISABLE_ALL_TABLES);
    }

    @Test
    @DisplayName("Test clicking on 'Enable All Tables'")
    void testToggle() {
        installContextMenu();

        clickOnMenuButton(schema, ENABLE_ALL_TABLES);
        assertTrue(schema.tables().allMatch(HasEnabled::isEnabled),
            "All tables are not enabled");

        clickOnMenuButton(schema, DISABLE_ALL_TABLES);
        assertTrue(schema.tables().noneMatch(HasEnabled::isEnabled),
            "All tables are not disabled");
    }
}