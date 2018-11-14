package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.internal.db.AbstractDbmsOperationHandler;

/**
 * The implementation of {@link DbmsOperationHandler} for SQLite databases.
 *
 * @author Emil Forslund
 * @since  3.1.9
 */
@InjectKey(DbmsOperationHandler.class)
public final class SqliteOperationHandler
extends AbstractDbmsOperationHandler {}