package com.speedment.runtime.connector.sqlite.internal.types;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.JavaTypeMap;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Used to load the type map from a SQL database asynchronously.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public interface SqlTypeMappingHelper {

    CompletableFuture<Map<String, Class<?>>> loadFor(Dbms dbms);

    Optional<Class<?>> findFor(Map<String, Class<?>> loaded, ColumnMetaData metaData);

    static SqlTypeMappingHelper create(Injector injector, JavaTypeMap mappings) {
        return new SqlTypeMappingHelperImpl(
            injector.getOrThrow(ConnectionPoolComponent.class),
            injector.getOrThrow(DbmsHandlerComponent.class),
            mappings
        );
    }
}
