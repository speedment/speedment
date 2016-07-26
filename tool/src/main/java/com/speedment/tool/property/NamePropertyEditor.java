package com.speedment.tool.property;

import com.speedment.runtime.config.trait.HasName;
import com.speedment.tool.config.trait.HasNameProperty;
import java.util.stream.Stream;

/**
 *
 * @author Simon
 * @param <T>
 */
public class NamePropertyEditor<T extends HasNameProperty> implements PropertyEditor<T>{

    @Override
    public Stream<PropertyEditor.Item> fieldsFor(T document) {
        return Stream.of(
            new SimpleStringItem(
                document.mainInterface().getSimpleName() + " Name",
                document.nameProperty()
            )
        );
    }

    @Override
    public String getPropertyKey() {
        return HasName.NAME;
    }
    
    
}
