package com.speedment.config.db.trait;

import com.speedment.config.Document;
import static com.speedment.config.db.trait.HasName.NAME;

/**
 *
 * @author Per Minborg
 */
public interface HasChildren extends Document {

    default <C extends Document & HasName> String defaultNameFor(Class<C> childClass) {
        int counter = 1;
        String nameCandidate;
        do {
            nameCandidate = childClass.getSimpleName() + counter++;
        } while (
            children()
                .map(child -> child.getAsString(NAME))
                .anyMatch(nameCandidate::equals)
        );
        
        return nameCandidate;
    }
}