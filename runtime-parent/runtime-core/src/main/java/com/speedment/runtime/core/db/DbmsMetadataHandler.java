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

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.core.util.ProgressMeasure;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * A DbmsMetadataHandler provides the interface between Speedment and an underlying
 * {@link Dbms} for when initially loading the metadata.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.0.0
 */
public interface DbmsMetadataHandler {

    /**
     * Reads the schema metadata with populated {@link Schema Schemas} that are
     * available in this database. The schemas are populated by all their
     * sub-items such as tables, columns etc. Schemas that are a part of the
     * {@code getDbms().getType().getSchemaExcludSet()} set are excluded from
     * the model or that does not match the given filter will be excluded from
     * the {@code Stream}.
     *
     * @param dbms              the dbms to read it from
     * @param progressListener  the progress listener
     * @param filterCriteria    criteria that schema names must fulfill
     * @return                  the handle for this task
     */
    CompletableFuture<Project> readSchemaMetadata(
        Dbms dbms,
        ProgressMeasure progressListener,
        Predicate<String> filterCriteria
    );
    
    /**
     * Returns a string with information on the current dbms.
     *
     * @param dbms to get information on
     * @return a string with information on the current dbms
     * @throws SQLException if an error occurs
     */
    String getDbmsInfoString(Dbms dbms) throws SQLException;
    
}