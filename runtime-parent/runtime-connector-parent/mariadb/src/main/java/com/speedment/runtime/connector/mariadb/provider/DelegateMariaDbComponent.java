package com.speedment.runtime.connector.mariadb.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.connector.mariadb.MariaDbComponent;
import com.speedment.runtime.connector.mariadb.MariaDbDbmsType;
import com.speedment.runtime.connector.mariadb.internal.MariaDbComponentImpl;
import com.speedment.runtime.connector.mysql.MySqlComponent;
import com.speedment.runtime.connector.mysql.MySqlDbmsType;
import com.speedment.runtime.connector.mysql.internal.MySqlComponentImpl;
import com.speedment.runtime.core.component.DbmsHandlerComponent;

import static com.speedment.common.injector.State.INITIALIZED;

public final class DelegateMariaDbComponent implements MariaDbComponent {

    private final MariaDbComponentImpl inner;

    public DelegateMariaDbComponent() {
        this.inner = new MariaDbComponentImpl();
    }

    @ExecuteBefore(INITIALIZED)
    public void onInitialize(DbmsHandlerComponent dbmsHandlerComponent, MariaDbDbmsType mySqlDbmsType) {
        inner.onInitialize(dbmsHandlerComponent, mySqlDbmsType);
    }
}
