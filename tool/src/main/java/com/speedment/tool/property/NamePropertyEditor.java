package com.speedment.tool.property;

import com.speedment.runtime.config.trait.HasName;
import com.speedment.tool.config.trait.HasNameProperty;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Simon
 * @param <T>
 */
public class NamePropertyEditor<T extends HasNameProperty> implements PropertyEditor<T>{

    @Override
    public Stream<PropertySheet.Item> fieldsFor(T document) {
        return Stream.of(
            new StringPropertyItem(
                document.nameProperty(), 
                document.mainInterface().getSimpleName() + " Name", 
                "The name of the persisted entity in the database. This should only be modified if the database has been changed!"
            )
        );
    }

    @Override
    public String getPropertyKey() {
        return HasName.NAME;
    }
    
    
}
