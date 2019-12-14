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