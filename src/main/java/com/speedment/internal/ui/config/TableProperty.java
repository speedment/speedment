package com.speedment.internal.ui.config;

import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
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
    public BiFunction<Table, Map<String, Object>, ColumnProperty> columnConstructor() {
        return ColumnProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, IndexProperty> indexConstructor() {
        return IndexProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ForeignKeyProperty> foreignKeyConstructor() {
        return ForeignKeyProperty::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, PrimaryKeyColumnProperty> primaryKeyColumnConstructor() {
        return PrimaryKeyColumnProperty::new;
    }

    @Override
    protected final DocumentProperty createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case COLUMNS             : return new ColumnProperty(this, data);
            case INDEXES             : return new IndexProperty(this, data);
            case FOREIGN_KEYS        : return new ForeignKeyProperty(this, data);
            case PRIMARY_KEY_COLUMNS : return new PrimaryKeyColumnProperty(this, data);
            default                  : return super.createDocument(key, data);
        }
    }
    
    @Override
    public Stream<? extends ColumnProperty> columns() {
        return (Stream<ColumnProperty>) Table.super.columns();
    }

    @Override
    public Stream<? extends IndexProperty> indexes() {
        return (Stream<IndexProperty>) Table.super.indexes();
    }

    @Override
    public Stream<? extends ForeignKeyProperty> foreignKeys() {
        return (Stream<ForeignKeyProperty>) Table.super.foreignKeys();
    }

    @Override
    public Stream<? extends PrimaryKeyColumnProperty> primaryKeyColumns() {
        return (Stream<PrimaryKeyColumnProperty>) Table.super.primaryKeyColumns();
    }
    
    public ObservableList<ColumnProperty> columnsProperty() {
        return observableListOf(COLUMNS, ColumnProperty.class);
    }
    
    public ObservableList<IndexProperty> indexesProperty() {
        return observableListOf(INDEXES, IndexProperty.class);
    }
    
    public ObservableList<ForeignKeyProperty> foreignKeysProperty() {
        return observableListOf(FOREIGN_KEYS, ForeignKeyProperty.class);
    }
    
    public ObservableList<PrimaryKeyColumnProperty> primaryKeyColumnsProperty() {
        return observableListOf(PRIMARY_KEY_COLUMNS, PrimaryKeyColumnProperty.class);
    }

    @Override
    public ColumnProperty addNewColumn() {
        return (ColumnProperty) Table.super.addNewColumn();
    }

    @Override
    public IndexProperty addNewIndex() {
        return (IndexProperty) Table.super.addNewIndex();
    }

    @Override
    public ForeignKeyProperty addNewForeignKey() {
        return (ForeignKeyProperty) Table.super.addNewForeignKey();
    }

    @Override
    public PrimaryKeyColumnProperty addNewPrimaryKeyColumn() {
        return (PrimaryKeyColumnProperty) Table.super.addNewPrimaryKeyColumn();
    }
}