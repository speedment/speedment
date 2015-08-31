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
package com.speedment.internal.core.manager.metaresult;

import com.speedment.db.SqlMetaResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public final class SqlMetaResultImpl<ENTITY>
    extends AbstractMetaResult<ENTITY, SqlMetaResultImpl<ENTITY>>
    implements SqlMetaResult<ENTITY, SqlMetaResultImpl<ENTITY>> {

    private String query;
    private List<Object> parameters;

    public SqlMetaResultImpl() {
        parameters = new ArrayList<>();
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public SqlMetaResultImpl<ENTITY> setQuery(String query) {
        this.query = requireNonNull(query);
        return this;
    }

    @Override
    public List<Object> getParameters() {
        return parameters;
    }

    @Override
    public SqlMetaResultImpl<ENTITY> setParameters(List<Object> parameters) {
        this.parameters = requireNonNull(parameters);
        return this;
    }

    @Override
    public String toString() {
        return "SqlMetaResult (query=\"" + Objects.toString(query) + "\", parameters=" + Objects.toString(parameters) + ", throwable=" + getThrowable() + ")";
    }

}
