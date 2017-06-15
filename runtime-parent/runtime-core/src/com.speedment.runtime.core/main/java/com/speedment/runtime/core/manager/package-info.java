/**
 * The {@link com.speedment.runtime.core.manager.Manager} interface and its related classes
 * are located in this package. 
 * <p>
 * It is the manager that is responsible for managing entities for a particular 
 * table in the database. Each entity then has methods to persist and update it, 
 * but those methods are only delegates for the manager equivalents. Managers 
 * are stored in the {@link com.speedment.runtime.core.component.ManagerComponent}.
 * <p>
 * This package is part of the API. Modifications to classes here should only
 * (if ever) be done in major releases.
 */
package com.speedment.runtime.core.manager;