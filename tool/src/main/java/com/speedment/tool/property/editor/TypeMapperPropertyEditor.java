/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.ChoiceBoxItem;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.mapstream.MapStream;
import com.speedment.generator.component.TypeMapperComponent;
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
 * @author Simon
 * @param <T>  the document type
 */
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
        final TypeMapperComponent typeMapperComponent = typeMappers;     
        
        final Map<String, String> mapping = MapStream.fromStream(
            typeMapperComponent.mapFrom(type),
            tm -> tm.getLabel(),
            tm -> tm.getClass().getName()
        ).toSortedMap();
        
        final ObservableList<String> alternatives = FXCollections.observableList(
            mapping.keySet().stream().collect(toList())
        );
        alternatives.add(0, IDENTITY_MAPPER);
        
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
        
        return Stream.of(new ChoiceBoxItem(
                "JDBC Type to Java",
                outputValue,
                alternatives,
                "The class that will be used to map types between the database and the generated code."
            )
        );
    }     
}
