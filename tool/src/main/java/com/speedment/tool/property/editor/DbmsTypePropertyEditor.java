package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.ChoiceBoxItem;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.config.parameter.DbmsType;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.property.PropertyEditor;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import static javafx.collections.FXCollections.observableList;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class DbmsTypePropertyEditor<T extends DbmsProperty> implements PropertyEditor<T>{

    private @Inject DbmsHandlerComponent dbmsHandler;
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        final ObservableList<String> supportedTypes = observableList(
            dbmsHandler
                .supportedDbmsTypes()
                .map(DbmsType::getName)
                .collect(toList())
        );
        
        return Stream.of(new ChoiceBoxItem<String>(
                "Dbms Type",
                document.typeNameProperty(),
                supportedTypes,
                "Which type of database this is. If the type you are looking " +
                "for is missing, make sure it has been loaded properly in "    +
                "the pom.xml-file."
            )
        );
    }
}
