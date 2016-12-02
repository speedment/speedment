package com.speedment.runtime.core.db;

import com.speedment.runtime.config.Column;

import java.util.function.Predicate;


/**
 * A DbmsColumnHandler governs selection of columns used in queries to the database
 *
 * @author  Dan Lawesson
 * @since   3.0.2
 */
public interface DbmsColumnHandler {

    /**
     * By default, all columns are included in SQL INSERT statements, this predicate
     * allows exclusion from that rule in order to for example exclude auto incremented
     * fields.
     *
     * @return a predicate used to determine if a column shall be exluded in generated SQL INSERT statements
     */
    Predicate<Column> excludedInInsertStatement();
}
