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
package com.speedment.core.core.manager.metaresult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
public class SqlMetaResult<ENTITY> extends AbstractMetaResult<ENTITY, SqlMetaResult<ENTITY>> {

    private String query;
    private List<Object> parameters;

    public SqlMetaResult() {
        parameters = new ArrayList<>();
    }

    public String getQuery() {
        return query;
    }

    public SqlMetaResult<ENTITY> setQuery(String query) {
        this.query = query;
        return this;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public SqlMetaResult<ENTITY> setParameters(List<Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public Optional<SqlMetaResult<ENTITY>> getSqlMetaResult() {
        return Optional.of(this);
    }

    @Override
    public String toString() {
        return "SqlMetaResult (query=\"" + query + "\", parameters=" + parameters.toString() + ", throwable=" + getThrowable() + ")";
    }

}
