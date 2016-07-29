package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.property.item.SimpleTextFieldItem;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class UsernamePropertyEditor<T extends DbmsProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(new SimpleTextFieldItem(
                "Username", 
                document.usernameProperty(), 
                "The username to use when connecting to the database."
            )
        );
    }
}
