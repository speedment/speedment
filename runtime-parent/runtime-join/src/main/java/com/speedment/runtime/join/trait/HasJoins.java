package com.speedment.runtime.join.trait;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;

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
     * Adds the provided {@code joinedTable} to the collection of joined tables.
     * Elements are joined from the provided {@code joinedTable} using an
     * <em>INNER JOIN</em> whereby rows from two tables are present only if
     * there is a match between the joining columns. Thus, records that do not
     * have matches in the joining columns will not be present in the result.
     *
     * @param <ENTITY> entity type for the added table
     * @param joinedTable to add to the current join builder
     * @return a builder where the provided {@code joinedTable} is added
     *
     * @throws NullPointerException if the provided {@code joinedTable} is
     * {@code null}
     */
    <ENTITY> R innerJoin(TableIdentifier<ENTITY> joinedTable);

    /**
     * Adds the provided {@code joinedTable} to the collection of joined tables.
     * Elements are joined from the provided {@code joinedTable} using a
     * <em>LEFT JOIN</em> whereby rows from two tables are present either if
     * there is a match between the joining columns or for each row from
     * previously existing table(s).
     * <p>
     * Unmatched rows will have entities from this manager set to {@code null}
     *
     * @param <ENTITY> entity type for the added table
     * @param joinedTable to add to the current join builder
     * @return a builder where the provided {@code joinedTable} is added
     *
     * @throws NullPointerException if the provided {@code joinedTable} is
     * {@code null}
     */
    <ENTITY> R leftJoin(TableIdentifier<ENTITY> joinedTable);

    /**
     * Adds the provided {@code joinedTable} to the collection of joined tables.
     * Elements are joined from the provided {@code joinedTable} using a
     * <em>RIGHT JOIN</em> whereby rows from two tables are present either if
     * there is a match between the joining columns or for each row from the
     * provided {@code joinedTable}.
     * <p>
     * Unmatched rows will have entities from previously existing table(s) are
     * set to {@code null}
     *
     * @param <ENTITY> entity type for the added table
     * @param joinedTable to add to the current join builder
     * @return a builder where the provided {@code joinedTable} is added
     *
     * @throws NullPointerException if the provided {@code joinedTable} is
     * {@code null}
     */
    <ENTITY> R rightJoin(TableIdentifier<ENTITY> joinedTable);

    /**
     * Adds the provided {@code joinedTable} to the collection of joined tables.
     * Elements are joined from the provided {@code joinedTable} using a
     * <em>FULL OUTER JOIN</em> whereby rows from two tables are present either
     * if there is a match between the joining columns or for each row from the
     * provided {@code joinedTable} or for each row from previously existing
     * table(s).
     * <p>
     * Unmatched rows will have entities from relevant tables(s) are set to
     * {@code null}
     *
     * @param <ENTITY> entity type for the added table
     * @param joinedTable to add to the current join builder
     * @return a builder where the provided {@code joinedTable} is added
     *
     * @throws NullPointerException if the provided {@code joinedTable} is
     * {@code null}
     */
    <ENTITY> R fullOuterJoin(TableIdentifier<ENTITY> joinedTable);

    /**
     * Adds the provided {@code joinedTable} to the collection of joined tables.
     * Elements are joined from the provided {@code joinedTable} using a
     * <em>CARTESIAN JOIN</em> whereby all combination of rows using all
     * tables(s) are produced.
     *
     * @param <ENTITY> entity type for the added table
     * @param joinedTable to add to the current join builder
     * @return a builder where the provided {@code joinedTable} is added
     *
     * @throws NullPointerException if the provided {@code joinedTable} is
     * {@code null}
     */
    <ENTITY> RC crossJoin(TableIdentifier<ENTITY> joinedTable);

//
// Future joins
//    
//    <ENTITY> R leftSemiJoin(Manager<? extends ENTITY> joinedManager);
//
//    <ENTITY> R rightSemiJoin(Manager<? extends ENTITY> joinedManager);
//
//    <ENTITY> R crossJoin(Manager<? extends ENTITY> joinedManager);
//
//    <ENTITY> R naturalJoin(Manager<? extends ENTITY> joinedManager);
//
//    <ENTITY> R naturalLeftJoin(Manager<? extends ENTITY> joinedManager);
//
//    <ENTITY> R naturalRigthJoin(Manager<? extends ENTITY> joinedManager);
}
