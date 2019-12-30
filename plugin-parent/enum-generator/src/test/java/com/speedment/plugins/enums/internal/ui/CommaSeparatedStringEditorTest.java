package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.injector.Injector;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.config.trait.HasEnumConstantsProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
final class CommaSeparatedStringEditorTest {

/*    @Mock
    ColumnProperty columnProperty;

    @Mock
    StringProperty enumConstants;*/

    @Mock
    private HasEnumConstantsProperty hasEnumConstantsProperty;
    @Mock
    private StringProperty enumConstants;

    @Test
    void fieldsFor() throws InstantiationException {
        final Injector injector = Injector.builder().build();

        final CommaSeparatedStringEditor<ColumnProperty> editor = new CommaSeparatedStringEditor<>();
        injector.inject(editor);

        when(hasEnumConstantsProperty.enumConstantsProperty())
        assertThrows(Exception.class, () -> editor.fieldsFor(hasEnumConstantsProperty));

    }

}