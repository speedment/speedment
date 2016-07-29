package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.property.item.DefaultTextFieldItem;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
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