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
package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.ChoiceBoxItem;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.mapstream.MapStream;
import com.speedment.generator.component.TypeMapperComponent;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class TypeMapperPropertyEditor<T extends ColumnProperty> implements PropertyEditor<T>{
    public final static String IDENTITY_MAPPER = "(Use Identity Mapper)";
    
    private @Inject TypeMapperComponent typeMappers;
    private final StringProperty outputValue;
    private StringBinding binding;
    
    public TypeMapperPropertyEditor(){
        this.outputValue = new SimpleStringProperty();
    }
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        final String currentValue = 
            document.getTypeMapper().isPresent() 
            ? document.getTypeMapper().get() 
            : null;
        final Class<?> type = document.findDatabaseType();   
        
        final Map<String, String> mapping = MapStream.fromStream(
            typeMappers.mapFrom(type),
            tm -> tm.getLabel(),
            tm -> tm.getClass().getName()
        ).toSortedMap();
        
        final ObservableList<String> alternatives = FXCollections.observableList(
            mapping.keySet().stream().collect(toList())
        );
        alternatives.add(0, IDENTITY_MAPPER);
        
        //Create a binding that will convert the ChoiceBox's value to a valid
        //value for the document.typeMapperProperty()
        binding = Bindings.createStringBinding(() -> {
            if( outputValue.isEmpty().get() || outputValue.get().equals(IDENTITY_MAPPER) ){
                return null;
            } else {
                return mapping.get( outputValue.get() );
            }
        }, outputValue);
        document.typeMapperProperty().bind( binding );
        
        outputValue.set( 
            currentValue != null
            ? MapStream.of(mapping)
                .filterValue(currentValue::equals)
                .keys()
                .findAny()
                .orElseThrow(() -> new SpeedmentException(
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
