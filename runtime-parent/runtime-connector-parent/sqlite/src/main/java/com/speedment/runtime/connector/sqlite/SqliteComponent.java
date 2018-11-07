package com.speedment.runtime.connector.sqlite;

import com.speedment.common.injector.annotation.InjectKey;

/**
 * Component that injects the SQLite runtime to the Speedment platform.
 *
 * @author Emil Forslund
 * @since  3.1.9
 */
@InjectKey(SqliteComponent.class)
public interface SqliteComponent {}