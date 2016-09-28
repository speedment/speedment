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
package com.speedment.tool.property.editor;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.tool.property.item.ChoiceBoxItem;

import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.config.parameter.DbmsType;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import static javafx.collections.FXCollections.observableList;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
public class DbmsTypePropertyEditor<T extends DbmsProperty> implements PropertyEditor<T>{

    private @Inject DbmsHandlerComponent dbmsHandler;
    
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
