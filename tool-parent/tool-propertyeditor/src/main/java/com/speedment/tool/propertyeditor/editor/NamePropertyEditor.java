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


import com.speedment.tool.config.trait.HasNameProperty;
import com.speedment.tool.config.trait.HasNameProtectedProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.SimpleTextFieldItem;

import java.util.stream.Stream;

import static com.speedment.tool.propertyeditor.item.ItemUtil.lockDecorator;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
public class NamePropertyEditor<T extends HasNameProperty> implements PropertyEditor<T>{

    @Override
    public Stream<PropertyEditor.Item> fieldsFor(T document) {
        return Stream.of(new SimpleTextFieldItem(
            document.mainInterface().getSimpleName() + " Name",
            document.nameProperty(),
            "The name of the persisted entity in the database. This should only be modified if the database has been changed!",
            editor -> document instanceof HasNameProtectedProperty ?
                lockDecorator(editor, document,
                "This field should ONLY be changed to reflect changes made in the underlying database. "
                + "If you want to change the name of this entity in Java, consider editing the Alias field instead."
                + "\nEnable editing by by right clicking on the field."
            ) : editor
        ));
    }
}
