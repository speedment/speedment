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

import java.util.Arrays;
import java.util.function.Consumer;
import javafx.beans.property.Property;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @param <E> the enum type
 */
public final class EnumPropertyItem<E extends Enum<E>> extends AbstractPropertyItem<E, Property<E>> {
    
    private final Class<E> enumType;
    
    public EnumPropertyItem(Class<E> enumType, Property<E> property, String name, String description) {
        super(property, name, description, AbstractPropertyItem.DEFAULT_DECORATOR);
        this.enumType = requireNonNull(enumType);
    }

    public EnumPropertyItem(Class<E> enumType, Property<E> property, String name, String description, Consumer<PropertyEditor<?>> decorator) {
        super(property, name, description, decorator);
        this.enumType = requireNonNull(enumType);
    }

    @Override
    public Class<E> getType() {
        return enumType;
    }
    
    @Override
    protected PropertyEditor<?> createUndecoratedEditor() {
        return Editors.createChoiceEditor(this, Arrays.asList(enumType.getEnumConstants()));
    }
}
