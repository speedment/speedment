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


import com.speedment.tool.config.ForeignKeyColumnProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.ItemUtil;
import com.speedment.tool.propertyeditor.item.SimpleTextFieldItem;

import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
public class ForeignKeyColumnEditor<T extends ForeignKeyColumnProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(new SimpleTextFieldItem(
                 "Foreign Database Name",
                 document.foreignDatabaseNameProperty(), 
                 "The name of the database that this foreign key references.",
                 editor -> ItemUtil.lockDecorator(editor, document, ItemUtil.DATABASE_RELATION_TOOLTIP)
            ),
            new SimpleTextFieldItem(
                "Foreign Schema Name",
                document.foreignSchemaNameProperty(), 
                "The name of the schema that this foreign key references.",
                 editor -> ItemUtil.lockDecorator(editor, document, ItemUtil.DATABASE_RELATION_TOOLTIP)
            ),
            new SimpleTextFieldItem(
                "Foreign Table Name",
                document.foreignTableNameProperty(), 
                "The name of the database table that this foreign key references.",
                 editor -> ItemUtil.lockDecorator(editor, document, ItemUtil.DATABASE_RELATION_TOOLTIP)
            ),
            new SimpleTextFieldItem(
                "Foreign Column Name",
                document.foreignColumnNameProperty(), 
                "The name of the database column that this foreign key references.",
                 editor -> ItemUtil.lockDecorator(editor, document, ItemUtil.DATABASE_RELATION_TOOLTIP)
            )
        );
    }
}
