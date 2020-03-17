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
package com.speedment.runtime.core.util;

import com.speedment.runtime.core.internal.util.InternalMergeUtil;
import com.speedment.runtime.core.manager.Manager;

import java.util.Set;

public final class MergeUtil {

    private MergeUtil() {}

    /**
     * Merges the provided {@code entities} with the underlying database.
     * <p>
     * The entity must have exactly one primary key.
     *
     * @param <T> entity type
     * @param manager to use for merging
     * @param entities to merge with the database
     * @return the entities that have been merged
     * @throws UnsupportedOperationException if the entity class
     *         does not have exactly one primary key
     */
    public static <T> Set<T> merge(Manager<T> manager, Set<T> entities) {
        return InternalMergeUtil.merge(manager, entities);
    }

}
