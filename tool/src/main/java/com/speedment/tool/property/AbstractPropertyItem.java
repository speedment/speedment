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
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @param <T>         the type of the value
 * @param <PROPERTY>  the type of the property implementation
 * @param <EDITOR>    the type of the property editor
 * 
 * @author  Emil Forslund
 * @since   2.2.0
 */
@Api(version="3.0")
public abstract class AbstractPropertyItem<T, PROPERTY extends ObservableValue<T> & WritableValue<T>, EDITOR extends PropertyEditor<?>> implements PropertySheet.Item {
    
    protected static <EDITOR extends PropertyEditor<?>> Consumer<EDITOR> defaultDecorator() { 
        return editor -> {};
    }
    
    private final PROPERTY property;
    private final String name;
    private final String description;
    private final Consumer<EDITOR> decorator;
    
    protected AbstractPropertyItem(PROPERTY property, String name, String description, Consumer<EDITOR> decorator) {
        this.property    = requireNonNull(property);
        this.name        = requireNonNull(name);
        this.description = requireNonNull(description);
        this.decorator   = requireNonNull(decorator);
    }
    
    protected abstract EDITOR createUndecoratedEditor();
    
    public final EDITOR createEditor() {
        final EDITOR editor = createUndecoratedEditor();
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