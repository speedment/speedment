package com.speedment.plugins.json.internal;

import com.speedment.plugins.json.TestUtil;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class JsonUtilTest {

    private static final String COLUMN_NAME = "bigint_unsigned";

    @Test
    void jsonField() {
        final Project project = TestUtil.project();
        final ColumnIdentifier<?> columnIdentifier = ColumnIdentifier.of("speedment_test","speedment_test", "unsigned_test", COLUMN_NAME);
        final String jsonFieldName = JsonUtil.jsonField(project, columnIdentifier);
        assertEquals(COLUMN_NAME, jsonFieldName);
    }
}