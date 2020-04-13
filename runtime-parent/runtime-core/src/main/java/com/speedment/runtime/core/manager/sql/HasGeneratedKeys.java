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
package com.speedment.runtime.core.manager.sql;

import com.speedment.runtime.field.Field;

import java.util.List;

/**
 * Trait for {@link SqlStatement} that describes the generated keys that can be
 * set automatically when an insert is done in the database.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface HasGeneratedKeys extends SqlStatement {

    /**
     * Returns a list with the fields that are generated automatically by this
     * insert statement.
     *
     * @return  the generated fields
     */
    List<Field<?>> getGeneratedColumnFields();

    /**
     * Adds the specified key to the list of keys that has been generated.
     *
     * @param generatedKey to be added
     */
    void addGeneratedKey(Long generatedKey);

    /**
     * Notifies the generated key listener that a key has been generated.
     */
    void notifyGeneratedKeyListener();

}
