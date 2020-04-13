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
import java.util.Optional;

/**
 * Trait for {@link Document} implementations that is not the root. More 
 * concretely this means that they implement the {@link #getParent()} method.
 * 
 * @param <PARENT>  the type of the parent document
 * @author   Emil Forslund
 * @version  2.3.0
 */
public interface HasParent<PARENT extends Document> extends Document {

    /**
     * Returns the parent of this document. That is the document that is located
     * exactly one step above this document in the tree. If this is a root node,
     * an empty {@code Optional} is returned.
     * 
     * @return  the parent or {@code empty} if this is the root
     * @see #getParentOrThrow()
     */
    @Override
    Optional<PARENT> getParent();

    /**
     * Returns the parent of this document or throws an exception if this was
     * the root. This method can be used if you know beforehand that this
     * document is never the root.
     * 
     * @return                        the parent document
     * @throws IllegalStateException  if this was the root
     * @see #getParent()
     */
    default PARENT getParentOrThrow() {
        return getParent().orElseThrow(
            () -> new IllegalStateException(
                "Unable to get parent for " + toString()
            )
        );
    }
}