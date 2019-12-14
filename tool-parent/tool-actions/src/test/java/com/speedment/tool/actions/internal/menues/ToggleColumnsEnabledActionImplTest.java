package com.speedment.tool.actions.internal.menues;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.speedment.tool.actions.internal.menues.ToggleColumnsEnabledActionImpl.DISABLE_ALL_COLUMNS;
import static com.speedment.tool.actions.internal.menues.ToggleColumnsEnabledActionImpl.ENABLE_ALL_COLUMNS;

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
}