package com.speedment.runtime.connector.sqlite;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsType;

@InjectKey(SqliteDbmsType.class)
public interface SqliteDbmsType extends DbmsType {}
