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
package com.speedment.internal.ui.property;

import static com.speedment.internal.ui.property.AbstractPropertyItem.defaultDecorator;
import com.speedment.internal.ui.util.EditorsUtil;
import java.util.function.Consumer;
import javafx.beans.property.StringProperty;
import org.controlsfx.property.editor.PropertyEditor;

/**
 * A property item for strings that gives the user a bit more space to enter
 * longer pieces of text.
 * 
 * @author Emil Forslund
 */
public final class StringAreaPropertyItem extends AbstractPropertyItem<String, StringProperty, PropertyEditor<?>> {

    public StringAreaPropertyItem(StringProperty value, String name, String description) {
        super(value, name, description, defaultDecorator());
    }
    
    public StringAreaPropertyItem(StringProperty value, String name, String description, Consumer<PropertyEditor<?>> decorator) {
        super(value, name, description, decorator);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    protected PropertyEditor<?> createUndecoratedEditor() {
        return EditorsUtil.createTextAreaEditor(this);
    }
}