package com.speedment.tool.property.editor;

import static com.speedment.runtime.config.Project.DEFAULT_PACKAGE_LOCATION;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.DefaultTextFieldItem;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class PackageLocationPropertyEditor <T extends ProjectProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(
            new DefaultTextFieldItem(
                "Package Location",
                new SimpleStringProperty(DEFAULT_PACKAGE_LOCATION),
                document.packageLocationProperty(),
                "The folder to store all generated files in. This should be a relative name from the working directory."
            )
        );
    }
    
}
