package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasColumn;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasOrdinalPosition;
import com.speedment.config.db.trait.HasParent;

/**
 *
 * @author Per Minborg
 */
public interface PrimaryKeyColumnMutator extends Document, HasParent<TableMutator>, HasName, 
    HasEnabled, HasOrdinalPosition, HasColumn, HasMainInterface {

    @Override
    default Class<PrimaryKeyColumnMutator> mainInterface() {
        return PrimaryKeyColumnMutator.class;
    }    

}