package com.speedment.plugins.enums.internal;

import com.speedment.plugins.enums.TestUtil;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.util.DocumentDbUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

final class EnumGeneratorUtilTest {

    @Test
    void enumConstantsOfWithNoEnumConstants() {
        final Project project = TestUtil.project();
        final Column column = DocumentDbUtil.referencedColumn(project,"speedment_test","speedment_test", "user", "surname");
        assertThrows(NoSuchElementException.class, () -> EnumGeneratorUtil.enumConstantsOf(column));
    }
}