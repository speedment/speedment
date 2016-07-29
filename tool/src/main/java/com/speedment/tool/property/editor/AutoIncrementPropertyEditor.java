package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.property.item.SimpleCheckBoxItem;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;

/**
 *
 * @author Simon Jonassons
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class AutoIncrementPropertyEditor<T extends ColumnProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(new SimpleCheckBoxItem(
                "Is Auto Incrementing",
                document.autoIncrementProperty(),
                "If this column will increment automatically for each new entity.")
        );
    }
}
