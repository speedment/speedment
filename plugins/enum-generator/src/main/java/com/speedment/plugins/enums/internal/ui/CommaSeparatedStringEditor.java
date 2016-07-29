package com.speedment.plugins.enums.internal.ui;

import com.speedment.plugins.enums.StringToEnumTypeMapper;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;

/**
 * Editor for generating a comma-separated string.
 * <p>
 * We parse what values an enum should be able to take from a string, where
 * each element is separated by a comma. This editor allows the user
 * to easily edit such a string.
 * 
 * @author Simon Jonasson
 * @since 1.0.0
 */
public class CommaSeparatedStringEditor<T extends ColumnProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        return Stream.of(
            new AddRemoveStringItem(
                "Enum Constants", 
                document.enumConstantsProperty(), 
                document.getEnumConstants().orElse(null),
                "Used for defining what value the enum can take",
                Bindings.equal(document.typeMapperProperty(), StringToEnumTypeMapper.class.getName())
            )
        );
    }
}
