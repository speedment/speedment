package com.speedment.tool.property.editor;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.annotation.Api;
import com.speedment.tool.config.trait.HasPackageNameProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.DefaultTextFieldItem;
import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class PackageNameEditor<T extends HasPackageNameProperty> implements PropertyEditor<T>{

    private @Inject Injector injector;
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of( 
            new DefaultTextFieldItem(
                "Package Name",
                document.defaultPackageNameProperty(injector),
                document.packageNameProperty(),
                document.useDefaultPackageNameProperty(),
                "The package where generated classes will be located."
            )
        );
    }    
}
