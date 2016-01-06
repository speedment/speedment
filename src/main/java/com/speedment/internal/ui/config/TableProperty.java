package com.speedment.internal.ui.config;

import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;

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
    public Column newColumn(Map<String, Object> data) {
        return new ColumnProperty(this, data);
    }

    @Override
    public Index newIndex(Map<String, Object> data) {
        return new IndexProperty(this, data);
    }

    @Override
    public ForeignKey newForeignKey(Map<String, Object> data) {
        return new ForeignKeyProperty(this, data);
    }

    @Override
    public PrimaryKeyColumn newPrimaryKeyColumn(Map<String, Object> data) {
        return new PrimaryKeyColumnProperty(this, data);
    }
}