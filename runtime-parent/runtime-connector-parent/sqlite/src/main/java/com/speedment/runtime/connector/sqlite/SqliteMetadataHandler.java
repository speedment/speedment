package com.speedment.runtime.connector.sqlite;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsMetadataHandler;

@InjectKey(SqliteMetadataHandler.class)
public interface SqliteMetadataHandler extends DbmsMetadataHandler {
}
