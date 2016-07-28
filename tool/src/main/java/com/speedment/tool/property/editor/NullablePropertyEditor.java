/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.SimpleBooleanItem;
import com.speedment.runtime.config.trait.HasNullable;
import com.speedment.tool.config.trait.HasNullableProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class NullablePropertyEditor<T extends HasNullableProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of( 
            new SimpleBooleanItem(
                "Is nullable", 
                document.nullableProperty(),
                "If this node can hold 'null'-values or not."
            )
        );
    }
}
