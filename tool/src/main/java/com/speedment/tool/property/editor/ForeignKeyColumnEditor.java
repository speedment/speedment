package com.speedment.tool.property.editor;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.config.ForeignKeyColumnProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.ItemUtil;
import com.speedment.tool.property.item.SimpleTextFieldItem;
import java.util.stream.Stream;

/**
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public class ForeignKeyColumnEditor<T extends ForeignKeyColumnProperty> implements PropertyEditor<T>{

    @Override
    public Stream<Item> fieldsFor(T document) {
        //TODO> Split editor into multiple editors
        return Stream.of(new SimpleTextFieldItem(
                 "Foreign Database Name",
                 document.foreignDatabaseNameProperty(), 
                 "The name of the database that this foreign key references.",
                 (editor) -> ItemUtil.lockDecorator(editor, ItemUtil.DATABASE_RELATION_TOOLTIP)
            ),
            new SimpleTextFieldItem(
                "Foreign Schema Name",
                document.foreignSchemaNameProperty(), 
                "The name of the schema that this foreign key references.",
                 (editor) -> ItemUtil.lockDecorator(editor, ItemUtil.DATABASE_RELATION_TOOLTIP)
            ),
            new SimpleTextFieldItem(
                "Foreign Table Name",
                document.foreignTableNameProperty(), 
                "The name of the database table that this foreign key references.",
                 (editor) -> ItemUtil.lockDecorator(editor, ItemUtil.DATABASE_RELATION_TOOLTIP)
            ),
            new SimpleTextFieldItem(
                "Foreign Column Name",
                document.foreignColumnNameProperty(), 
                "The name of the database column that this foreign key references.",
                 (editor) -> ItemUtil.lockDecorator(editor, ItemUtil.DATABASE_RELATION_TOOLTIP)
            )
        );
    }
}
