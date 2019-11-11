package com.speedment.runtime.connector.postgres;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsType;

@InjectKey(PostgresDbmsType.class)
public interface PostgresDbmsType extends DbmsType {}
