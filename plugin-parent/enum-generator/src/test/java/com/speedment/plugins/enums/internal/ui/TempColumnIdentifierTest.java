package com.speedment.plugins.enums.internal.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class TempColumnIdentifierTest {

    private static final String DB = "db";
    private static final String SCHEMA = "schema";
    private static final String TABLE = "table";
    private static final String COLUMN = "column";

    private static final TempColumnIdentifier INSTANCE = new TempColumnIdentifier(DB, SCHEMA, TABLE, COLUMN);

    @Test
    void getDbmsId() {
        assertEquals(DB, INSTANCE.getDbmsId());
    }

    @Test
    void getTableId() {
        assertEquals(TABLE, INSTANCE.getTableId());
    }

    @Test
    void getColumnId() {
        assertEquals(COLUMN, INSTANCE.getColumnId());
    }

    @Test
    void getSchemaId() {
        assertEquals(SCHEMA, INSTANCE.getSchemaId());
    }

    @Test
    void testEquals() {
        final TempColumnIdentifier other = new TempColumnIdentifier(DB, SCHEMA, TABLE, COLUMN + "X");
        assertEquals(INSTANCE, INSTANCE);
        assertFalse(INSTANCE.equals(null));
        assertFalse(INSTANCE.equals(1));
        assertNotEquals(INSTANCE, other);
        assertNotEquals(other, INSTANCE);
    }

    @Test
    void testHashCode() {
        final TempColumnIdentifier other = new TempColumnIdentifier(DB, SCHEMA, TABLE, COLUMN + "X");
        assertNotEquals(INSTANCE.hashCode(), other.hashCode());
    }

    @Test
    void testToString() {
        final String actual = INSTANCE.toString();
        assertTrue(actual.contains(DB));
        assertTrue(actual.contains(SCHEMA));
        assertTrue(actual.contains(TABLE));
        assertTrue(actual.contains(COLUMN));
    }
}