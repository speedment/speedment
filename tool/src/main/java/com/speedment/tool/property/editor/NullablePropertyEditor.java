package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.config.trait.HasNullableProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.NullableItem;
import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class NullablePropertyEditor<T extends HasNullableProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(new NullableItem(
            document.nullableProperty(),
            document.nullableImplementationProperty()
        ));
    }
}
