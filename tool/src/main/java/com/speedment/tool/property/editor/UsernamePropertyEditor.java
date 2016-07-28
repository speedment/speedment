/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.SimpleStringItem;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class UsernamePropertyEditor<T extends DbmsProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(
            new SimpleStringItem(
                "Username", 
                document.usernameProperty(), 
                "The username to use when connecting to the database."
            )
        );
    }
}
