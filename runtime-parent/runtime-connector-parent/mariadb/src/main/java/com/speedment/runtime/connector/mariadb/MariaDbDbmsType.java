package com.speedment.runtime.connector.mariadb;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsType;

@InjectKey(MariaDbDbmsType.class)
public interface MariaDbDbmsType extends DbmsType {}