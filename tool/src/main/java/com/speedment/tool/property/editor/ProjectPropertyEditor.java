package com.speedment.tool.property.editor;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import static com.speedment.runtime.config.Project.DEFAULT_PACKAGE_LOCATION;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.DefaultTextFieldItem;
import com.speedment.tool.property.item.SimpleStringItem;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class ProjectPropertyEditor<T extends ProjectProperty> implements PropertyEditor<T>{
    //TODO: Split into different editor classses
    private @Inject Injector injector;
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(new SimpleStringItem(
                "Company Name",
                document.companyNameProperty(),
                "The company name that should be used for this project. It is used in the generated code."
            ),
            new DefaultTextFieldItem(
                "Package Location",
                new SimpleStringProperty(DEFAULT_PACKAGE_LOCATION),
                document.packageLocationProperty(),
                "The folder to store all generated files in. This should be a relative name from the working directory."
            )
        );
    }
    
}
