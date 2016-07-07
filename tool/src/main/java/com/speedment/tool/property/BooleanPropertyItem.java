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
package com.speedment.tool.property;

import com.speedment.runtime.annotation.Api;
import javafx.beans.property.BooleanProperty;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.function.Consumer;

/**
 *
 * @author  Emil Forslund
 * @since   2.2.0
 */
@Api(version="3.0")
public final class BooleanPropertyItem extends AbstractPropertyItem<Boolean, BooleanProperty, PropertyEditor<?>> {

    public BooleanPropertyItem(BooleanProperty value, String name, String description) {
        super(value, name, description, defaultDecorator());
    }
    
    public BooleanPropertyItem(BooleanProperty value, String name, String description, Consumer<PropertyEditor<?>> decorator) {
        super(value, name, description, decorator);
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    protected PropertyEditor<?> createUndecoratedEditor() {
        return Editors.createCheckEditor(this);
    }
}