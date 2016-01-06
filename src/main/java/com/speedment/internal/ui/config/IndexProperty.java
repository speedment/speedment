package com.speedment.internal.ui.config;

import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;
import javafx.beans.property.BooleanProperty;

/**
 *
 * @author Emil Forslund
 */
public final class IndexProperty extends AbstractChildDocumentProperty<Table> 
    implements Index, HasEnabledProperty, HasNameProperty {

    public IndexProperty(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    public final BooleanProperty uniqueProperty() {
        return booleanPropertyOf(UNIQUE);
    }

    @Override
    public IndexColumn newIndexColumn(Map<String, Object> data) {
        return new IndexColumnProperty(this, data);
    }
}