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
package com.speedment.db.crud;

import com.speedment.annotation.Api;

/**
 *
 * @author Emil Forslund
 * @param <OPERATION> the type of the selective operation being built
 * @param <BUILDER>   the type of the implementing builder class
 */
@Api(version = "2.2")
public interface JoinableBuilder<OPERATION extends CrudOperation & Selective, BUILDER extends SelectiveBuilder<OPERATION, BUILDER>> extends CrudOperationBuilder<OPERATION> {
    
    /**
     * Appends an additional join to this builder to determine which 
     * entities are involved in the operation.
     *
     * @param join  the join
     * @return      a reference to this builder
     */
    BUILDER join(Join join);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default boolean isJoinable() {
        return true;
    }
}