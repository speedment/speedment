/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util.document;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.internal.core.config.BaseDocument;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class TraitUtil {
    
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
    
    @FunctionalInterface
    public interface TraitViewConstructor<TRAIT> {
        TRAIT create(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface);
    }
    
    public static abstract class AbstractTraitView extends BaseDocument implements HasMainInterface {
        
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
    
    /**
     * Utility classes should not be instantiated.
     */
    private TraitUtil() {
        instanceNotAllowed(getClass());
    }
}