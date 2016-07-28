/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.DefaultTextAreaItem;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class ConnectionUrlPropertyEditor<T extends DbmsProperty> implements PropertyEditor<T> {

    private @Inject Injector injector;
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        final DbmsHandlerComponent dbmsHandler = injector.getOrThrow(DbmsHandlerComponent.class);
        
        return Stream.of(
            new DefaultTextAreaItem(
                "Connection URL", 
                defaultConnectionUrlProperty(document, dbmsHandler), 
                document.connectionUrlProperty(), 
                "The connection URL that should be used when establishing " +
                "connection with the database. If this is set to Auto, the " +
                "DbmsType will generate one."
            )
        );
    }
    
    protected StringBinding defaultConnectionUrlProperty(T document, DbmsHandlerComponent dbmsHandlerComponent) throws SpeedmentException {
        return Bindings.createStringBinding(() -> 
            DocumentDbUtil.findDbmsType(dbmsHandlerComponent, document).getConnectionUrlGenerator().from(document), 
                document.typeNameProperty(),
                document.ipAddressProperty(),
                document.portProperty(),
                document.usernameProperty()
        );
    }
}