package com.speedment.config.db.trait;

import com.speedment.config.Document;
import static com.speedment.stream.MapStream.comparing;
import java.util.Comparator;

/**
 *
 * @author Emil Forslund
 */
public interface HasOrdinalPosition extends Document {
    
    final int ORDINAL_FIRST = 1, UNSET = -1;
    
    final String ORDINAL_POSITION = "ordinalPosition";
    
    final Comparator<HasOrdinalPosition> COMPARATOR = 
        comparing(HasOrdinalPosition::getOrdinalPosition);
    
    /**
     * Returns the position to use when ordering this node.
     * 
     * @return the ordinal position.
     */
    default int getOrdinalPosition() {
        return getAsInt(ORDINAL_POSITION).orElse(0);
    }
}