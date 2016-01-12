package com.speedment.config.db.trait;

import com.speedment.config.Document;
import static com.speedment.config.db.trait.HasName.NAME;

/**
 *
 * @author Per Minborg
 */
public interface HasChildren extends Document {

    default <C extends Document & HasName & HasMainInterface> String defaultNameFor(C childDocument) {
        int counter = 1;
        String nameCandidate;
        do {
            nameCandidate = childDocument.mainInterface().getSimpleName() + counter++;
        } while (
            children()
                .map(child -> child.getAsString(NAME))
                .anyMatch(nameCandidate::equals)
        );
        
        return nameCandidate;
    }
}