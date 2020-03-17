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
package com.speedment.tool.propertyeditor.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A component for drawing certain editable UI properties.
 *
 * @author  Simon Jonasson
 * @since   3.0.0
 */
@InjectKey(PropertyEditorComponent.class)
public interface PropertyEditorComponent {
    
    /**
     * Retrieves a Stream of UI editor items, that's been installed under 
     * a certain document type.
     * 
     * @param <DOC>    the document type
     * @param document the document
     * @return         all editor items for the document
     */
    <DOC extends DocumentProperty> Stream<PropertyEditor.Item> getUiVisibleProperties(DOC document);
    
    /**
     * Installs a new supplier for an editor item under a certain document type 
     * and key. 
     * <P>
     * Installations also has to provide a unique key for the particular editor. 
     * If two editors are installed on the same document type, with the same 
     * key, only the last installed editor will be produced.
     * 
     * @param <DOC>         the document type
     * @param documentType  the document type
     * @param propertyKey   the property key
     * @param factory       supplier for creating the editor
     */
    <DOC extends DocumentProperty> void install(Class<DOC> documentType, String propertyKey, Supplier<PropertyEditor<DOC>> factory);    
    
}