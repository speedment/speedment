package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.property.item.DefaultTextFieldItem;
import com.speedment.tool.config.trait.HasAliasProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;

/**
 *
 * @author Simon Jonassons
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class AliasPropertyEditor<T extends HasAliasProperty> implements PropertyEditor<T> {

    @Override
    public Stream<PropertyEditor.Item> fieldsFor(T document) {
        return Stream.of(new DefaultTextFieldItem(
            "Java Alias",
            document.nameProperty(), 
            document.aliasProperty(),
            "The name that will be used for this in generated code.")
        );
    }
}
