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

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.tool.config.trait.HasPackageNameProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.DefaultTextFieldItem;

import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
public class PackageNameEditor<T extends HasPackageNameProperty> implements PropertyEditor<T>{

    public @Inject Injector injector;
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of( 
            new DefaultTextFieldItem(
                "Package Name",
                document.defaultPackageNameProperty(injector),
                document.packageNameProperty(),
                "The package where generated classes will be located."
            )
        );
    }    
}
