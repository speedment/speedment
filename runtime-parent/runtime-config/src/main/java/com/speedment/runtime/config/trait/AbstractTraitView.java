package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.internal.BaseDocument;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * An abstract base implementation for a view of a particular trait.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
abstract class AbstractTraitView
extends BaseDocument implements HasMainInterface {
        
    private final Class<? extends Document> mainInterface;

    protected AbstractTraitView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data);
        this.mainInterface = requireNonNull(mainInterface);
    }

    @Override
    public final Class<? extends Document> mainInterface() {
        return mainInterface;
    }
}