package com.speedment.runtime.connector.mysql;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsType;

@InjectKey(MySqlDbmsType.class)
public interface MySqlDbmsType extends DbmsType {}
