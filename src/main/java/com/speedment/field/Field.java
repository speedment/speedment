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
package com.speedment.field;

import com.speedment.annotation.Api;

/**
 * A Field is the most basic representation of an Entity field. Because Field
 * has a generic type of its Entity, we can prevent applications from applying a
 * field from another Entity type.
 *
 * @author          pemi, Emil Forslund
 * @param <ENTITY>  The entity type
 */
@Api(version = "2.1")
public interface Field<ENTITY> {

    /**
     * Returns the column name that corresponds to this Field.
     *
     * @return the name
     */
    String getColumnName();

}