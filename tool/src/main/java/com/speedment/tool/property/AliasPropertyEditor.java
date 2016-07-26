/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property;

import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.tool.config.trait.HasAliasProperty;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class AliasPropertyEditor<T extends HasAliasProperty> implements PropertyEditor<T> {

    @Override
    public Stream<PropertyEditor.Item> fieldsFor(T document) {
        return Stream.empty();
//        return Stream.of(new DefaultStringPropertyItem(
//                document.aliasProperty(),
//                document.nameProperty(),
//                "Java Alias", 
//                "The name that will be used for this in generated code."
//            )
//        );
    }

    @Override
    public String getPropertyKey() {
        return HasAlias.ALIAS;
    }
    
}
