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


        assertThrows(Exception.class, () -> editor.fieldsFor(hasEnumConstantsProperty));

    }

}