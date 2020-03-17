/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.config.trait;


import com.speedment.runtime.config.Document;

import static com.speedment.runtime.config.trait.HasNameUtil.NAME;

/**
 * Trait for {@link Document} implementations that has named children and 
 * therefore implements the {@code defaultNameFor(Document)} method.
 * 
 * @author  Per Minborg
 * @since   2.3.0
 */

public interface HasChildren extends Document {

    /**
     * Generates a new default name for the specified child by appending an 
     * unique number to the {@link HasMainInterface#mainInterface()} of that
     * document.
     * <p>
     * This method will only suggest a new name for the specified child, not set
     * it.
     * 
     * @param <C>            the child type
     * @param childDocument  the child to name
     * @return               the suggested name for that child
     */
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