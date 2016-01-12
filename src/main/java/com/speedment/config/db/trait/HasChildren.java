package com.speedment.config.db.trait;

import com.speedment.config.Document;

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
                .filter(HasName.class::isInstance)
                .map(HasName.class::cast)
                .map(HasName::getName)
                .anyMatch(nameCandidate::equals)
        );
        
        return nameCandidate;
    }
}