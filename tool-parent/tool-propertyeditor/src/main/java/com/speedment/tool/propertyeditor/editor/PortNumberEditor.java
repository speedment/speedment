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

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.util.DatabaseUtil;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.DefaultSpinnerItem;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;

import java.util.stream.Stream;

/**
 *
 * @param <T>  the document type
 * 
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public class PortNumberEditor<T extends DbmsProperty> implements PropertyEditor<T> {

    public @Inject DbmsHandlerComponent dbmsHandler;
    
    @Override
    public Stream<Item> fieldsFor(T document) {                    
        return Stream.of(new DefaultSpinnerItem(
            "Port", 
            defaultPortProperty(document, dbmsHandler), 
            document.portProperty(), 
            "The port of the database on the database host.", 
            0, 65535
        ));
    }

    private IntegerBinding defaultPortProperty(T document, DbmsHandlerComponent dbmsHandlerComponent) {
        return Bindings.createIntegerBinding(() -> 
            DatabaseUtil.findDbmsType(dbmsHandlerComponent, document).getDefaultPort(),
            document.typeNameProperty()
        );
    }    
}