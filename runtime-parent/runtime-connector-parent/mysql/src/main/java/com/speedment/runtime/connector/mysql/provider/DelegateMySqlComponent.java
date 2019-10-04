package com.speedment.runtime.connector.mysql.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.connector.mysql.MySqlComponent;
import com.speedment.runtime.connector.mysql.MySqlDbmsType;
import com.speedment.runtime.connector.mysql.internal.MySqlComponentImpl;
import com.speedment.runtime.core.component.DbmsHandlerComponent;

import static com.speedment.common.injector.State.INITIALIZED;

public final class DelegateMySqlComponent implements MySqlComponent {

    private final MySqlComponentImpl inner;

    public DelegateMySqlComponent() {
        this.inner = new MySqlComponentImpl();
    }

    @ExecuteBefore(INITIALIZED)
    public void onInitialize(DbmsHandlerComponent dbmsHandlerComponent, MySqlDbmsType mySqlDbmsType) {
        inner.onInitialize(dbmsHandlerComponent, mySqlDbmsType);
    }
}
