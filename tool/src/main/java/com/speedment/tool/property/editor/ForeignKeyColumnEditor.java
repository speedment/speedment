package com.speedment.tool.property.editor;

import com.speedment.tool.config.ForeignKeyColumnProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.SimpleTextFieldItem;
import java.util.stream.Stream;

/**
 *
 * @author Simon
 * @param <T>  the document type
 */
public class ForeignKeyColumnEditor<T extends ForeignKeyColumnProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        //TODO> Split editor into multiple editors
        return Stream.of(new SimpleTextFieldItem(
                 "Foreign Database Name",
                 document.foreignDatabaseNameProperty(), 
                 "The name of the database that this foreign key references."
            ),
            new SimpleTextFieldItem(
                "Foreign Schema Name",
                document.foreignSchemaNameProperty(), 
                "The name of the schema that this foreign key references."
            ),
            new SimpleTextFieldItem(
                "Foreign Table Name",
                document.foreignTableNameProperty(), 
                "The name of the database table that this foreign key references."
            ),
            new SimpleTextFieldItem(
                "Foreign Column Name",
                document.foreignColumnNameProperty(), 
                "The name of the database column that this foreign key references."
            )
        );
    }
}
