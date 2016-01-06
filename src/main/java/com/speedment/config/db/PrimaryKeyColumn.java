package com.speedment.config.db;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasColumn;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasOrdinalPosition;
import com.speedment.config.db.trait.HasParent;

/**
 *
 * @author Emil Forslund
 */
public interface PrimaryKeyColumn extends Document, HasParent<Table>, HasName, 
    HasEnabled, HasOrdinalPosition, HasColumn {}