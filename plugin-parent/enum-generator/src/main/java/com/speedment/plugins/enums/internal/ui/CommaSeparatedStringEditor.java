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
import com.speedment.common.injector.annotation.Inject;
import com.speedment.plugins.enums.StringToEnumTypeMapper;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.tool.config.trait.HasEnumConstantsProperty;
import com.speedment.tool.config.trait.HasTypeMapperProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import javafx.beans.binding.Bindings;

import java.util.stream.Stream;

/**
 * Editor for generating a comma-separated string.
 * <p>
 * We parse what values an enum should be able to take from a string, where
 * each element is separated by a comma. This editor allows the user
 * to easily edit such a string.
 * 
 * @param <DOC>  the column property type
 * 
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public class CommaSeparatedStringEditor
    <DOC extends HasEnumConstantsProperty
               & HasTypeMapperProperty
               & HasParent<? extends Table>
               & HasName>
implements PropertyEditor<HasEnumConstantsProperty> {

    @Inject public Injector injector;

    @Override
    public Stream<Item> fieldsFor(HasEnumConstantsProperty column) {

        final DOC document;
        try {
            @SuppressWarnings("unchecked")
            final DOC doc = (DOC) column;
            document = doc;
        } catch (final ClassCastException ex) {
            throw new SpeedmentException("Expected document to be a valid column, but was a '" + column.getClass().getName() + "'.");
        }

        return Stream.of(
            new AddRemoveStringItem<>(
                document,
                "Enum Constants", 
                document.enumConstantsProperty(),
                "Used for defining what constants the generated enum can have",
                Bindings.equal(document.typeMapperProperty(), StringToEnumTypeMapper.class.getName())
            )
        ).map(injector::inject);
    }
}
