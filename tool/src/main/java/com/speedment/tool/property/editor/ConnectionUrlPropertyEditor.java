package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.DefaultTextAreaItem;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.annotation.Api;
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
 * @author Simon Jonassons
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
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