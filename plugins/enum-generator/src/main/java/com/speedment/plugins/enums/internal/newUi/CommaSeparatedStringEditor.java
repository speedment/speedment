package com.speedment.plugins.enums.internal.newUi;

import com.speedment.plugins.enums.StringToEnumTypeMapper;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.property.PropertyEditor;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;

/**
 *
 * @author Simon
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
