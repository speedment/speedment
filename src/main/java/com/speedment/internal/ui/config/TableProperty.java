package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import com.speedment.config.db.Column;
import static com.speedment.config.db.Dbms.SCHEMAS;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class TableProperty extends AbstractChildDocumentProperty<Schema> 
    implements Table, HasEnabledProperty, HasNameProperty, HasAliasProperty {

    public TableProperty(Schema parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            HasNameProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties(),
            HasAliasProperty.super.getUiVisibleProperties()
        ).flatMap(s -> s);
    }

    @Override
    public BiFunction<Table, Map<String, Object>, Column> columnConstructor() {
        return ColumnProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, Index> indexConstructor() {
        return IndexProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ForeignKey> foreignKeyConstructor() {
        return ForeignKeyProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, PrimaryKeyColumn> primaryKeyColumnConstructor() {
        return PrimaryKeyColumnProperty::new;
    }
<<<<<<< Updated upstream
    
    @Override
    protected final Document createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case COLUMNS             : return new ColumnProperty(this, data);
            case INDEXES             : return new IndexProperty(this, data);
            case FOREIGN_KEYS        : return new ForeignKeyProperty(this, data);
            case PRIMARY_KEY_COLUMNS : return new PrimaryKeyColumnProperty(this, data);
            default                  : return super.createDocument(key, data);
        }
=======

    @Override
    public Column addNewColumn() {
        return columnConstructor().apply(this, newEmptyMap(COLUMNS));
    }

    @Override
    public Index addNewIndex() {
        return columnConstructor().apply(this, newEmptyMap(COLUMNS));
    }

    @Override
    public ForeignKey addNewForeignKey() {
        return columnConstructor().apply(this, newEmptyMap(COLUMNS));
    }

    @Override
    public PrimaryKeyColumn addNewPrimaryKeyColumn() {
        return columnConstructor().apply(this, newEmptyMap(COLUMNS));
>>>>>>> Stashed changes
    }
}