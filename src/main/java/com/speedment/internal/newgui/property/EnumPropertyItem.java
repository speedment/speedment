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

import java.util.Arrays;
import static java.util.Objects.requireNonNull;
import javafx.beans.property.Property;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @author Emil Forslund
 * @param <E> the enum type
 */
public final class EnumPropertyItem<E extends Enum<E>> extends AbstractPropertyItem<E, Property<E>> {
    
    private final Class<E> enumType;

    public EnumPropertyItem(Class<E> enumType, Property<E> property, String category, String name, String description) {
        super(property, category, name, description);
        this.enumType = requireNonNull(enumType);
    }

    @Override
    public Class<E> getType() {
        return enumType;
    }
    
    @Override
    public PropertyEditor<?> createEditor() {
        return Editors.createChoiceEditor(this, Arrays.asList(enumType.getEnumConstants()));
    }
}
