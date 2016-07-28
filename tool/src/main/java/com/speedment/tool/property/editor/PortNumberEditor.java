/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.editor;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.DefaultIntegerItem;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;

/**
 *
 * @author Simon
 */
public class PortNumberEditor<T extends DbmsProperty> implements PropertyEditor<T>{

    private @Inject DbmsHandlerComponent dbmsHandler;
    
    @Override
    public Stream<Item> fieldsFor(T document) {                    
        return Stream.of(
            new DefaultIntegerItem(
                "Port", 
                defaultPortProperty(document, dbmsHandler), 
                document.portProperty(), 
                "The port of the database on the database host.", 
                0, 65536)
        );
    }

    private IntegerBinding defaultPortProperty(T document, DbmsHandlerComponent dbmsHandlerComponent) {
        return Bindings.createIntegerBinding(() -> 
            DocumentDbUtil.findDbmsType(dbmsHandlerComponent, document).getDefaultPort(),
            document.typeNameProperty()
        );
    }    
}