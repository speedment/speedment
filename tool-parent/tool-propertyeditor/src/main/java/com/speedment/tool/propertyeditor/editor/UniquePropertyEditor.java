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


import com.speedment.tool.config.IndexProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.ItemUtil;
import com.speedment.tool.propertyeditor.item.SimpleCheckBoxItem;

import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
public class UniquePropertyEditor<T extends IndexProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(new SimpleCheckBoxItem(
                "Is Unique", 
                document.uniqueProperty(), 
                "True if elements in this index are unique.",
                editor -> ItemUtil.lockDecorator(editor, document, ItemUtil.DATABASE_RELATION_TOOLTIP)
            )
        );
    }
    
}
