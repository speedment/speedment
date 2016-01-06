package com.speedment.config.db.trait;

import com.speedment.config.Document;
import com.speedment.config.db.Column;
import com.speedment.config.db.Table;

/**
 *
 * @author Emil Forslund
 */
public interface HasColumn extends Document, HasName {
    
    default Column findColumn() {
        final Table table = (Table) ancestors()
            .filter(doc -> Table.class.isAssignableFrom(doc.getClass()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                "A node in the config tree that references a column is" +
                "not located inside a table node."
            ));
        
        return table.columns()
            .filter(col -> col.getName().equals(getName()))
            .findAny()
            .orElseThrow(() -> new IllegalStateException(
                "A non-existing column '" + getName() + "' was referenced."
            ));
    }
}