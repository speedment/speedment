/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.newgui.property;

import javafx.beans.property.StringProperty;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @author Emil Forslund
 */
public final class StringPropertyItem extends AbstractPropertyItem<String, StringProperty> {

    public StringPropertyItem(StringProperty value, String name, String description) {
        super(value, name, description);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public PropertyEditor<?> createEditor() {
        return Editors.createTextEditor(this);
    }
}