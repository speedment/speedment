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
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.ChoiceBoxItem;
import javafx.collections.ObservableList;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableList;

/**
 *
 * @param <T>  the document type
 * 
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public class DbmsTypePropertyEditor<T extends DbmsProperty> implements PropertyEditor<T> {

    public @Inject DbmsHandlerComponent dbmsHandler;
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        final ObservableList<String> supportedTypes = observableList(
            dbmsHandler
                .supportedDbmsTypes()
                .map(DbmsType::getName)
                .collect(toList())
        );
        
        return Stream.of(new ChoiceBoxItem<String>(
                "Dbms Type",
                document.typeNameProperty(),
                supportedTypes,
                "Which type of database this is. If the type you are looking " +
                "for is missing, make sure it has been loaded properly in "    +
                "the pom.xml-file."
            )
        );
    }
}
