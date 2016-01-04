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
package com.speedment.internal.ui.property;

import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @param <T> the type of the value
 * @param <PROPERTY> the type of the property implementation
 */
public abstract class AbstractPropertyItem<T, PROPERTY extends ObservableValue<T> & WritableValue<T>> implements PropertySheet.Item {
    
    protected final static Consumer<PropertyEditor<?>> DEFAULT_DECORATOR = editor -> {};
    
    private final PROPERTY property;
    private final String name;
    private final String description;
    private final Consumer<PropertyEditor<?>> decorator;
    
    protected AbstractPropertyItem(PROPERTY property, String name, String description, Consumer<PropertyEditor<?>> decorator) {
        this.property    = requireNonNull(property);
        this.name        = requireNonNull(name);
        this.description = requireNonNull(description);
        this.decorator   = DEFAULT_DECORATOR;
    }
    
    protected abstract PropertyEditor<?> createUndecoratedEditor();
    
    public final PropertyEditor createEditor() {
        final PropertyEditor<?> editor = createUndecoratedEditor();
        decorator.accept(editor);
        return editor;
    }

    @Override
    public final String getCategory() {
        return "Node";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public T getValue() {
        return property.getValue();
    }

    @Override
    public void setValue(Object obj) {
        @SuppressWarnings("unchecked")
        final T typed = (T) obj;
        property.setValue(typed);
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.of(property);
    }
}