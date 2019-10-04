package com.speedment.runtime.connector.postgres.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.connector.postgres.PostgresComponent;
import com.speedment.runtime.connector.postgres.PostgresDbmsType;
import com.speedment.runtime.connector.postgres.internal.PostgresComponentImpl;
import com.speedment.runtime.connector.postgres.internal.PostgresDbmsTypeImpl;
import com.speedment.runtime.core.component.DbmsHandlerComponent;

import static com.speedment.common.injector.State.INITIALIZED;

public final class DelegatePostgresComponent implements PostgresComponent {

    private final PostgresComponentImpl inner;

    public DelegatePostgresComponent() {
        this.inner = new PostgresComponentImpl();
    }

    @ExecuteBefore(INITIALIZED)
    public void onInitialize(DbmsHandlerComponent dbmsHandlerComponent, PostgresDbmsType postgresDbmsType) {
        inner.onInitialize(dbmsHandlerComponent, postgresDbmsType);
    }
}
