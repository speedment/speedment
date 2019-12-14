package com.speedment.tool.actions.internal.menues;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.speedment.tool.actions.internal.menues.ToggleExpandedActionImpl.COLLAPSE_ALL;
import static com.speedment.tool.actions.internal.menues.ToggleExpandedActionImpl.EXPAND_ALL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
class ToggleExpandedActionImplTest extends AbstractToolActionTest {

    @Override
    AbstractToolAction newToolAction() {
        return new ToggleExpandedActionImpl();
    }

    @Test
    @DisplayName("Table context menu not empty")
    void testTableContextMenuNotEmpty() {
        assertAnyMenuExistsFor(table1);
        assertAnyMenuExistsFor(table2);
    }

    @Test
    @DisplayName("Schema context menu not empty")
    void testSchemaContextMenuNotEmpty() {
        assertAnyMenuExistsFor(schema);
    }

    @Test
    @DisplayName("Dbms context menu not empty")
    void testDbmsContextMenuNotEmpty() {
        assertAnyMenuExistsFor(dbms);
    }

    @Test
    @DisplayName("'Expand/Collapse' shows up in context menu for tables")
    void testMenuItemShowsUpForTables() {
        assertMenuExistsFor(table1, EXPAND_ALL);
        assertMenuExistsFor(table2, COLLAPSE_ALL);
        assertMenuExistsFor(table1, EXPAND_ALL);
        assertMenuExistsFor(table2, COLLAPSE_ALL);
    }

    @Test
    @DisplayName("'Expand/Collapse' shows up in context menu for schema")
    void testMenuItemShowsUpForSchema() {
        assertMenuExistsFor(schema, EXPAND_ALL);
        assertMenuExistsFor(schema, COLLAPSE_ALL);
    }

    @Test
    @DisplayName("'Expand/Collapse' shows up in context menu for dbms")
    void testMenuItemShowsUpForDbms() {
        assertMenuExistsFor(dbms, EXPAND_ALL);
        assertMenuExistsFor(dbms, COLLAPSE_ALL);
    }

    @Test
    @DisplayName("Test clicking on 'Expand/Collapse' for table")
    void testToggleTable() {
        installContextMenu();

        clickOnMenuButton(table1, EXPAND_ALL);
        assertTrue(table1.isExpanded(), "Table is not expanded");

        clickOnMenuButton(table1, COLLAPSE_ALL);
        assertFalse(table1.isExpanded(), "Table is expanded");
    }

    @Test
    @DisplayName("Test clicking on 'Expand/Collapse' for schema")
    void testToggleSchema() {
        installContextMenu();

        clickOnMenuButton(schema, EXPAND_ALL);
        assertTrue(schema.isExpanded(), "Schema is not expanded");

        clickOnMenuButton(schema, COLLAPSE_ALL);
        assertFalse(schema.isExpanded(), "Schema is expanded");
    }

    @Test
    @DisplayName("Test clicking on 'Expand/Collapse' for schema")
    void testToggleDbms() {
        installContextMenu();

        clickOnMenuButton(dbms, EXPAND_ALL);
        assertTrue(dbms.isExpanded(), "Dbms is not expanded");

        clickOnMenuButton(dbms, COLLAPSE_ALL);
        assertFalse(dbms.isExpanded(), "Dbms is expanded");
    }
}