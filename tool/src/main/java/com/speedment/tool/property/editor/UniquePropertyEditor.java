/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.config.IndexProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.SimpleCheckBoxItem;
import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class UniquePropertyEditor<T extends IndexProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(new SimpleCheckBoxItem(
                "Is Unique", 
                document.uniqueProperty(), 
                "True if elements in this index are unique."
            )
        );
    }
    
}
