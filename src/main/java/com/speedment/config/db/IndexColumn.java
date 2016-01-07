package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasColumn;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasOrderType;
import com.speedment.config.db.trait.HasOrdinalPosition;
import com.speedment.config.db.trait.HasParent;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface IndexColumn extends Document, HasParent<Index>, HasName, 
    HasOrdinalPosition, HasOrderType, HasColumn, HasMainInterface {
    
    @Override
    default Class<IndexColumn> mainInterface() {
        return IndexColumn.class;
    }    

}