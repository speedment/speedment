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
package com.speedment.maven.typemapper;

import com.speedment.runtime.typemapper.TypeMapper;
import static java.util.Objects.requireNonNull;

/**
 * A mapping between a particular database type and a {@link TypeMapper}. This
 * class is intended to be instantiated by the maven plugin when parsing the
 * pom.xml-file.
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
public final class Mapping {

    private String databaseType;
    private String implementation;

    public String getDatabaseType() {
        return databaseType;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = requireNonNull(databaseType);
    }

    public void setImplementation(String implementation) {
        this.implementation = requireNonNull(implementation);
    }
}
