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
package com.speedment.plugins.json.internal;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.ColumnIdentifier;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.config.util.DocumentDbUtil.referencedColumn;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class JsonUtil {

    private JsonUtil() {}

    /**
     * Determines the JSON label for the specified field identifier. This will
     * be the result of {@link Column#getJavaName()}.
     * 
     * @param project     the project that the identifier is referencing
     * @param identifier  the identifier for the field to name
     * @return            the JSON label of that field
     */
    public static String jsonField(Project project, ColumnIdentifier<?> identifier) {
        requireNonNulls(project, identifier);
        return referencedColumn(project, identifier).getJavaName();
    }
    
}