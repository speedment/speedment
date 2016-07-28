package com.speedment.tool.property.editor;

import com.speedment.tool.property.item.SimpleTextFieldItem;
import com.speedment.tool.config.trait.HasNameProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;

/**
 *
 * @author Simon
 * @param <T>
 */
public class NamePropertyEditor<T extends HasNameProperty> implements PropertyEditor<T>{

    @Override
    public Stream<PropertyEditor.Item> fieldsFor(T document) {
        return Stream.of(new SimpleTextFieldItem(
                document.mainInterface().getSimpleName() + " Name",
                document.nameProperty(),
                "The name of the persisted entity in the database. This should only be modified if the database has been changed!"
            )
        );
    }
}
