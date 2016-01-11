package com.speedment.internal.core.config;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author          Emil Forslund
 * @param <PARENT>  the parent interface
 */
public abstract class AbstractChildDocument<PARENT extends Document> extends BaseDocument implements HasParent<PARENT> {

    public AbstractChildDocument(PARENT parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Optional<PARENT> getParent() {
        return (Optional<PARENT>) super.getParent();
    }
}