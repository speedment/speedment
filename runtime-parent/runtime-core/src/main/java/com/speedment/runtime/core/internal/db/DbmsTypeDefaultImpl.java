package com.speedment.runtime.core.internal.db;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.core.db.DbmsColumnHandler;
import com.speedment.runtime.core.db.DbmsTypeDefault;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static com.speedment.common.invariant.LongRangeUtil.requireNonNegative;

public final class DbmsTypeDefaultImpl implements DbmsTypeDefault {

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

    public DbmsTypeDefaultImpl() {}

    @Override
    public String getResultSetTableSchema() {
        return "TABLE_SCHEM";
    }

    @Override
    public String getSchemaTableDelimiter() {
        return ".";
    }

    @Override
    public String getInitialQuery() {
        return "select 1 from dual";
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
