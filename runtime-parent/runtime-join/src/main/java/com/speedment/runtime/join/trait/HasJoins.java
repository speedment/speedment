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
package com.speedment.runtime.join.trait;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;

/**
 * This interface contains the the supported join operations of Speedment.
 *
 * @author Per Minborg
 * @param <R> Return type for all joins except cross join
 * @param <RC> Return type for cross join
 * @since 3.0.23
 */
public interface HasJoins<R, RC> {

    /**
     * Adds the provided {@code joinedField} to the collection of joined
     * column/tables. Elements are joined from the table of the provided
     * {@code joinedField} using an <em>INNER JOIN</em> whereby rows from two
     * tables are present only if there is a match between the joining columns.
     * Thus, rows that do not have matches in the joining columns will not be
     * present in the result.
     *
     * @param <ENTITY> entity type
     * @param joinedField to add to the current join builder
     * @return a builder where the provided {@code joinedField} is added
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}
     */
    <ENTITY> R innerJoinOn(HasComparableOperators<ENTITY, ?> joinedField);

    /**
     * Adds the provided {@code joinedField} to the collection of joined
     * column/tables. Elements are joined from the table of the provided
     * {@code joinedField} using an <em>LEFT JOIN</em> whereby rows from two
     * tables are present either if there is a match between the joining columns
     * or for each row from previously existing table(s).
     * <p>
     * Unmatched rows will have entities from the table in the given
     * {@code joinedField} set to {@code null}
     *
     * @param <ENTITY> entity type
     * @param joinedField to add to the current join builder
     * @return a builder where the provided {@code joinedField} is added
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}
     */
    <ENTITY> R leftJoinOn(HasComparableOperators<ENTITY, ?> joinedField);

    /**
     * Adds the provided {@code joinedField} to the collection of joined
     * column/tables. Elements are joined from the table of the provided
     * {@code joinedField} using an <em>RIGHT JOIN</em> whereby rows from two
     * tables are present either if there is a match between the joining columns
     * or for each row from the table for the provided {@code joinedField}.
     * <p>
     * Unmatched rows will have entities from the table in the given
     * {@code joinedField} set to {@code null}
     *
     * @param <ENTITY> entity type
     * @param joinedField to add to the current join builder
     * @return a builder where the provided {@code joinedField} is added
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}
     */
    <ENTITY> R rightJoinOn(HasComparableOperators<ENTITY, ?> joinedField);

    /**
     * Adds the provided {@code joinedTable} to the collection of joined tables.
     * Elements are joined from the table of the provided {@code joinedTable}
     * using an <em>CROSS JOIN</em> whereby all combination of rows using all
     * tables(s) are produced.
     *
     * @param <ENTITY> entity type
     * @param joinedTable to add to the current join builder
     * @return a builder where the provided {@code joinedField} is added
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}
     */
    <ENTITY> RC crossJoin(TableIdentifier<ENTITY> joinedTable);

    // FULL OUTER JOIN HAS BEEN POSTPONED TO A FUTURE RELEASE
    // IT IS NOT SUPPORTED BY MOST RDBMS TYPES
    // Future joins leftSemiJoin, rightSemiJoin, naturalJoin, naturalLeftJoin, naturalRightJoin

}