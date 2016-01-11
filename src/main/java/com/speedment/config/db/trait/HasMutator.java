/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;

/**
 *
 * @author Emil Forslund
 * @param <T> Mutator type
 */
@Api(version = "2.3")
public interface HasMutator<T extends DocumentMutator> {

    /**
     * Returns a {@link DocumentMutator} for this Document. A DocumentMutator allows a
     * Document to be updated in a type safe way.
     *
     * @return a DocumentMutator for this Document
     * @throws UnsupportedOperationException if this Document does not support
     * mutation. For example, an immutable Document might elect to throw an
     * exception upon a call to this method.
     */
    T mutator();

}
