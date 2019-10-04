package com.speedment.runtime.connector.sqlite.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.connector.sqlite.SqliteComponent;
import com.speedment.runtime.connector.sqlite.SqliteDbmsType;
import com.speedment.runtime.connector.sqlite.internal.SqliteComponentImpl;
import com.speedment.runtime.core.component.DbmsHandlerComponent;

import static com.speedment.common.injector.State.INITIALIZED;

public final class DelegateSqliteComponent implements SqliteComponent {

    private final SqliteComponentImpl inner;

    public DelegateSqliteComponent() {
        this.inner = new SqliteComponentImpl();
    }

    @ExecuteBefore(INITIALIZED)
    public void onInitialize(DbmsHandlerComponent dbmsHandlerComponent, SqliteDbmsType sqliteDbmsType) {
        inner.onInitialize(dbmsHandlerComponent, sqliteDbmsType);
    }
}
