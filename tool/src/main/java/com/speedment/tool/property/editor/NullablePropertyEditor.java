package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.property.item.SimpleCheckBoxItem;
import com.speedment.tool.config.trait.HasNullableProperty;
import com.speedment.tool.property.PropertyEditor;
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
        return Stream.of(new SimpleCheckBoxItem(
                "Is nullable", 
                document.nullableProperty(),
                "If this node can hold 'null'-values or not."
            )
        );
    }
}
