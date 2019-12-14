package com.speedment.tool.actions.internal.menues;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.speedment.tool.actions.internal.menues.ToggleTablesEnabledActionImpl.DISABLE_ALL_TABLES;
import static com.speedment.tool.actions.internal.menues.ToggleTablesEnabledActionImpl.ENABLE_ALL_TABLES;

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
    @DisplayName("'Toggle Columns' shows up in context menu")
    void testMenuItemShowsUp() {
        assertMenuExistsFor(schema, ENABLE_ALL_TABLES);
        assertMenuExistsFor(schema, DISABLE_ALL_TABLES);
    }
}