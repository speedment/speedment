/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import com.speedment.plugins.enums.StringToEnumTypeMapper;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.core.property.PropertyEditor;
import javafx.beans.binding.Bindings;

import java.util.stream.Stream;

/**
 * Editor for generating a comma-separated string.
 * <p>
 * We parse what values an enum should be able to take from a string, where
 * each element is separated by a comma. This editor allows the user
 * to easily edit such a string.
 * 
 * @author Simon Jonasson
 * @since 1.0.0
 */
public class CommaSeparatedStringEditor<T extends ColumnProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(
            new AddRemoveStringItem(
                "Enum Constants", 
                document.enumConstantsProperty(),
                "Used for defining what contants the generated enum can have",
                Bindings.equal(document.typeMapperProperty(), StringToEnumTypeMapper.class.getName())
            )
        );
    }
}
