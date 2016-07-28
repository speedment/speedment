/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.SimpleBooleanItem;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.tool.config.trait.HasEnabledProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class EnabledPropertyEditor<T extends HasEnabledProperty> implements PropertyEditor<T>{

    @Override
    public Stream<PropertyEditor.Item> fieldsFor(T document) {
        return Stream.of(
            new SimpleBooleanItem(
                "Enabled", 
                document.enabledProperty(),
                "True if this node should be included in the code generation."
            )
        );
    }
}
