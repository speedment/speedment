/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.DefaultTextFieldItem;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class IpAdressPropertyEditor<T extends DbmsProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(new DefaultTextFieldItem(
                "IP Adress", 
                new SimpleStringProperty("192.168.0.1"), 
                document.ipAddressProperty(), 
                "The ip of the database host."
            )
        );
    }
    
}