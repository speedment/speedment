package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import java.util.Optional;

/**
 *
 * @author          Emil Forslund
 * @param <PARENT>  the type of the parent
 */
@Api(version = "2.3")
public interface HasParent<PARENT extends Document> extends Document {
    @Override
    Optional<PARENT> getParent();
}