package com.speedment.tool.property.editor;

import com.speedment.tool.config.ForeignKeyColumnProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.SimpleStringItem;
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
        return Stream.of(
            new SimpleStringItem(
                 "Foreign Database Name",
                 document.foreignDatabaseNameProperty(), 
                 "The name of the database that this foreign key references."
            ),
            new SimpleStringItem(
                "Foreign Schema Name",
                document.foreignSchemaNameProperty(), 
                "The name of the schema that this foreign key references."
            ),
            new SimpleStringItem(
                "Foreign Table Name",
                document.foreignTableNameProperty(), 
                "The name of the database table that this foreign key references."
            ),
            new SimpleStringItem(
                "Foreign Column Name",
                document.foreignColumnNameProperty(), 
                "The name of the database column that this foreign key references."
            )
        );
    }
}
