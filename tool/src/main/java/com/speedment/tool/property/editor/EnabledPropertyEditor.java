package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.property.item.SimpleCheckBoxItem;
import com.speedment.tool.config.trait.HasEnabledProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class EnabledPropertyEditor<T extends HasEnabledProperty> implements PropertyEditor<T>{

    @Override
    public Stream<PropertyEditor.Item> fieldsFor(T document) {
        return Stream.of(new SimpleCheckBoxItem(
                "Enabled", 
                document.enabledProperty(),
                "True if this node should be included in the code generation."
            )
        );
    }
}
