package com.speedment.runtime.connector.sqlite;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsOperationHandler;

@InjectKey(SqliteOperationHandler.class)
public interface SqliteOperationHandler extends DbmsOperationHandler {}
