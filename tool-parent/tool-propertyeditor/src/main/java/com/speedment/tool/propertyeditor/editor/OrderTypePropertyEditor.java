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


import com.speedment.runtime.config.parameter.OrderType;
import com.speedment.tool.config.trait.HasOrderTypeProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.ChoiceBoxItem;
import com.speedment.tool.propertyeditor.item.ItemUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
public class OrderTypePropertyEditor<T extends HasOrderTypeProperty> implements PropertyEditor<T>{
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        final ObservableList<OrderType> alternatives = 
            FXCollections.observableArrayList(OrderType.values());
        
        return Stream.of(
            new ChoiceBoxItem<>(
                "Order Type",
                document.orderTypeProperty(),
                alternatives,
                "The order in which elements will be considered.",
                editor -> ItemUtil.lockDecorator(editor, document, ItemUtil.DATABASE_RELATION_TOOLTIP)
                
            )
        );
    }
    
}
