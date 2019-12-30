package com.speedment.plugins.enums.internal.ui;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.db.SqlFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class SingleColumnSqlAdapterTest {

    @Mock
    private ResultSet resultSet;

    private static final String DB = "db";
    private static final String SCHEMA = "schema";
    private static final String TABLE = "table";
    private static final String COLUMN = "column";

    private static final SingleColumnSqlAdapter INSTANCE = new SingleColumnSqlAdapter(DB, SCHEMA, TABLE, COLUMN);

    @Test
    void identifier() {
        final TableIdentifier<?> expected = TableIdentifier.of(DB, SCHEMA, TABLE);
        assertSame(expected, INSTANCE.identifier());
    }

    @Test
    void entityMapper() throws SQLException {
        final String expected = "A";
        when(resultSet.getString(COLUMN)).thenReturn(expected);
        final SqlFunction<ResultSet, String> mapper = INSTANCE.entityMapper();
        final String actual = mapper.apply(resultSet);
        assertEquals(expected, actual);
    }

    @Test
    void testEntityMapper() throws SQLException {
        final String expected = "A";
        when(resultSet.getString(COLUMN)).thenReturn(expected);
        final SqlFunction<ResultSet, String> mapper = INSTANCE.entityMapper(7);
        final String actual = mapper.apply(resultSet);
        assertEquals(expected, actual);
    }
}