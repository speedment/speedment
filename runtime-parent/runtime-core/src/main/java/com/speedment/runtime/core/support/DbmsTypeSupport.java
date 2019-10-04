package com.speedment.runtime.core.support;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsColumnHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.DriverComponent;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;
import com.speedment.runtime.core.internal.db.DefaultDatabaseNamingConvention;

import java.sql.Driver;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static com.speedment.common.injector.State.CREATED;
import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.invariant.LongRangeUtil.requireNonNegative;
import static java.util.Objects.requireNonNull;

/**
 * General support class for implementing DbmsType.
 * This class cannot be sub-classes. Us the support pattern instead.
 *
 * @author per
 * @since 3.2.0
 */
public final class DbmsTypeSupport implements DbmsType {

    private static final DbmsColumnHandler DEFAULT_COLUMN_HANDLER = new DbmsColumnHandler() {
        @Override
        public Predicate<Column> excludedInInsertStatement() {
            return Column::isAutoIncrement;
        }

        @Override
        public Predicate<Column> excludedInUpdateStatement() {
            return c -> false;
        }
    };

    private final DriverComponent drivers;

    public DbmsTypeSupport(DriverComponent drivers, DbmsHandlerComponent component, DbmsType dbmsType) {
        this.drivers = requireNonNull(drivers);
        component.install(dbmsType); // Install the implementing dbmsType. ERROR: "this" is not available at this stage.
    }

    @ExecuteBefore(INITIALIZED)
    void install(@WithState(CREATED) DbmsHandlerComponent component) {
        component.install(this);
    }

    @Override
    public String getResultSetTableSchema() {
        return "TABLE_SCHEM";
    }

    @Override
    public String getSchemaTableDelimiter() {
        return ".";
    }

    @Override
    public boolean isSupported() {
        return isSupported(getDriverName());
    }

    protected boolean isSupported(String driverName) {
        return driver(driverName).isPresent();
    }

    protected Optional<Driver> driver(String driverName) {
        return drivers.driver(driverName);
    }

    @Override
    public DatabaseNamingConvention getDatabaseNamingConvention() {
        return new DefaultDatabaseNamingConvention();
    }

    @Override
    public Set<TypeInfoMetaData> getDataTypes() {
        return Collections.emptySet();
    }

    @Override
    public Optional<String> getDefaultDbmsName() {
        return Optional.empty();
    }

    @Override
    public String getInitialQuery() {
        return "select 1 from dual";
    }

    @Override
    public DbmsColumnHandler getColumnHandler() {
        return DEFAULT_COLUMN_HANDLER;
    }

    @Override
    public SkipLimitSupport getSkipLimitSupport() {
        return SkipLimitSupport.STANDARD;
    }

    @Override
    public String applySkipLimit(String originalSql, List<Object> params, long skip, long limit) {
        requireNonNegative(skip);
        requireNonNegative(limit);

        if (skip == 0 && limit == Long.MAX_VALUE) {
            return originalSql;
        }

        final StringBuilder sb = new StringBuilder(originalSql);
        if (limit == Long.MAX_VALUE) {
            sb.append(" LIMIT 223372036854775807"); // Some big number that does not overflow
        } else {
            sb.append(" LIMIT ?");
            params.add(limit);
        }

        if (skip > 0) {
            sb.append(" OFFSET ?");
            params.add(skip);
        }

        return sb.toString();

    }

    @Override
    public SubSelectAlias getSubSelectAlias() {
        return SubSelectAlias.REQUIRED;
    }

    @Override
    public SortByNullOrderInsertion getSortByNullOrderInsertion() {
        return SortByNullOrderInsertion.PRE;
    }

}
