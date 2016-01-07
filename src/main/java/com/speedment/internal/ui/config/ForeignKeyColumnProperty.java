package com.speedment.internal.ui.config;

import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasColumnProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.config.trait.HasOrdinalPositionProperty;
import com.speedment.internal.ui.property.StringPropertyItem;
import java.util.Map;
import java.util.stream.Stream;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyColumnProperty extends AbstractChildDocumentProperty<ForeignKey> 
    implements ForeignKeyColumn, HasNameProperty, HasOrdinalPositionProperty, HasColumnProperty {

    public ForeignKeyColumnProperty(ForeignKey parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            HasColumnProperty.super.getUiVisibleProperties(),
            Stream.of(
                new StringPropertyItem(
                    foreignTableNameProperty(), 
                    "Foreign Table Name",
                    "The name of the database table that this foreign key references."
                ),
                new StringPropertyItem(
                    foreignColumnNameProperty(), 
                    "Foreign Column Name",
                    "The name of the database column that this foreign key references."
                )
            )
        ).flatMap(s -> s);
    }

    public final StringProperty foreignTableNameProperty() {
        return stringPropertyOf(FOREIGN_TABLE_NAME);
    }

    public final StringProperty foreignColumnNameProperty() {
        return stringPropertyOf(FOREIGN_COLUMN_NAME);
    }

    public final ObjectBinding<Table> foreignTableProperty() {
        return createObjectBinding(this::findForeignTable, foreignTableNameProperty());
    }

    public final ObjectBinding<Column> foreignColumnProperty() {
        return createObjectBinding(this::findForeignColumn, foreignTableNameProperty(), foreignColumnNameProperty());
    }
}