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

package com.speedment.runtime.core.db;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * @author Mislav Milicevic
 * @since 3.2.11
 */
public enum DatabaseCommentStyle {

    /**
     * Default SQL comment style
     */
    SQL("--"),
    /**
     * Alternative SQL comment style, might not be supported by all DBMS types
     */
    C_STYLE("/*", "*/");

    private final String prefix;
    private final String suffix;

    DatabaseCommentStyle(final String prefix) {
        this(prefix, null);
    }

    DatabaseCommentStyle(final String prefix, final String suffix) {
        this.prefix = requireNonNull(prefix);
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public Optional<String> getSuffix() {
        return Optional.ofNullable(suffix);
    }
}
