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

import static com.speedment.tool.actions.internal.menues.ToggleColumnsEnabledActionImpl.DISABLE_ALL_COLUMNS;
import static com.speedment.tool.actions.internal.menues.ToggleColumnsEnabledActionImpl.ENABLE_ALL_COLUMNS;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
class ToggleColumnsEnabledActionImplTest extends AbstractToolActionTest {

    @Override
    AbstractToolAction newToolAction() {
        return new ToggleColumnsEnabledActionImpl();
    }

    @Test
    @DisplayName("Table context menu not empty")
    void testTableContextMenuNotEmpty() {
        assertAnyMenuExistsFor(table1);
        assertAnyMenuExistsFor(table2);
    }

    @Test
    @DisplayName("'Toggle Columns' shows up in context menu")
    void testMenuItemShowsUp() {
        assertMenuExistsFor(table1, ENABLE_ALL_COLUMNS);
        assertMenuExistsFor(table2, ENABLE_ALL_COLUMNS);
        assertMenuExistsFor(table1, DISABLE_ALL_COLUMNS);
        assertMenuExistsFor(table2, DISABLE_ALL_COLUMNS);
    }

    @Test
    @DisplayName("Test clicking on 'Toggle Columns'")
    void testToggle() {
        installContextMenu();

        clickOnMenuButton(table1, ENABLE_ALL_COLUMNS);
        assertTrue(table1.columns().allMatch(HasEnabled::isEnabled),
            "All columns are not enabled");

        clickOnMenuButton(table1, DISABLE_ALL_COLUMNS);
        assertTrue(table1.columns().noneMatch(HasEnabled::isEnabled),
            "All columns are not disabled");
    }
}