package com.speedment.tool.property.editor;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.DefaultSpinnerItem;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class PortNumberEditor<T extends DbmsProperty> implements PropertyEditor<T>{

    private @Inject DbmsHandlerComponent dbmsHandler;
    
    @Override
    public Stream<Item> fieldsFor(T document) {                    
        return Stream.of(new DefaultSpinnerItem(
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