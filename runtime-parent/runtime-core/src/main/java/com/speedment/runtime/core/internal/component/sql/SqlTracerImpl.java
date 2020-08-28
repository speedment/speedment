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

package com.speedment.runtime.core.internal.component.sql;

import com.speedment.runtime.core.component.sql.SqlTraceComponent;
import com.speedment.runtime.core.db.DatabaseCommentStyle;

/**
 * @author Mislav Milicevic
 * @since 3.2.11
 */
public final class SqlTracerImpl implements SqlTracer {

    private final SqlTraceComponent sqlTraceComponent;

    SqlTracerImpl(final SqlTraceComponent sqlTraceComponent) {
        this.sqlTraceComponent = sqlTraceComponent;
    }

    @Override
    public String attachTraceData(final String sql) {
        if (sqlTraceComponent == null
            || sqlTraceComponent.getComment() == null
            || sqlTraceComponent.getCommentStyle() == null) {
            return sql;
        }

        final String comment = sqlTraceComponent.getComment();
        final DatabaseCommentStyle commentStyle = sqlTraceComponent.getCommentStyle();

        final StringBuilder sb = new StringBuilder(sql);

        sb.append(" ").append(commentStyle.getPrefix()).append(" ").append(comment).append(" ");
        commentStyle.getSuffix().ifPresent(sb::append);

        return sb.toString();
    }
}
