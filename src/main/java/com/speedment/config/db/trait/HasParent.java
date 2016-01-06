package com.speedment.config.db.trait;

import com.speedment.config.Document;
import java.util.Optional;

/**
 *
 * @author          Emil Forslund
 * @param <PARENT>  the type of the parent
 */
public interface HasParent<PARENT extends Document> extends Document {
    @Override
    Optional<PARENT> getParent();
}