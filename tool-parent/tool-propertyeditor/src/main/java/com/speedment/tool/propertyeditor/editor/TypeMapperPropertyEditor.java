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
package com.speedment.tool.propertyeditor.editor;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.tool.config.trait.HasTypeMapperProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.item.ChoiceBoxItem;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @param <T>  the document type
 * 
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public class TypeMapperPropertyEditor<T extends HasTypeMapperProperty> implements PropertyEditor<T> {

    private static final String IDENTITY_MAPPER = "(Use Identity Mapper)";

    private final StringProperty outputValue;
    
    public @Inject TypeMapperComponent typeMappers;

    public TypeMapperPropertyEditor() {
        this.outputValue = new SimpleStringProperty();
    }
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        final String currentValue = document.getTypeMapper().orElse(null);
        final Class<?> type = document.findDatabaseType();
        
        final Map<String, String> mapping = MapStream.fromStream(
            typeMappers.mapFrom(type).filter(TypeMapper::isToolApplicable),
            TypeMapper::getLabel,
            tm -> tm.getClass().getName()
        ).toSortedMap();
        
        final ObservableList<String> alternatives = FXCollections.observableList(
            new ArrayList<>(mapping.keySet())
        );
        alternatives.add(0, IDENTITY_MAPPER);
        
        //Create a binding that will convert the ChoiceBox's value to a valid
        //value for the document.typeMapperProperty()
        StringBinding binding = Bindings.createStringBinding(() -> {
            if (outputValue.isEmpty().get() || outputValue.get().equals(IDENTITY_MAPPER)) {
                return null;
            } else {
                return mapping.get(outputValue.get());
            }
        }, outputValue);
        document.typeMapperProperty().bind(binding);
        
        outputValue.set( 
            currentValue != null
            ? MapStream.of(mapping)
                .filterValue(currentValue::equals)
                .keys()
                .findAny()
                .orElseThrow(() -> new RuntimeException(
                    "Could not find requested value '" + currentValue + "' in mapping."
                )) 
            : null );
        
        return Stream.of(new ChoiceBoxItem<String>(
                "JDBC Type to Java",
                outputValue,
                alternatives,
                "The class that will be used to map types between the database and the generated code."
            )
        );
    }     
}
