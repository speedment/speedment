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

package com.speedment.tool.propertyeditor.editor;

import com.speedment.runtime.config.TableUtil;
import com.speedment.tool.config.TableProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.SimpleTextFieldItem;

import java.util.stream.Stream;

/**
 * @author Mislav Milicevic
 * @param <T> the document type
 * @since 3.2.9
 */
public class ImplementsEditor<T extends TableProperty> implements PropertyEditor<T> {

    private static final String TOOLTIP =
        "A comma-separated list of interfaces that this table will implement upon generation.\n"
        + "\n"
        + "Example: com.company.custom.MyInterface, com.company.custom.AnotherInterface";

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(
            new SimpleTextFieldItem(
                "Implements",
                document.stringPropertyOf(TableUtil.IMPLEMENTS, () -> ""),
                TOOLTIP
            )
        );
    }
}
