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
package com.speedment.db;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <R> return type
 */
public interface SqlMetaResult<ENTITY, R extends SqlMetaResult<ENTITY, R>> extends MetaResult<ENTITY> {

    String getQuery();

    R setQuery(String query);

    List<Object> getParameters();

    R setParameters(List<Object> parameters);

    Optional<Throwable> getThrowable();

    R setThrowable(Throwable throwable);

    @Override
    default Optional<R> getSqlMetaResult() {
        @SuppressWarnings("unchecked")
        final R self = (R) this;
        return Optional.of(self);
    }
}
