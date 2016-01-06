package com.speedment.config.db;

import com.speedment.config.Document;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasColumn;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasOrdinalPosition;
import com.speedment.config.db.trait.HasParent;
import com.speedment.exception.SpeedmentException;

/**
 *
 * @author Emil Forslund
 */
public interface ForeignKeyColumn extends Document, HasParent<ForeignKey>, HasName, HasOrdinalPosition, HasColumn {
    
    /**
     * Returns the name of the foreign column referenced by this column.
     * 
     * @return  the name of the foreign column
     */
    default String getForeignTableName() {
        return (String) get("foreignTableName").get();
    }
    
    /**
     * Returns the name of the foreign table referenced by this column.
     * 
     * @return  the name of the foreign table
     */
    default String getForeignColumnName() {
        return (String) get("foreignColumnName").get();
    }
    
    /**
     * A helper method for accessing the foreign {@link Table} referenced by
     * this key. 
     * <p>
     * If the table was not found, a {@link SpeedmentException} is thrown.
     * 
     * @return  the foreign {@link Table} referenced by this
     */
    default Table findForeignTable() throws SpeedmentException {
        final Schema schema = (Schema) ancestors()
            .filter(doc -> Schema.class.isAssignableFrom(doc.getClass()))
            .findFirst()
            .orElseThrow(() -> new SpeedmentException(
                "A foreign key in the config tree references a table that " +
                "is not located in the same schema"
            ));
        
        return schema.tables()
            .filter(tab -> tab.getName().equals(getForeignTableName()))
            .findAny()
            .orElseThrow(() -> new SpeedmentException(
                "A non-existing table '" + getForeignTableName() + "' was referenced."
            ));
    }
    
    /**
     * A helper method for accessing the foreign {@link Column} referenced by
     * this key.
     * <p>
     * If the column was not found, a {@link SpeedmentException} is thrown.
     * 
     * @return  the foreign {@link Column} referenced by this
     */
    default Column findForeignColumn() throws SpeedmentException {
        return findForeignTable()
            .columns()
            .filter(col -> col.getName().equals(getForeignColumnName()))
            .findAny()
            .orElseThrow(() -> new SpeedmentException(
                "A non-existing column '" + getForeignColumnName() + 
                "' in table '" + getForeignTableName() + "' was referenced."
            ));
    }
}