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
package com.speedment.config.aspects;

import com.speedment.config.Node;
import com.speedment.annotation.Api;

/**
 * A trait for nodes that have a predefined order. This can for an example be
 * columns or indexes.
 * 
 * @author pemi
 */
@Api(version = "2.1")
public interface Ordinable extends Node {

    final int ORDINAL_FIRST = 1, UNSET = -1;

    /**
     * Returns the position to use when ordering this node.
     * 
     * @return                 the ordinal position.
     */
    int getOrdinalPosition();

    /**
     * Sets the position to use when ordering this node.
     * 
     * @param ordinalPosition  the ordinal position.
     */
    void setOrdinalPosition(int ordinalPosition);

    /**
     * Returns <code>true</code> since nodes of this type has a specific order.
     * 
     * @return  <code>true</code> since this is an <code>Ordinable</code>.
     */
    @Override
    default boolean isOrdinable() {
        return true;
    }
}