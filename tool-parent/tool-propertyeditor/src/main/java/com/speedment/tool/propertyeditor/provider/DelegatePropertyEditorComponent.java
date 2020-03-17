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
package com.speedment.tool.propertyeditor.provider;

import static com.speedment.common.injector.State.INITIALIZED;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.PropertyEditor.Item;
import com.speedment.tool.propertyeditor.component.PropertyEditorComponent;
import com.speedment.tool.propertyeditor.internal.component.PropertyEditorComponentImpl;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class DelegatePropertyEditorComponent implements PropertyEditorComponent {

    private final PropertyEditorComponentImpl propertyEditorComponent;

    public DelegatePropertyEditorComponent() {
        propertyEditorComponent = new PropertyEditorComponentImpl();
    }

    @ExecuteBefore(INITIALIZED)
    public void setInjector(Injector injector) {
        propertyEditorComponent.setInjector(injector);
    }

    @ExecuteBefore(INITIALIZED)
    public void installEditors() {
        propertyEditorComponent.installEditors();
    }

    @Override
    public <DOC extends DocumentProperty> Stream<Item> getUiVisibleProperties(DOC document) {
        return propertyEditorComponent.getUiVisibleProperties(document);
    }

    @Override
    public <DOC extends DocumentProperty> void install(Class<DOC> documentType,
            String propertyKey,
            Supplier<PropertyEditor<DOC>> factory) {
        propertyEditorComponent.install(documentType, propertyKey, factory);
    }

}
