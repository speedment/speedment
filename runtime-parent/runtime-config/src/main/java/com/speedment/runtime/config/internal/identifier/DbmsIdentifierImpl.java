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
package com.speedment.runtime.config.internal.identifier;

import com.speedment.runtime.config.identifier.DbmsIdentifier;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the entity type
 */
public final class DbmsIdentifierImpl<ENTITY> implements DbmsIdentifier<ENTITY> {

    private final String dbmsName;

    public DbmsIdentifierImpl(String dbmsName) {
        this.dbmsName = requireNonNull(dbmsName);
    }

    @Override
    public String getDbmsId() {
        return dbmsName;
    }

    @Override
    public int hashCode() {
        return dbmsName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DbmsIdentifier) {
            final DbmsIdentifier<?> that = (DbmsIdentifier<?>) obj;
            return Objects.equals(dbmsName, that.getDbmsId());
        }
        return false;
    }

    @Override
    public String toString() {
        return dbmsName;
    }
}
