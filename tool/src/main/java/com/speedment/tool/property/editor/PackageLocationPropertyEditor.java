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

import com.speedment.runtime.annotation.Api;
import static com.speedment.runtime.config.Project.DEFAULT_PACKAGE_LOCATION;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.DefaultTextFieldItem;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class PackageLocationPropertyEditor <T extends ProjectProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(
            new DefaultTextFieldItem(
                "Package Location",
                new SimpleStringProperty(DEFAULT_PACKAGE_LOCATION),
                document.packageLocationProperty(),
                "The folder to store all generated files in. This should be a relative name from the working directory."
            )
        );
    }
    
}
