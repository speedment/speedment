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

package com.speedment.runtime.core.component.sql;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DatabaseCommentStyle;

/**
 * @author Mislav Milicevic
 * @since 3.2.11
 */
@InjectKey(SqlTraceComponent.class)
public interface SqlTraceComponent {

    /**
     * Returns a comments that should be attached to SQL statements.
     *
     * @return comment to attach to SQL statements
     */
    String getComment();

    /**
     * Returns a {@link DatabaseCommentStyle} that should be used to render the comment.
     *
     * @return comment style to use when attaching the comment.
     */
    default DatabaseCommentStyle getCommentStyle() {
         return DatabaseCommentStyle.SQL;
    }
}
