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
package com.speedment.runtime.config.util;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.trait.HasMainInterface;
import java.util.Map;

/**
 * An utility class with methods that traits can use to produce a view of the
 * document that satisfies exactly that trait.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class TraitUtil {
    
    /**
     * Returns a view of the specified document that implements the specified
     * trait. The returned document might or might not be the same instance as
     * was inputted to this method. If not, the instance will be initialized
     * using the specified constructor.
     * 
     * @param <TRAIT>      the trait class
     * @param document     the document to create a view of
     * @param trait        the trait type
     * @param constructor  constructor to use for documents that lack the trait
     * @return             the view of the trait
     */
    public static <TRAIT extends Document> TRAIT viewOf(
            Document document,
            Class<TRAIT> trait,
            TraitViewConstructor<? extends TRAIT> constructor) {
        
        if (trait.isInstance(document)) {
            return trait.cast(document);
        } else {
            
            final Class<? extends Document> mainInterface;
            
            if (document instanceof HasMainInterface) {
                mainInterface = ((HasMainInterface) document).mainInterface();
            } else {
                mainInterface = trait;
            }
            
            return constructor.create(
                document.getParent().orElse(null), 
                document.getData(),
                mainInterface
            );
        }
    }
    
    /**
     * A functional interface describing the constructor for a class that
     * implements a specific trait.
     * 
     * @param <TRAIT>  the trait to construct an implementation of
     */
    @FunctionalInterface
    public interface TraitViewConstructor<TRAIT> {
        TRAIT create(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface);
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private TraitUtil() {
        throw new UnsupportedOperationException();
    }
}