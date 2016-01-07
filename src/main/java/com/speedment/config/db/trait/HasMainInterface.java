package com.speedment.config.db.trait;

import com.speedment.config.Document;

/**
 *
 * @author Per Minborg
 */
public interface HasMainInterface {

    Class<? extends Document> mainInterface();
    
}
