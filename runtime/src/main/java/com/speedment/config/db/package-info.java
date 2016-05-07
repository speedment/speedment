/**
 * Interfaces for exposing a typed database-specific view of a 
 * {@link com.speedment.config.Document} tree is located in this package.
 * <p>
 * The database configuration is structured in the following way:
 * <pre>
 * {@link Project}
 * - {@link Dbms}
 * -- {@link Schema}
 * --- {@link Table}
 * ---- {@link PrimaryKeyColumn}
 * ---- {@link Column}
 * ---- {@link Index}
 * ----- {@link IndexColumn}
 * ---- {@link ForeignKey}
 * ----- {@link ForeignKeyColumn}
 * </pre>
 * <p>
 * This package is part of the API. Modifications to classes here should only
 * (if ever) be done in major releases.
 */
package com.speedment.config.db;
