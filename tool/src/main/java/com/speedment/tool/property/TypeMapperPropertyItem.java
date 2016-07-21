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

import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.annotation.Api;
import com.speedment.generator.component.TypeMapperComponent;
import com.speedment.runtime.exception.SpeedmentException;
import javafx.beans.property.StringProperty;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.Map;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;
import static java.util.Objects.requireNonNull;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author  Emil Forslund
 * @since   2.2.0
 */
@Api(version="3.0")
public final class TypeMapperPropertyItem extends AbstractPropertyItem<String, StringProperty, PropertyEditor<?>> {
    
    public final static String IDENTITY_MAPPER = "(Use Identity Mapper)";
    
    private final StringProperty property;
    private final Speedment speedment;
    private final Class<?> type;
    
    public TypeMapperPropertyItem(Speedment speedment, Class<?> type, StringProperty property, String name, String description) {
        this(speedment, type, property, name, description, defaultDecorator());
    }

    public TypeMapperPropertyItem(Speedment speedment, Class<?> type, StringProperty property, String name, String description, Consumer<PropertyEditor<?>> decorator) {
        super(property, name, description, decorator);
        this.speedment = requireNonNull(speedment);
        this.type      = requireNonNull(type);
        this.property  = requireNonNull(property);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    protected PropertyEditor<?> createUndecoratedEditor() {
        final TypeMapperComponent typeMapperComponent = speedment.getOrThrow(TypeMapperComponent.class);
        
        final Map<String, String> mapping = MapStream.fromStream(
            typeMapperComponent.mapFrom(type),
            tm -> tm.getLabel(),
            tm -> tm.getClass().getName()
        ).toSortedMap();
        
        final ObservableList<String> alternatives = FXCollections.observableList(
            mapping.keySet().stream().collect(toList())
        );
        
        alternatives.add(0, IDENTITY_MAPPER);
        
        final ChoiceBox<String> choice = new ChoiceBox<>(alternatives);
        choice.valueProperty().addListener((ob, o, n) -> {
            if (n == null || IDENTITY_MAPPER.equals(n)) {
                property.set(null);
            } else {
                property.set(mapping.get(n));
            }
        });
        
        final PropertyEditor<String> editor = new PropertyEditor<String>() {

            @Override
            public void setValue(String javaName) {}

            @Override
            public Node getEditor() {
                return choice;
            }

            @Override
            public String getValue() {
                return choice.getValue();
            }
        };
        
        final String initialValue = property.get();
        if (initialValue == null) {
            choice.setValue(IDENTITY_MAPPER);
        } else {
            choice.setValue(MapStream.of(mapping)
                .filterValue(initialValue::equals)
                .keys()
                .findAny()
                .orElseThrow(() -> new SpeedmentException(
                    "Could not find requested value '" + initialValue + "' in mapping."
                ))
            );
        }
        
        return editor;
    }
}